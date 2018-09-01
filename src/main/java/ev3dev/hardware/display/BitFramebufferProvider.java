package ev3dev.hardware.display;

import ev3dev.hardware.display.spi.FramebufferProvider;

/**
 * Creates new Linux BW framebuffer.
 */
public class BitFramebufferProvider implements FramebufferProvider {
    @Override
    public JavaFramebuffer createFramebuffer(String fbPath) throws IllegalArgumentException {
        return new BitFramebuffer(fbPath);
    }
}