package ev3dev.utils.display;

import ev3dev.utils.display.BitFramebuffer;
import ev3dev.utils.display.JavaFramebuffer;
import ev3dev.utils.display.spi.FramebufferProvider;

/**
 * Creates new Linux BW framebuffer.
 */
public class BitFramebufferProvider implements FramebufferProvider {
    @Override
    public JavaFramebuffer createFramebuffer(String fbPath) {
        return new BitFramebuffer(fbPath);
    }
}
