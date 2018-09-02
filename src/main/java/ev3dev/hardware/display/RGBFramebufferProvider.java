package ev3dev.hardware.display;

import ev3dev.hardware.display.spi.FramebufferProvider;
import ev3dev.utils.io.NativeFramebuffer;

import java.io.IOException;

/**
 * Creates new Linux RGB framebuffer.
 */
public class RGBFramebufferProvider implements FramebufferProvider {
    @Override
    public JavaFramebuffer createFramebuffer(NativeFramebuffer fb) throws IOException, IllegalArgumentException {
        return new RGBFramebuffer(fb);
    }
}
