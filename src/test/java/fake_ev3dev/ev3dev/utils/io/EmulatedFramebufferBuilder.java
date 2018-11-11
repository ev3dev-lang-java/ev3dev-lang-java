package fake_ev3dev.ev3dev.utils.io;

import com.sun.jna.NativeLong;
import ev3dev.hardware.display.ImageUtils;
import ev3dev.utils.io.NativeConstants;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static ev3dev.utils.io.NativeFramebuffer.fb_fix_screeninfo;
import static ev3dev.utils.io.NativeFramebuffer.fb_var_screeninfo;

/**
 * <p>Emulated framebuffer builder.</p>
 *
 * <p>This class allows user to configure emulated framebuffer.
 * It is required to configure at least screen size and color model.</p>
 *
 * @author Jakub Vaněk
 * @since 2.4.7
 */
public class EmulatedFramebufferBuilder {
    private float mm_per_dot = 0.5f;
    private int width;
    private int height;
    private int bpp;
    private int stride;
    private boolean zeroblack;
    private int indexRed;
    private int indexGreen;
    private int indexBlue;
    private int indexAlpha;
    private int number = 0;
    private HashMap<Integer, Integer> con2fb;

    private boolean sizeSet = false;
    private boolean colorSet = false;

    /**
     * Initialize new builder.
     */
    public EmulatedFramebufferBuilder() {
        con2fb = new HashMap<>();
    }

    /**
     * Set screen resolution in pixels.
     *
     * @param pxWidth    Screen width in pixels.
     * @param pxHeight   Screen height in pixels.
     * @param byteStride Screen scanline stride in bytes.
     */
    public EmulatedFramebufferBuilder setScreenSize(int pxWidth, int pxHeight, int byteStride) {
        width = pxWidth;
        height = pxHeight;
        stride = byteStride;
        sizeSet = true;
        return this;
    }

    /**
     * Set color model to XRGB with default EV3 color offsets.
     */
    public EmulatedFramebufferBuilder setXRGB() {
        return setXRGB(2, 1, 0, 3);
    }

    /**
     * Set color model to XRGB with specified color offsets.
     *
     * @param rInd Red color byte offset.
     * @param gInd Green color byte offset.
     * @param bInd Blue color byte offset.
     * @param aInd Alpha byte offset.
     */
    public EmulatedFramebufferBuilder setXRGB(int rInd, int gInd, int bInd, int aInd) {
        bpp = 32;
        indexRed = rInd;
        indexGreen = gInd;
        indexBlue = bInd;
        indexAlpha = aInd;
        colorSet = true;
        return this;
    }

    /**
     * Set color model to BW with specified color order.
     *
     * @param zeroIsBlack Whether zero-valued pixel is black.
     */
    public EmulatedFramebufferBuilder setBW(boolean zeroIsBlack) {
        bpp = 1;
        zeroblack = zeroIsBlack;
        colorSet = true;
        return this;
    }

    /**
     * Set framebuffer number.
     *
     * @param n Zero-based framebuffer index.
     */
    public EmulatedFramebufferBuilder setNumber(int n) {
        number = n;
        return this;
    }

    /**
     * Add new mapping from VT number to FB number.
     *
     * @param console Console number.
     * @param fb      Framebuffer number.
     */
    public EmulatedFramebufferBuilder addConsoleFramebufferMap(int console, int fb) {
        con2fb.put(console, fb);
        return this;
    }

    /**
     * Set framebuffer spatial resolution.
     *
     * @param mmPerDot Size of pixel in millimeters.
     */
    public EmulatedFramebufferBuilder setResolution(float mmPerDot) {
        mm_per_dot = mmPerDot;
        return this;
    }

    /**
     * Build new emulator from the specified values.
     *
     * @return configured EmulatedFramebuffer.
     * @throws IllegalStateException when not all required parameters were set.
     */
    public EmulatedFramebuffer build() {
        if (!sizeSet || !colorSet) {
            throw new IllegalStateException("Framebuffer size or color model not set.");
        }
        fb_fix_screeninfo fInfo = fillFixInfo();
        fb_var_screeninfo vInfo = fillVarInfo();
        BufferedImage model;
        byte[] buffer = new byte[height * stride];
        if (bpp == 1) {
            model = ImageUtils.createBWImage(width, height, stride, zeroblack, buffer);
        } else {
            int[] off = new int[]{indexRed, indexGreen, indexBlue, indexAlpha};
            model = ImageUtils.createXRGBImage(width, height, stride, off, buffer);
        }
        return new EmulatedFramebuffer(model, fInfo, vInfo, con2fb);
    }

    /**
     * Fill in fixed screen info.
     *
     * @return Filled structure.
     */
    private fb_fix_screeninfo fillFixInfo() {
        fb_fix_screeninfo info = new fb_fix_screeninfo();

        byte[] strBytes = ("Emulated#" + number).getBytes(StandardCharsets.UTF_8);
        System.arraycopy(strBytes, 0, info.id, 0, strBytes.length);
        info.smem_start = new NativeLong(0);
        info.smem_len = bufferSize();
        info.type = NativeConstants.FB_TYPE_PACKED_PIXELS;
        info.type_aux = 0;
        if (bpp == 32) {
            info.visual = NativeConstants.FB_VISUAL_TRUECOLOR;
        } else {
            if (zeroblack) {
                info.visual = NativeConstants.FB_VISUAL_MONO01;
            } else {
                info.visual = NativeConstants.FB_VISUAL_MONO10;
            }
        }
        info.xpanstep = 0;
        info.ypanstep = 0;
        info.ywrapstep = 0;
        info.line_length = stride;
        info.mmio_start = new NativeLong(0);
        info.mmio_len = bufferSize();
        info.accel = 0;
        info.capabilities = 0;
        info.write();
        return info;
    }

    /**
     * Fill in variable screen info.
     *
     * @return Filled structure.
     */
    private fb_var_screeninfo fillVarInfo() {
        fb_var_screeninfo info = new fb_var_screeninfo();
        info.xres = width;
        info.yres = height;
        info.xres_virtual = width;
        info.yres_virtual = height;
        info.xoffset = 0;
        info.yoffset = 0;
        info.bits_per_pixel = bpp;
        info.grayscale = bpp == 32 ? 0 : 1;

        info.red.offset = bpp == 32 ? indexRed * 8 : 0;
        info.red.length = bpp == 32 ? 8 : 1;
        info.red.msb_right = 0;
        info.green.offset = bpp == 32 ? indexGreen * 8 : 0;
        info.green.length = bpp == 32 ? 8 : 1;
        info.green.msb_right = 0;
        info.blue.offset = bpp == 32 ? indexBlue * 8 : 0;
        info.blue.length = bpp == 32 ? 8 : 1;
        info.blue.msb_right = 0;
        info.transp.offset = bpp == 32 ? indexAlpha * 8 : 0;
        info.transp.length = 0;
        info.transp.msb_right = 0;

        info.nonstd = 0;
        info.activate = 0;

        info.height = (int) (mm_per_dot * height);
        info.width = (int) (mm_per_dot * width);

        info.accel_flags = 0;
        info.pixclock = 0;
        info.left_margin = 0;
        info.right_margin = 0;
        info.upper_margin = 0;
        info.lower_margin = 0;
        info.hsync_len = 0;
        info.vsync_len = 0;

        info.sync = 0;
        info.vmode = 0;
        info.rotate = 0;
        info.colorspace = 0;
        info.write();
        return info;
    }

    /**
     * Calculate the screen buffer size.
     *
     * @return Scanline stride multiplied by height.
     */
    private int bufferSize() {
        return stride * height;
    }
}
