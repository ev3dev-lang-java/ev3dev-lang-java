package ev3dev.hardware.display;

import ev3dev.hardware.display.spi.FramebufferProvider;
import ev3dev.utils.io.NativeFramebuffer;

import java.io.IOException;

/**
 * Creates new Linux BW framebuffer.
 */
public class BitFramebufferProvider implements FramebufferProvider {
    @Override
    public JavaFramebuffer createFramebuffer(NativeFramebuffer fb) throws IOException, IllegalArgumentException {
        return new BitFramebuffer(fb);
    }
}
