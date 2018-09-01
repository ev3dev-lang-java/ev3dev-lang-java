package ev3dev.hardware.display;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static ev3dev.utils.io.NativeConstants.*;

/**
 * Linux black-and-white 1bpp framebuffer
 *
 * @since 2.4.7
 */
public class BitFramebuffer extends LinuxFramebuffer {
    /**
     * Create and initialize new Linux 1bpp framebuffer.
     *
     * @param path Path to the framebuffer device (e.g. /dev/fb0)
     */
    public BitFramebuffer(String path) throws IOException, IllegalArgumentException {
        super(path);
        if (getFixedInfo().type != FB_TYPE_PACKED_PIXELS) {
            close();
            throw new IllegalArgumentException("Only framebuffers with packed pixels are supported");
        }
        // probably duplicated, but this way we are sure
        boolean nonMono = getFixedInfo().visual != FB_VISUAL_MONO10 && getFixedInfo().visual != FB_VISUAL_MONO01;
        boolean non1bpp = getVariableInfo().bits_per_pixel != 1;
        if (nonMono || non1bpp) {
            close();
            throw new IllegalArgumentException("Only framebuffers with 1bpp BW are supported");
        }
    }

    @Override
    public BufferedImage createCompatibleBuffer(int width, int height) {
        int stride = (width + 7) / 8;
        return createCompatibleBuffer(width, height, stride, new byte[stride * height]);
    }

    @Override
    public BufferedImage createCompatibleBuffer(int width, int height, int stride, byte[] backed) {
        return ImageUtils.createBWImage(width, height, stride, getFixedInfo().visual == FB_VISUAL_MONO01, backed);
    }
}
