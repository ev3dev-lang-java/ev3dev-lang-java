package ev3dev.hardware.display;

import com.sun.jna.LastErrorException;
import ev3dev.hardware.display.spi.FramebufferProvider;
import ev3dev.utils.io.NativeFramebuffer;

/**
 * Creates new Linux BW framebuffer.
 */
public class BitFramebufferProvider implements FramebufferProvider {

    @Override
    public JavaFramebuffer createFramebuffer(NativeFramebuffer fb, DisplayInterface disp)
        throws LastErrorException, IllegalArgumentException {

        return new BitFramebuffer(fb, disp);
    }
}
