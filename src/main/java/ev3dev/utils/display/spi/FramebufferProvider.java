package ev3dev.utils.display.spi;

import ev3dev.utils.display.JavaFramebuffer;

import java.io.IOError;

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
     * @throws IllegalArgumentException When this framebuffer is not compatible with this device.
     * @throws IOError When there was an error accessing the device.
     */
    JavaFramebuffer createFramebuffer(String fbPath) throws IllegalArgumentException, IOError;
}
