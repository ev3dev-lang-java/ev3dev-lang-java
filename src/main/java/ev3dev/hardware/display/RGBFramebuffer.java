package ev3dev.hardware.display;

import com.sun.jna.LastErrorException;
import ev3dev.utils.io.NativeFramebuffer;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ev3dev.utils.io.NativeConstants.FB_TYPE_PACKED_PIXELS;
import static ev3dev.utils.io.NativeConstants.FB_VISUAL_TRUECOLOR;

/**
 * Linux XRGB 32bpp framebuffer
 *
 * @since 2.4.7
 */
@Slf4j
public class RGBFramebuffer extends LinuxFramebuffer {
    /**
     * Create and initialize new Linux RGB framebuffer.
     *
     * @param fb   The framebuffer device (e.g. /dev/fb0)
     * @param disp Display manager (e.g. /dev/tty)
     */
    public RGBFramebuffer(@NonNull NativeFramebuffer fb, DisplayInterface disp)
        throws LastErrorException, IllegalArgumentException {

        super(fb, disp);

        if (getFixedInfo().type != FB_TYPE_PACKED_PIXELS) {
            try {
                close();
            } catch (LastErrorException e) {
                throw new RuntimeException("Cannot close framebuffer", e);
            }
            LOGGER.debug("Framebuffer uses non-packed pixels");
            throw new IllegalArgumentException("Only framebuffers with packed pixels are supported");
        }
        if (getFixedInfo().visual != FB_VISUAL_TRUECOLOR || getVariableInfo().bits_per_pixel != 32) {
            try {
                close();
            } catch (LastErrorException e) {
                throw new RuntimeException("Cannot close framebuffer", e);
            }
            LOGGER.debug("Framebuffer is not 32bpp truecolor");
            throw new IllegalArgumentException("Only framebuffers with 32bpp RGB are supported");
        }
        // taking ownership
        initializeMemory();
        setDeviceClose(true);
    }

    @Override
    public BufferedImage createCompatibleBuffer(int width, int height) {
        int stride = 4 * width;
        return createCompatibleBuffer(width, height, stride, new byte[stride * height]);
    }

    @Override
    public BufferedImage createCompatibleBuffer(int width, int height, int stride, @NonNull byte[] buffer) {
        return ImageUtils.createXRGBImage(width, height, stride, getComponentOffsets(), buffer);
    }

    /**
     * get color offsets, use the not-used-one for alpha
     *
     * @return Offsets: { R, G, B, A }
     */
    private int[] getComponentOffsets() {
        int[] offsets = new int[4];
        offsets[0] = getVariableInfo().red.toLEByteOffset();
        offsets[1] = getVariableInfo().green.toLEByteOffset();
        offsets[2] = getVariableInfo().blue.toLEByteOffset();
        List<Integer> set = Arrays.asList(0, 1, 2, 3);
        ArrayList<Integer> avail = new ArrayList<>(set);
        avail.remove((Integer) offsets[0]);
        avail.remove((Integer) offsets[1]);
        avail.remove((Integer) offsets[2]);
        offsets[3] = avail.get(0);
        return offsets;
    }

}
