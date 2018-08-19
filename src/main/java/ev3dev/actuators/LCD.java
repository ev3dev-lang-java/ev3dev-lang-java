package ev3dev.actuators;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.hardware.EV3DevScreenInfo;
import ev3dev.hardware.EV3DevPlatforms;
import ev3dev.utils.Sysfs;
import lejos.hardware.lcd.GraphicsLCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.*;
import java.awt.color.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class LCD extends EV3DevDevice implements GraphicsLCD {

    private static final Logger log = LoggerFactory.getLogger(LCD.class);

    public static final String EV3DEV_LCD_KEY = "EV3DEV_LCD_KEY";

    private EV3DevScreenInfo info;
    private int bufferSize;

    private BufferedImage image;
    private Graphics2D g2d;

    private static GraphicsLCD instance;

    /**
     * Return a Instance of Sound.
     *
     * @return A Sound instance
     */
    public static GraphicsLCD getInstance() {
        if (instance == null) {
            instance = new LCD();
        }
        return instance;
    }

    // Prevent duplicate objects
    private LCD() {
        EV3DevPlatforms conf = new EV3DevPlatforms();

        if(conf.getPlatform() == EV3DevPlatform.EV3BRICK){
            init(conf.getFramebufferInfo());
        } else {
            log.error("This actuator was only tested for: {}", EV3DevPlatform.EV3BRICK);
            throw new RuntimeException("This actuator was only tested for: " + EV3DevPlatform.EV3BRICK);
        }
    }

    private BufferedImage initBitplane() {
        // initialize backing store
        byte[] data = new byte[bufferSize];
        DataBuffer db = new DataBufferByte(data, data.length);

        // initialize buffer <-> sample mapping
        MultiPixelPackedSampleModel packing =
            new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE,
                                            info.getWidth(), info.getHeight(),
                                            1, info.getStride(), 0);

        // initialize raster
        WritableRaster wr = Raster.createWritableRaster(packing, db, null);

        // initialize color interpreter
        int[] bits = new int[]{1};
        ColorSpace gray = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ComponentColorModel cm = new ComponentColorModel(gray, bits, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);

        // glue everything together
        return new BufferedImage(cm, wr, false, null);
    }

    private BufferedImage initXrgb() {
        // initialize backing store
        byte[] data = new byte[bufferSize];
        DataBuffer db = new DataBufferByte(data, data.length);

        // initialize buffer <-> sample mapping
        //            offset of:  R  G  B  A
        int[] offsets = new int[]{1, 2, 3, 0};
        PixelInterleavedSampleModel packing =
            new PixelInterleavedSampleModel(DataBuffer.TYPE_BYTE,
                                            info.getWidth(), info.getHeight(),
                                            4, 4 * info.getWidth(), offsets);

        // initialize raster
        WritableRaster wr = Raster.createWritableRaster(packing, db, null);

        // initialize color interpreter
        // sample order: R, G, B, A
        ColorSpace rgb = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        ComponentColorModel cm = new ComponentColorModel(rgb, true, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);

        // glue everything together
        return new BufferedImage(cm, wr, false, null);
    }

    private void init(EV3DevScreenInfo inInfo) {
        this.info = inInfo;
        this.bufferSize = this.info.getStride() * this.info.getHeight();

        String alternative = System.getProperty(EV3DEV_LCD_KEY);
        if (alternative != null) {
            this.info.setKernelPath(alternative);
        }

        if (Files.notExists(Paths.get(info.getKernelPath()))) {
            throw new RuntimeException("Device path not found: " + info.getKernelPath());
        }

        this.image = info.getKernelMode() == EV3DevScreenInfo.Mode.BITPLANE ? initBitplane() : initXrgb();
        this.g2d = (Graphics2D) image.getGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        this.refresh();
    }

    public BufferedImage getImage(){
        return image;
    }

    /**
     * Write LCD with current context
     */
    public void flush(){
        byte[] data = getHWDisplay();
        Sysfs.writeBytes(info.getKernelPath(), data);
    }


    //Graphics LCD

    @Override
    public void translate(int x, int y) {
        g2d.translate(x, y);
    }

    @Override
    public Font getFont() {
        return g2d.getFont();
    }

    @Override
    public void setFont(Font font) {
        g2d.setFont(font);
    }


    @Override
    public int getTranslateX() {
        return 0;
    }

    @Override
    public int getTranslateY() {
        return 0;
    }

    /**
     * Use in combination with possible values from
     * lejos.robotics.Color
     *
     * @param color
     */
    @Override
    public void setColor(int color) {
        if(color == lejos.robotics.Color.WHITE){
            g2d.setColor(Color.WHITE);
        }else if(color == lejos.robotics.Color.BLACK){
            g2d.setColor(Color.BLACK);
        }else{
            throw new IllegalArgumentException("Bad color configured");
        }
    }

    @Override
    public void setColor(int i, int i1, int i2) {
        log.debug("Feature not implemented");
    }

    @Override
    public void setPixel(int i, int i1, int i2) {
        log.debug("Feature not implemented");
    }

    @Override
    public int getPixel(int i, int i1) {
        log.debug("Feature not implemented");
        return -1;
    }

    @Override
    public void drawString(String s, int i, int i1, int i2, boolean b) {
        log.debug("Feature not implemented");
    }

    @Override
    public void drawString(String s, int i, int i1, int i2) {
        g2d.drawString(s, i, i1);
    }

    @Override
    public void drawSubstring(String s, int i, int i1, int i2, int i3, int i4) {
        log.debug("Feature not implemented");
    }

    @Override
    public void drawChar(char c, int i, int i1, int i2) {
        log.debug("Feature not implemented");
    }

    @Override
    public void drawChars(char[] chars, int i, int i1, int i2, int i3, int i4) {
        log.debug("Feature not implemented");
    }

    //TODO Review LeJOS Javadocs
    @Override
    public int getStrokeStyle() {
        log.debug("Feature not implemented");
        return -1;
    }

    //TODO Review LeJOS Javadocs
    @Override
    public void setStrokeStyle(int i) {
        log.debug("Feature not implemented");
    }

    @Override
    public void drawRegionRop(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
        log.debug("Feature not implemented");
    }

    @Override
    public void drawRegionRop(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        log.debug("Feature not implemented");
    }

    @Override
    public void drawRegion(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
        log.debug("Feature not implemented");
    }

    @Override
    public void drawImage(Image image, int i, int i1, int i2) {
        g2d.drawImage(image,i, i1, null);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        g2d.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        g2d.fillRect(x, y, width, height);
    }

    @Override
    public void copyArea(int i, int i1, int i2, int i3, int i4, int i5, int i6) {
        log.debug("Feature not implemented");
    }

    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        g2d.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        g2d.drawRect(x, y, width, height);
    }

    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        g2d.drawArc(x, y, width, height, startAngle, arcAngle);
    }

    @Override
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        g2d.fillArc(x, y, width, height, startAngle, arcAngle);
    }

    // CommonLCD

    @Override
    public void refresh() {
        flush();
    }

    @Override
    public void clear() {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0,0, info.getWidth(), info.getHeight());
        flush();
    }

    @Override
    public int getWidth() {
        return info.getWidth();
    }

    @Override
    public int getHeight() {
        return info.getHeight();
    }

    @Override
    public byte[] getDisplay() {
        return getHWDisplay();
    }

    @Override
    public byte[] getHWDisplay() {
        Raster rst = image.getRaster();
        DataBufferByte buf = (DataBufferByte) rst.getDataBuffer();
        return buf.getData();
    }

    @Override
    public void setContrast(int i) {
        log.debug("Feature not implemented");
    }

    @Override
    public void bitBlt(byte[] bytes, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        log.debug("Feature not implemented");
    }

    @Override
    public void bitBlt(byte[] bytes, int i, int i1, int i2, int i3, byte[] bytes1, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        log.debug("Feature not implemented");
    }

    @Override
    public void setAutoRefresh(boolean b) {
        log.debug("Feature not implemented");
    }

    @Override
    public int setAutoRefreshPeriod(int i) {
        log.debug("Feature not implemented");
        return -1;
    }
}
