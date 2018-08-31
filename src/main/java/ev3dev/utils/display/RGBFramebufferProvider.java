package ev3dev.utils.display;

import ev3dev.utils.display.JavaFramebuffer;
import ev3dev.utils.display.RGBFramebuffer;
import ev3dev.utils.display.spi.FramebufferProvider;

/**
 * Creates new Linux RGB framebuffer.
 */
public class RGBFramebufferProvider implements FramebufferProvider {
    @Override
    public JavaFramebuffer createFramebuffer(String fbPath) throws IllegalArgumentException {
        return new RGBFramebuffer(fbPath);
    }
}
