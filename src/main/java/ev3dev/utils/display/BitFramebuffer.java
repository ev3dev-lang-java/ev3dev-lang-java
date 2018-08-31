package ev3dev.utils.display;

import java.awt.image.BufferedImage;
import java.io.IOError;

import static ev3dev.utils.display.NativeFramebuffer.*;

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
    public BitFramebuffer(String path) throws IllegalArgumentException, IOError {
        super(path);
        if (getFixedInfo().type != FB_TYPE_PACKED_PIXELS) {
            close();
            throw new IllegalArgumentException("Only framebuffers with packed pixels are supported");
        }
        if (getFixedInfo().visual != FB_VISUAL_MONO10 && getFixedInfo().visual != FB_VISUAL_MONO01) {
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
