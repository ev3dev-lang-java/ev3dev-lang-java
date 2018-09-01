package ev3dev.hardware.display;

import ev3dev.hardware.display.spi.FramebufferProvider;

/**
 * Creates new Linux RGB framebuffer.
 */
public class RGBFramebufferProvider implements FramebufferProvider {
    @Override
    public JavaFramebuffer createFramebuffer(String fbPath) throws IllegalArgumentException {
        return new RGBFramebuffer(fbPath);
    }
}