package ev3dev.actuators;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.Sysfs;
import lejos.hardware.lcd.GraphicsLCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class LCDJessie extends EV3DevDevice implements GraphicsLCD {

    private static final Logger log = LoggerFactory.getLogger(LCDJessie.class);

    public static final String EV3DEV_EV3_DEVICES_PATH = "/dev";
    public static final String EV3DEV_EV3_LCD_NAME = "fb0";
    public static final String EV3DEV_EV3_LCD_PATH = EV3DEV_EV3_DEVICES_PATH + "/" + EV3DEV_EV3_LCD_NAME;
    public static final String EV3DEV_LCD_KEY = "EV3DEV_LCD_KEY";
    public static final String FB_PATH =
        Objects.nonNull(System.getProperty(EV3DEV_LCD_KEY)) ? System.getProperty(EV3DEV_LCD_KEY) : EV3DEV_EV3_LCD_PATH;

    private int SCREEN_WIDTH = 0;
    private int SCREEN_HEIGHT = 0;
    private int LINE_LEN = 0;
    private int BUFFER_SIZE = 0;

    public static final int EV3_SCREEN_WIDTH = 178;
    public static final int EV3_SCREEN_HEIGHT = 128;
    public static final int EV3_LINE_LEN = 24;
    public static final int EV3_ROWS = 128;
    public static final int EV3_BUFFER_SIZE = EV3_LINE_LEN * EV3_ROWS;

    private BufferedImage image;
    private Graphics2D g2d;

    private static GraphicsLCD instance;

    /**
     * Singleton constructor
     *
     * @return A Sound instance
     */
    public static GraphicsLCD getInstance() {
        //TODO Refactor
        if (instance == null) {
            instance = new LCDJessie();
        }
        return instance;
    }

    // Prevent duplicate objects
    private LCDJessie() {

        log.info("Instancing LCD for Jessie");

        if (CURRENT_PLATFORM.equals(EV3DevPlatform.EV3BRICK)) {
            init(EV3_SCREEN_WIDTH, EV3_SCREEN_HEIGHT, EV3_LINE_LEN, EV3_BUFFER_SIZE);
        } else {
            log.error("This actuator was only tested for: {}", EV3DevPlatform.EV3BRICK);
            throw new RuntimeException("This actuator was only tested for: " + EV3DevPlatform.EV3BRICK);
        }
    }

    private void init(
        final int width,
        final int height,
        final int lineLength,
        final int bufferSize) {

        this.SCREEN_WIDTH = width;
        this.SCREEN_HEIGHT = height;
        this.LINE_LEN = lineLength;
        this.BUFFER_SIZE = bufferSize;

        if (Files.notExists(Paths.get(FB_PATH))) {
            throw new RuntimeException("Device path not found: " + FB_PATH);
        }

        byte[] data = new byte[bufferSize];
        byte[] bwarr = {(byte) 0xff, (byte) 0x00};
        IndexColorModel bwcm = new IndexColorModel(1, bwarr.length, bwarr, bwarr, bwarr);

        DataBuffer db = new DataBufferByte(data, data.length);
        WritableRaster wr = Raster.createPackedRaster(db, SCREEN_WIDTH, SCREEN_HEIGHT, 1, null);

        this.image = new BufferedImage(bwcm, wr, false, null);
        this.g2d = (Graphics2D) image.getGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        this.refresh();
    }

    public BufferedImage getImage() {
        return image;
    }

    /**
     * Write LCD with current context
     */
    public void flush() {

        byte[] buf = new byte[BUFFER_SIZE];
        int bitPos;
        for (int i = 0; i < SCREEN_HEIGHT; i++) {
            bitPos = 0;
            for (int j = 0; j < SCREEN_WIDTH; j++) {

                if (bitPos > 7) {
                    bitPos = 0;
                }

                //TODO: Rewrite not to use getRGB()! It results in low performance
                Color color = new Color(image.getRGB(j, i));
                //Combine all colours together 255+255+255 = 765
                int y = (int) (0.2126 * color.getRed() + 0.7152 * color.getBlue() + 0.0722 * color.getGreen());
                if (y < 128) {
                    buf[i * LINE_LEN + j / 8] |= (1 << bitPos);
                } else {
                    buf[i * LINE_LEN + j / 8] &= ~(1 << bitPos);
                }
                bitPos++;
            }
        }

        Sysfs.writeBytes(FB_PATH, buf);
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
     * @param color Color
     */
    @Override
    public void setColor(int color) {
        if (color == lejos.robotics.Color.WHITE) {
            g2d.setColor(Color.WHITE);
        } else if (color == lejos.robotics.Color.BLACK) {
            g2d.setColor(Color.BLACK);
        } else {
            throw new IllegalArgumentException("Bad color configured");
        }
    }

    @Override
    public void setColor(int i, int i1, int i2) {
        if ((i == 0) && (i1 == 0) && (i2 == 0)) {
            g2d.setColor(Color.BLACK);
        } else if ((i == 255) && (i1 == 255) && (i2 == 255)) {
            g2d.setColor(Color.WHITE);
        } else {
            log.debug("EV3 Display only accepts rgb(0,0,0) or rgb(255,255,555)");
        }
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

    @Deprecated
    @Override
    public void drawRegionRop(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
        log.debug("Feature not implemented");
    }

    @Deprecated
    @Override
    public void drawRegionRop(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        log.debug("Feature not implemented");
    }

    @Deprecated
    @Override
    public void drawRegion(Image image, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
        log.debug("Feature not implemented");
    }

    @Override
    public void drawImage(Image image, int i, int i1, int i2) {
        g2d.drawImage(image, i, i1, null);
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
        g2d.fillRect(0, 0, this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
        flush();
    }

    @Override
    public int getWidth() {
        return this.SCREEN_WIDTH;
    }

    @Override
    public int getHeight() {
        return this.SCREEN_HEIGHT;
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
    public void bitBlt(
        byte[] bytes, int i, int i1, int i2, int i3, int i4, int i5,
        int i6, int i7, int i8) {
        log.debug("Feature not implemented");
    }

    @Override
    public void bitBlt(
        byte[] bytes, int i, int i1, int i2, int i3, byte[] bytes1, int i4, int i5,
        int i6, int i7, int i8, int i9, int i10) {
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

    @Override
    public void drawOval(int x, int y, int width, int height) {
        g2d.drawOval(x, y, width, height);
    }
}
