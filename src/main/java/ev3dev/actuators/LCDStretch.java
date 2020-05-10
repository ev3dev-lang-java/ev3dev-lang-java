package ev3dev.actuators;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.hardware.display.ImageUtils;
import ev3dev.hardware.display.JavaFramebuffer;
import ev3dev.hardware.display.SystemDisplay;
import lejos.hardware.lcd.GraphicsLCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Lejos LCD reimplementation using Java2D API
 */
public class LCDStretch extends EV3DevDevice implements GraphicsLCD {

    // custom config
    public static final String EV3DEV_LCD_KEY = "EV3DEV_LCD_KEY";
    public static final String EV3DEV_LCD_DEFAULT = "/dev/fb0";
    public static final String EV3DEV_LCD_MODE_KEY = "EV3DEV_LCD_MODE_KEY";

    // logger
    private static final Logger log = LoggerFactory.getLogger(LCDStretch.class);

    // drawable
    private JavaFramebuffer fb;
    private BufferedImage image;
    private Graphics2D g2d;

    // autorefresh
    private Timer timer;
    private boolean timer_run = false;
    private int timer_msec = 0;

    // stroke
    private int stroke;

    private static LCDStretch instance;

    /**
     * Singleton constructor
     *
     * @return GraphicsLCD
     */
    public static GraphicsLCD getInstance() {
        //TODO Refactor
        if (instance == null) {
            instance = new LCDStretch();
        }
        return instance;
    }

    // Prevent duplicate objects
    private LCDStretch() {

        log.info("Instancing LCD for Stretch");

        if (!CURRENT_PLATFORM.equals(EV3DevPlatform.EV3BRICK)) {
            log.error("This actuator was only tested for: {}", EV3DevPlatform.EV3BRICK);
            throw new RuntimeException("This actuator was only tested for: " + EV3DevPlatform.EV3BRICK);
        }

        this.fb = SystemDisplay.initializeRealFramebuffer();
        this.timer = new Timer("LCD flusher", true);
        this.image = fb.createCompatibleBuffer();
        this.g2d = this.image.createGraphics();
        this.clear();
    }

    public JavaFramebuffer getFramebuffer() {
        return fb;
    }

    //Graphics LCD

    /**
     * Write LCD with current context
     */
    public void flush() {
        if (log.isTraceEnabled()) {
            log.trace("flushing framebuffer");
        }

        fb.flushScreen(image);
    }

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
        return (int) g2d.getTransform().getTranslateX();
    }

    @Override
    public int getTranslateY() {
        return (int) g2d.getTransform().getTranslateY();
    }

    /**
     * Set RGB value
     *
     * @param rgb rgb
     */
    @Override
    public void setColor(int rgb) {
        g2d.setColor(new Color(rgb));
    }

    @Override
    public void setColor(int r, int g, int b) {
        g2d.setColor(new Color(r, g, b));
    }

    @Override
    public void setPixel(int x, int y, int color) {
        Point2D.Float in = new Point2D.Float(x, y);
        Point2D.Float dst = new Point2D.Float();
        g2d.getTransform().transform(in, dst);

        Color fill = color == 0 ? Color.WHITE : Color.BLACK;

        image.setRGB((int) dst.x, (int) dst.y, fill.getRGB());
    }

    @Override
    public int getPixel(int x, int y) {
        Point2D.Float in = new Point2D.Float(x, y);
        Point2D.Float dst = new Point2D.Float();
        g2d.getTransform().transform(in, dst);

        int rgb = image.getRGB((int) dst.x, (int) dst.y);
        if ((rgb & 0x00FFFFFF) == 0x00FFFFFF) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void drawString(String str, int x, int y, int anchor, boolean inverted) {
        Color oldFg = g2d.getColor();
        Color oldBg = g2d.getBackground();
        g2d.setColor(inverted ? Color.WHITE : Color.BLACK);
        g2d.setBackground(inverted ? Color.BLACK : Color.WHITE);

        drawString(str, x, y, anchor);

        g2d.setColor(oldFg);
        g2d.setBackground(oldBg);
    }

    @Override
    public void drawString(String str, int x, int y, int anchor) {
        FontMetrics metrics = g2d.getFontMetrics();
        int w = metrics.stringWidth(str);
        int h = metrics.getHeight();
        x = adjustX(x, w, anchor);
        y = adjustY(y, h, anchor);

        g2d.drawString(str, x, y);
    }

    @Override
    public void drawSubstring(String str, int offset, int len,
                              int x, int y, int anchor) {
        String sub = str.substring(offset, offset + len);
        drawString(sub, x, y, anchor);
    }

    @Override
    public void drawChar(char character, int x, int y, int anchor) {
        String str = new String(new char[]{character});
        drawString(str, x, y, anchor);
    }

    @Override
    public void drawChars(char[] data, int offset, int length,
                          int x, int y, int anchor) {
        String str = new String(data);
        drawString(str, x, y, anchor);
    }

    @Override
    public int getStrokeStyle() {
        return this.stroke;
    }

    @Override
    public void setStrokeStyle(int i) {
        this.stroke = i;
        Stroke stroke;
        if (i == DOTTED) {
            float[] dash = new float[]{3.0f, 3.0f};
            float dash_phase = 0.0f;
            stroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0.0f, dash, dash_phase);
        } else if (i == SOLID) {
            stroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0.0f);
        } else {
            throw new IllegalArgumentException("Invalid stroke");
        }
        g2d.setStroke(stroke);
    }

    @Deprecated
    @Override
    public void drawRegionRop(Image src, int sx, int sy, int w, int h, int x, int y, int anchor, int rop) {
        drawRegionRop(src, sx, sy, w, h, x, y, TRANS_NONE, anchor, rop);
    }

    @Deprecated
    @Override
    public void drawRegionRop(
        Image src, int sx, int sy, int w, int h, int transform, int x, int y, int anchor, int rop) {
        x = adjustX(x, w, anchor);
        y = adjustY(y, h, anchor);
        BufferedImage srcI = any2rgb(src);

        double midx = srcI.getWidth() / 2.0;
        double midy = srcI.getHeight() / 2.0;
        AffineTransform tf = new AffineTransform();
        tf.translate(midx, midy);
        int h0 = h;
        switch (transform) {
            case TRANS_MIRROR:
                tf.scale(-1.0, 1.0);
                break;
            case TRANS_MIRROR_ROT90:
                tf.scale(-1.0, 1.0);
                tf.quadrantRotate(1);
                h = w;
                w = h0;
                break;
            case TRANS_MIRROR_ROT180:
                tf.scale(-1.0, 1.0);
                tf.quadrantRotate(2);
                break;
            case TRANS_MIRROR_ROT270:
                tf.scale(-1.0, 1.0);
                tf.quadrantRotate(3);
                h = w;
                w = h0;
                break;
            case TRANS_NONE:
                break;
            case TRANS_ROT90:
                tf.quadrantRotate(1);
                h = w;
                w = h0;
                break;
            case TRANS_ROT180:
                tf.quadrantRotate(2);
                break;
            case TRANS_ROT270:
                tf.quadrantRotate(3);
                h = w;
                w = h0;
                break;
            default:
                throw new RuntimeException("Bad Option");
        }
        tf.translate(-midx, -midy);
        AffineTransformOp op = new AffineTransformOp(tf, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage transformed = ImageUtils.createXRGBImage(w, h);
        transformed = op.filter(srcI, transformed);

        BufferedImage dstI = any2rgb(image);
        bitBlt(srcI, sx, sy, dstI, x, y, w, h, rop);
        g2d.drawImage(dstI, 0, 0, null);
    }

    @Deprecated
    @Override
    public void drawRegion(Image src,
                           int sx, int sy,
                           int w, int h,
                           int transform,
                           int x, int y,
                           int anchor) {
        drawRegionRop(src, sx, sy, w, h, transform, x, y, anchor, ROP_COPY);
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
    public void copyArea(int sx, int sy,
                         int w, int h,
                         int x, int y, int anchor) {
        x = adjustX(x, w, anchor);
        y = adjustY(y, h, anchor);
        g2d.copyArea(sx, sy, w, h, x, y);
    }

    /**
     * Adjust the x co-ordinate to use the translation and anchor values.
     */
    private int adjustX(int x, int w, int anchor) {
        switch (anchor & (LEFT | RIGHT | HCENTER)) {
            case LEFT:
                break;
            case RIGHT:
                x -= w;
                break;
            case HCENTER:
                x -= w / 2;
                break;
            default:
                throw new RuntimeException("Bad Option");
        }
        return x;
    }

    /**
     * Adjust the y co-ordinate to use the translation and anchor values.
     */
    private int adjustY(int y, int h, int anchor) {
        switch (anchor & (TOP | BOTTOM | VCENTER)) {
            case TOP:
                break;
            case BOTTOM:
                y -= h;
                break;
            case VCENTER:
                y -= h / 2;
                break;
            default:
                throw new RuntimeException("Bad Option");
        }
        return y;
    }

    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        g2d.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    // CommonLCD

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

    @Override
    public void refresh() {
        flush();
    }

    @Override
    public void clear() {
        AffineTransform tf = (AffineTransform) g2d.getTransform().clone();
        g2d.getTransform().setToIdentity();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, fb.getWidth(), fb.getHeight());
        flush();
        g2d.setTransform(tf);
    }

    @Override
    public int getWidth() {
        return fb.getWidth();
    }

    @Override
    public int getHeight() {
        return fb.getHeight();
    }

    @Override
    public byte[] getDisplay() {
        return ImageUtils.getImageBytes(image);
    }


    @Override
    public byte[] getHWDisplay() {
        return getDisplay();
    }

    @Override
    public void setContrast(int i) {
        // not implemented even on leJOS
        log.debug("Feature not implemented");
    }

    /**
     * Convert from leJOS image format to Java image
     */
    private BufferedImage lejos2rgb(byte[] src, int width, int height) {
        @SuppressWarnings("SuspiciousNameCombination")
        BufferedImage in = ImageUtils.createBWImage(height, width, true, src);
        BufferedImage out = ImageUtils.createXRGBImage(width, height);
        return java_lejos_flip(in, out);
    }

    private BufferedImage any2rgb(Image img) {
        BufferedImage copy = ImageUtils.createXRGBImage(img.getWidth(null), img.getHeight(null));
        Graphics2D gfx = (Graphics2D) copy.getGraphics();
        gfx.drawImage(img, 0, 0, null);
        gfx.dispose();
        return copy;
    }

    /**
     * Convert from Java image to leJOS image format
     */
    private byte[] any2lejos(BufferedImage img) {
        BufferedImage out = ImageUtils.createBWImage(img.getHeight(), img.getWidth(), true);
        BufferedImage right = java_lejos_flip(img, out);
        return ((DataBufferByte) right.getRaster().getDataBuffer()).getData();
    }

    private BufferedImage java_lejos_flip(BufferedImage in, BufferedImage out) {
        AffineTransform tf = new AffineTransform();
        tf.quadrantRotate(1);
        tf.scale(-1.0, +1.0);
        AffineTransformOp op = new AffineTransformOp(tf, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(in, out);
    }

    /**
     * Slow emulation of leJOS bitBlt()
     */
    @Override
    public void bitBlt(byte[] src, int sw, int sh, int sx, int sy, int dx, int dy, int w, int h, int rop) {
        BufferedImage srcI = lejos2rgb(src, sw, sh);
        BufferedImage dstI = any2rgb(image);
        bitBlt(srcI, sx, sy, dstI, dx, dy, w, h, rop);
        g2d.drawImage(dstI, 0, 0, null);
    }

    /**
     * Slow emulation of leJOS bitBlt()
     */
    @Override
    public void bitBlt(
        byte[] src, int sw, int sh, int sx, int sy, byte[] dst, int dw, int dh, int dx, int dy, int w, int h, int rop) {
        BufferedImage srcI = lejos2rgb(src, sw, sh);
        BufferedImage dstI = lejos2rgb(dst, dw, dh);
        bitBlt(srcI, sx, sy, dstI, dx, dy, w, h, rop);
        Graphics2D gfx = dstI.createGraphics();
        gfx.drawImage(srcI,
            dy, dx, dy + h, dx + w,
            sy, sx, sy + h, sx + w,
            Color.WHITE, null);
        gfx.dispose();
        byte[] data = any2lejos(dstI);
        System.arraycopy(data, 0, dst, 0, Math.min(data.length, dst.length));
    }

    private void bitBlt(BufferedImage src, int sx, int sy, BufferedImage dst, int dx, int dy, int w, int h, int rop) {
        WritableRaster srcR = src.getRaster();
        WritableRaster dstR = dst.getRaster();

        byte msk_dst = (byte) (0xFF & (rop >> 24));
        byte xor_dst = (byte) (0xFF & (rop >> 16));
        byte msk_src = (byte) (0xFF & (rop >> 8));
        byte xor_src = (byte) (0xFF & (rop));
        boolean dstskip = msk_dst == 0 && xor_dst == 0;

        int[] dstpix = new int[4];
        int[] srcpix = new int[4];
        for (int vx = 0; vx < w; vx++) {
            for (int vy = 0; vy < h; vy++) {
                int srcx = sx + vx;
                int srcy = sy + vy;
                int dstx = dx + vx;
                int dsty = dy + vy;
                srcR.getPixel(srcx, srcy, srcpix);

                if (dstskip) {
                    // only rgb, no a
                    for (int s = 0; s < 3; s++) {
                        dstpix[s] = ((srcpix[s] & msk_src) ^ xor_src);
                    }
                } else {
                    dstR.getPixel(dstx, dsty, dstpix);
                    // only rgb, no a
                    for (int s = 0; s < 3; s++) {
                        dstpix[s] = ((dstpix[s] & msk_dst) ^ xor_dst) ^ ((srcpix[s] & msk_src) ^ xor_src);
                    }
                }
                dstR.setPixel(dstx, dsty, dstpix);
            }
        }
    }


    @Override
    public void setAutoRefresh(boolean b) {
        if (this.timer_run != b) {
            this.timer_run = b;
            timerUpdate();
        }
    }

    @Override
    public int setAutoRefreshPeriod(int i) {
        int old = this.timer_msec;
        if (old != i) {
            this.timer_msec = i;
            timerUpdate();
        }
        return old;
    }

    private void timerUpdate() {
        timer.cancel();
        if (timer_run && timer_msec > 0) {
            timer.scheduleAtFixedRate(new Flusher(), 0, timer_msec);
        }
    }

    private class Flusher extends TimerTask {
        @Override
        public void run() {
            refresh();
        }
    }

    @Override
    public void drawOval(int x, int y, int width, int height) {
        g2d.drawOval(x, y, width, height);
    }

}
