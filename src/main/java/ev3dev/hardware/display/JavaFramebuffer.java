package ev3dev.hardware.display;

import lombok.NonNull;

import java.awt.image.BufferedImage;
import java.io.Closeable;

/**
 * <p>Java2D-based framebuffer interface</p>
 *
 * <p>This class provides an interface between BufferedImages and native framebuffers.</p>
 *
 * @since 2.4.7
 */
public interface JavaFramebuffer extends Closeable {
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
     * Query framebuffer scanline stride, e.g. real row length in bytes.
     *
     * @return Screen scanline stride in bytes
     */
    int getStride();

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
    BufferedImage createCompatibleBuffer(int width, int height, int stride, @NonNull byte[] backed);

    /**
     * Write full-screen buffer into the framebuffer.
     *
     * @param compatible What to draw onto the screen.
     */
    void flushScreen(@NonNull BufferedImage compatible);

    /**
     * Controls whether {@link JavaFramebuffer#flushScreen(BufferedImage)} has effect or not.
     *
     * @param rly Whether flushing should be enabled or not.
     */
    void setFlushEnabled(boolean rly);

    /**
     * Store current hardware framebuffer state.
     */
    void storeData();

    /**
     * Restore original hardware framebuffer state.
     */
    void restoreData();

    /**
     * Clear the hardware framebuffer.
     */
    void clear();

    /**
     * Get the associated display manager.
     *
     * @return reference to display manager
     */
    DisplayInterface getDisplay();
}
