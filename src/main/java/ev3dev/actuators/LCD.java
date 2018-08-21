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
import java.io.RandomAccessFile;
import java.io.IOException;
import java.nio.*;
import java.nio.file.*;
import java.nio.channels.FileChannel;
import java.util.Objects;

public class LCD extends EV3DevDevice implements GraphicsLCD {

    private static final Logger log = LoggerFactory.getLogger(LCD.class);

    public static final String EV3DEV_LCD_KEY = "EV3DEV_LCD_KEY";

    private EV3DevScreenInfo info;

    private BufferedImage image;
    private Graphics2D g2d;

    private static GraphicsLCD instance;

    private MappedByteBuffer fbmem;
    private int bufferSize;

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

    private void identifyMode() {
        int bits = Sysfs.readInteger(Paths.get(info.getSysfsPath(), "bits_per_pixel").toString());

        if (bits == 32) {
            this.info.setKernelMode(EV3DevScreenInfo.Mode.XRGB);
        } else {
            this.info.setKernelMode(EV3DevScreenInfo.Mode.BITPLANE);
        }
    }

    private void initFramebuffer() throws IOException {
        String alternative = System.getProperty(EV3DEV_LCD_KEY);
        if (alternative != null) {
            this.info.setKernelPath(alternative);
        }

        if (Files.notExists(Paths.get(info.getKernelPath()))) {
            throw new RuntimeException("Device path not found: " + info.getKernelPath());
        }

        if (info.getKernelMode() == EV3DevScreenInfo.Mode.BITPLANE) {
            bufferSize = info.getBitModeStride() * info.getHeight();
        } else {
            bufferSize = 4 * info.getWidth() * info.getHeight();
        }

        try (RandomAccessFile f = new RandomAccessFile(info.getKernelPath(), "rw");
             FileChannel chan = f.getChannel();) {
            fbmem = chan.map(FileChannel.MapMode.READ_WRITE, 0, bufferSize);
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
                                            1, info.getBitModeStride(), 0);

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
        // data masks
        final int maskAlpha = 0xFF000000;
        final int maskRed   = 0x00FF0000;
        final int maskGreen = 0x0000FF00;
        final int maskBlue  = 0x000000FF;
        final int maskNone  = 0x00000000;

        // initialize backing store
        int[] data = new int[bufferSize / 4];
        DataBuffer db = new DataBufferInt(data, data.length);

        // initialize raster
        WritableRaster wr = Raster.createPackedRaster(db, info.getWidth(), info.getHeight(), info.getWidth(),
                                                      new int[]{ maskRed, maskGreen, maskBlue, maskAlpha }, null);

        // initialize color interpreter
        DirectColorModel cm = new DirectColorModel(32, maskRed, maskGreen, maskBlue, maskAlpha);

        // glue everything together
        return new BufferedImage(cm, wr, false, null);
    }

    private void init(EV3DevScreenInfo inInfo) {
        this.info = inInfo;

        identifyMode();

        try {
            initFramebuffer();
        } catch (IOException e) {
            throw new RuntimeException("Unable to map the framebuffer", e);
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
    public void flush() {
        WritableRaster rst = image.getRaster();
        DataBuffer buf     = rst.getDataBuffer();
        if (buf instanceof DataBufferByte) {
            byte[] data = ((DataBufferByte) buf).getData();
            ByteBuffer bytes = ByteBuffer.wrap(data);
            fbmem.rewind();
            fbmem.put(bytes);
        } else if (buf instanceof DataBufferInt) {
            int[] data = ((DataBufferInt) buf).getData();
            IntBuffer ints = IntBuffer.wrap(data);
            IntBuffer dest = fbmem.asIntBuffer();
            dest.rewind();
            dest.put(ints);
        }
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
        log.debug("Feature not implemented");
        return null;
    }

    @Override
    public byte[] getHWDisplay() {
        log.debug("Feature not implemented");
        return null;
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
