package ev3dev.utils.display;

import java.awt.image.BufferedImage;

/**
 * <p>Java2D-based framebuffer interface</p>
 *
 * @since 2.4.7
 */
public interface JavaFramebuffer {
    /**
     * Query framebuffer width.
     *
     * @return Screen width in pixels.
     */
    int getWidth();

    /**
     * Query framebuffer height.
     *
     * @return Screen height in pixels.
     */
    int getHeight();

    /**
     * Create full-screen buffer.
     *
     * @return BufferedImage with correct settings.
     */
    BufferedImage createCompatibleBuffer();

    /**
     * Create pixel-compatible buffer with a specified size.
     *
     * @param width  Requested image width.
     * @param height Requested image height.
     * @return BufferedImage with correct settings.
     */
    BufferedImage createCompatibleBuffer(int width, int height);

    /**
     * Create pixel-compatible buffer with a specified size and stride.
     *
     * @param width  Requested image width.
     * @param height Requested image height.
     * @param stride Requested scanline stride.
     * @return BufferedImage with correct settings.
     */
    BufferedImage createCompatibleBuffer(int width, int height, int stride);

    /**
     * Create pixel-compatible buffer with a specified size and stride.
     * This buffer will be backed by existing byte array.
     *
     * @param width  Requested image width.
     * @param height Requested image height.
     * @param stride Requested scanline stride.
     * @return BufferedImage with correct settings.
     */
    BufferedImage createCompatibleBuffer(int width, int height, int stride, byte[] backed);

    /**
     * Write full-screen buffer into the framebuffer.
     *
     * @param compatible What to draw onto the screen.
     */
    void flushScreen(BufferedImage compatible);
}
