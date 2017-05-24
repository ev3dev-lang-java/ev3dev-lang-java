package ev3dev.actuators.lcd;

import ev3dev.utils.Sysfs;
import lejos.hardware.lcd.GraphicsLCD;
import lombok.extern.slf4j.Slf4j;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.ImageObserver;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public @Slf4j class EV3GraphicsLCD extends Graphics2D implements GraphicsLCD {

    public static final String FB_PATH = "/dev/fb0";
    public static final int SCREEN_WIDTH = 178;
    public static final int SCREEN_HEIGHT = 128;

    public static final int LINE_LEN = 24;
    public static final int ROWS = 128;
    public static final int BUF_SIZE = LINE_LEN * ROWS;

    private BufferedImage image;
    private Graphics2D g2d;

    public EV3GraphicsLCD() {

        byte[] data = new byte[BUF_SIZE];
        byte[] bwarr = {(byte) 0xff, (byte) 0x00};
        IndexColorModel bwcm = new IndexColorModel(1, bwarr.length, bwarr, bwarr, bwarr);

        DataBuffer db = new DataBufferByte(data, data.length);
        WritableRaster wr = Raster.createPackedRaster(db, SCREEN_WIDTH, SCREEN_HEIGHT, 1, null);

        this.image = new BufferedImage(bwcm, wr, false, null);
        this.g2d = (Graphics2D) image.getGraphics();

        g2d.setPaint(Color.WHITE);
        g2d.setBackground(Color.WHITE);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    public BufferedImage getImage(){
        return image;
    }

    /**
     * Applies the Graphics context onto the ev3dev's LCD
     */
    public void flush(){

        byte[] buf = new byte[BUF_SIZE];
        byte[] pixel = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

        int bitPos;
        for (int i = 0; i < SCREEN_HEIGHT; i++){
            bitPos = 0;
            for (int j = 0; j < SCREEN_WIDTH; j++){

                if (bitPos > 7){
                    bitPos = 0;
                }

                //TODO: Rewrite not to use getRGB()! It results in low performance
                Color color = new Color(image.getRGB(j, i));

                int y = (int) (0.2126 * color.getRed() + 0.7152 * color.getBlue() + 0.0722 * color.getGreen()); //Combine all colours together 255+255+255 = 765

                if (y < 128){
                    buf[i * LINE_LEN + j / 8] |= (1 << bitPos);
                } else {
                    buf[i * LINE_LEN + j / 8] &= ~(1 << bitPos);
                }

                bitPos++;
            }
        }

        Sysfs.writeBytes(FB_PATH, buf);
    }

    @Override
    public void draw(Shape s) {
        g2d.draw(s);
    }

    @Override
    public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
        return g2d.drawImage(img, xform, obs);
    }

    @Override
    public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
        g2d.drawImage(img, op, x, y);
    }

    @Override
    public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
        g2d.drawRenderedImage(img, xform);
    }

    @Override
    public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
        g2d.drawRenderableImage(img, xform);
    }

    @Override
    public void drawString(String str, int x, int y) {
        g2d.drawString(str, x, y);
    }

    @Override
    public void drawString(String str, float x, float y) {
        g2d.drawString(str, x, y);
    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        g2d.drawString(iterator, x, y);
    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, float x, float y) {
        g2d.drawString(iterator, x, y);
    }

    @Override
    public void drawGlyphVector(GlyphVector g, float x, float y) {
        g2d.drawGlyphVector(g, x, y);
    }

    @Override
    public void fill(Shape s) {
        g2d.fill(s);
    }

    @Override
    public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
        return g2d.hit(rect, s, onStroke);
    }

    @Override
    public GraphicsConfiguration getDeviceConfiguration() {
        return g2d.getDeviceConfiguration();
    }

    @Override
    public void setComposite(Composite comp) {
        g2d.setComposite(comp);
    }

    @Override
    public void setPaint(Paint paint) {
        g2d.setPaint(paint);
    }

    @Override
    public void setStroke(Stroke s) {
        g2d.setStroke(s);
    }

    @Override
    public void setRenderingHint(Key hintKey, Object hintValue) {
        g2d.setRenderingHint(hintKey, hintValue);
    }

    @Override
    public Object getRenderingHint(Key hintKey) {
        return g2d.getRenderingHint(hintKey);
    }

    @Override
    public void setRenderingHints(Map<?, ?> hints) {
        g2d.setRenderingHints(hints);
    }

    @Override
    public void addRenderingHints(Map<?, ?> hints) {
        g2d.addRenderingHints(hints);
    }

    @Override
    public RenderingHints getRenderingHints() {
        return g2d.getRenderingHints();
    }

    @Override
    public void translate(int x, int y) {
        g2d.translate(x, y);
    }

    @Override
    public void translate(double tx, double ty) {
        g2d.translate(tx, ty);
    }

    @Override
    public void rotate(double theta) {
        g2d.rotate(theta);
    }

    @Override
    public void rotate(double theta, double x, double y) {
        g2d.rotate(theta, x, y);
    }

    @Override
    public void scale(double sx, double sy) {
        g2d.scale(sx, sy);
    }

    @Override
    public void shear(double shx, double shy) {
        g2d.shear(shx, shy);
    }

    @Override
    public void transform(AffineTransform Tx) {
        g2d.transform(Tx);
    }

    @Override
    public void setTransform(AffineTransform Tx) {
        g2d.setTransform(Tx);
    }

    @Override
    public AffineTransform getTransform() {
        return g2d.getTransform();
    }

    @Override
    public Paint getPaint() {
        return g2d.getPaint();
    }

    @Override
    public Composite getComposite() {
        return g2d.getComposite();
    }

    @Override
    public void setBackground(Color color) {
        g2d.setBackground(color);
    }

    @Override
    public Color getBackground() {
        return g2d.getBackground();
    }

    @Override
    public Stroke getStroke() {
        return g2d.getStroke();
    }

    @Override
    public void clip(Shape s) {
        g2d.clip(s);
    }

    @Override
    public FontRenderContext getFontRenderContext() {
        return g2d.getFontRenderContext();
    }

    @Override
    public Graphics create() {
        return g2d.create();
    }

    @Override
    public Color getColor() {
        return g2d.getColor();
    }

    @Override
    public void setColor(Color c) {
        g2d.setColor(c);
    }

    @Override
    public void setPaintMode() {
        g2d.setPaintMode();
    }

    @Override
    public void setXORMode(Color c1) {
        g2d.setXORMode(c1);
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
    public FontMetrics getFontMetrics(Font f) {
        return g2d.getFontMetrics(f);
    }

    @Override
    public Rectangle getClipBounds() {
        return g2d.getClipBounds();
    }

    @Override
    public void clipRect(int x, int y, int width, int height) {
        g2d.clipRect(x, y, width, height);
    }

    @Override
    public void setClip(int x, int y, int width, int height) {
        g2d.setClip(x, y, width, height);
    }

    @Override
    public Shape getClip() {
        return g2d.getClip();
    }

    @Override
    public void setClip(Shape clip) {
        g2d.setClip(clip);
    }

    @Override
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        g2d.copyArea(x, y, width, height, dx, dy);
    }

    //Graphics LCD

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
    public void clearRect(int x, int y, int width, int height) {
        g2d.clearRect(x, y, width, height);
    }

    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        g2d.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    @Override
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        g2d.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    @Override
    public void drawOval(int x, int y, int width, int height) {
        g2d.drawOval(x, y, width, height);
    }

    @Override
    public void fillOval(int x, int y, int width, int height) {
        g2d.fillOval(x, y, width, height);
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
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        g2d.drawPolyline(xPoints, yPoints, nPoints);
    }

    @Override
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        g2d.drawPolygon(xPoints, yPoints, nPoints);
    }

    @Override
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        g2d.fillPolygon(xPoints, yPoints, nPoints);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return g2d.drawImage(img, x, y, observer);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return g2d.drawImage(img, x, y, width, height, observer);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return g2d.drawImage(img, x, y, bgcolor, observer);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return g2d.drawImage(img, x, y, width, height, observer);
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
                             ImageObserver observer) {
        return g2d.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
                             Color bgcolor, ImageObserver observer) {
        return g2d.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
    }

    @Override
    public void dispose() {
        g2d.dispose();
        flush();
    }

    // CommonLCD

    @Override
    public void refresh() {
        flush();
    }

    @Override
    public void clear() {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0,0, this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
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
