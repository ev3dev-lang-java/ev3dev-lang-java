package ev3dev.utils.display.spi;

import ev3dev.utils.display.JavaFramebuffer;

/**
 * Framebuffer factory service provider
 *
 * @since 2.4.7
 */
public interface FramebufferProvider {

    /**
     * Create and initialize a new framebuffer.
     *
     * @param fbPath Path to the framebuffer device (e.g. /dev/fb0)
     */
    JavaFramebuffer createFramebuffer(String fbPath);
}
