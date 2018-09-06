package fake_ev3dev.ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import ev3dev.hardware.display.ImageUtils;
import ev3dev.utils.io.NativeConstants;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static ev3dev.utils.io.NativeFramebuffer.*;

/**
 * <p>Emulated Linux framebuffer device.</p>
 *
 * <p>This device implements basic framebuffer interface.
 * No acceleration features are available.</p>
 *
 * @author Jakub Vaněk
 * @since 2.4.7
 */
public class EmulatedFramebuffer implements ICounter {
    /**
     * Size of one pixel.
     */
    private static final float mm_per_dot = 0.5f;
    private int width;
    private int height;
    private int bpp;
    private int stride;
    private Pointer memory;
    private BufferedImage imageView;
    private boolean zeroblack;
    private int indexRed;
    private int indexGreen;
    private int indexBlue;
    private int indexAlpha;
    private int number;
    private HashMap<Integer, Integer> con2fb;
    private fb_var_screeninfo varinfo;
    private fb_fix_screeninfo fixinfo;

    /**
     * List of rendered images.
     */
    @Getter
    private List<BufferedImage> snapshots;
    /**
     * Count of mmap calls with wrong flags set.
     */
    @Getter
    private int countMmapBadFlags;
    /**
     * Count of mmap call with wrong protocol set.
     */
    @Getter
    private int countMmapBadProt;


    /**
     * Initialize new emulated framebuffer.
     *
     * @param width  Screen width.
     * @param height Screen height.
     * @param bpp    Bits per screen pixel.
     * @param stride Screen scanline stride.
     */
    public EmulatedFramebuffer(int width, int height, int bpp, int stride) {
        this(width, height, bpp, stride, false, 2, 1, 0, 3);
    }

    /**
     * Initialize new emulated framebuffer.
     *
     * @param width     Screen width.
     * @param height    Screen height.
     * @param bpp       Bits per screen pixel.
     * @param stride    Screen scanline stride.
     * @param zeroblack Whether pixel with value zero is black (1bpp only)
     */
    public EmulatedFramebuffer(int width, int height, int bpp, int stride, boolean zeroblack) {
        this(width, height, bpp, stride, zeroblack, 2, 1, 0, 3);
    }

    /**
     * Initialize new emulated framebuffer.
     *
     * @param width  Screen width.
     * @param height Screen height.
     * @param bpp    Bits per screen pixel.
     * @param stride Screen scanline stride.
     * @param rInd   Index of red component (32bpp only)
     * @param gInd   Index of green component (32bpp only)
     * @param bInd   Index of blue component (32bpp only)
     * @param aInd   Index of alpha component (32bpp only)
     */
    public EmulatedFramebuffer(int width, int height, int bpp, int stride,
                               int rInd, int gInd, int bInd, int aInd) {
        this(width, height, bpp, stride, false, rInd, gInd, bInd, aInd);
    }

    /**
     * Initialize new emulated framebuffer.
     *
     * @param width     Screen width.
     * @param height    Screen height.
     * @param bpp       Bits per screen pixel.
     * @param stride    Screen scanline stride.
     * @param zeroblack Whether pixel with value zero is black (1bpp only)
     * @param rInd      Index of red component (32bpp only)
     * @param gInd      Index of green component (32bpp only)
     * @param bInd      Index of blue component (32bpp only)
     * @param aInd      Index of alpha component (32bpp only)
     */
    public EmulatedFramebuffer(int width, int height, int bpp, int stride, boolean zeroblack,
                               int rInd, int gInd, int bInd, int aInd) {
        this.zeroblack = zeroblack;
        this.width = width;
        this.height = height;
        this.bpp = bpp;
        this.stride = stride;
        this.indexRed = rInd;
        this.indexGreen = gInd;
        this.indexBlue = bInd;
        this.indexAlpha = aInd;
        this.memory = new Memory(bufferSize());
        byte[] buffer = new byte[height * stride];
        if (bpp == 1) {
            imageView = ImageUtils.createBWImage(width, height, stride, zeroblack, buffer);
        } else if (bpp == 32) {
            int[] off = new int[]{rInd, gInd, bInd, aInd};
            imageView = ImageUtils.createXRGBImage(width, height, stride, off, buffer);
        } else {
            throw new IllegalArgumentException("Only supported are 1bpp and 32bpp images");
        }
        this.fixinfo = new fb_fix_screeninfo();
        this.varinfo = new fb_var_screeninfo();
        this.con2fb = new HashMap<>();
        this.snapshots = new LinkedList<>();
        fillFixInfo(fixinfo);
        fillVarInfo(varinfo);
        resetCount();
    }

    /**
     * Set this framebuffer number.
     *
     * @param n Zero-based number of this framebuffer.
     */
    public void setNumber(int n) {
        this.number = n;
    }

    /**
     * Add new VT-FB mapping.
     *
     * @param console Console number.
     * @param fb      Associated framebuffer number.
     */
    public void addMapping(int console, int fb) {
        con2fb.put(console, fb);
    }

    /**
     * Fill in fixed screen info.
     *
     * @param info Structure to fill.
     */
    private void fillFixInfo(fb_fix_screeninfo info) {
        long peer = Pointer.nativeValue(memory);
        byte[] strBytes = ("Emulated#" + number).getBytes(StandardCharsets.UTF_8);
        System.arraycopy(strBytes, 0, info.id, 0, strBytes.length);
        info.smem_start = new NativeLong(peer);
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
        info.mmio_start = new NativeLong(peer);
        info.mmio_len = bufferSize();
        info.accel = 0;
        info.capabilities = 0;
        info.write();
    }

    /**
     * Fill in variable screen info.
     *
     * @param info Structure to fill.
     */
    private void fillVarInfo(fb_var_screeninfo info) {
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
    }

    /**
     * Process variable info set request.
     *
     * @param info Structure from the application.
     * @throws LastErrorException EINVAL if the configured parameters do not meet the initial ones.
     */
    private void takeVarInfo(fb_var_screeninfo info) throws LastErrorException {
        if (!info.dataEquals(varinfo)) {
            throw new LastErrorException(NativeConstants.EINVAL);
        }
    }


    @Override
    public void resetCount() {
        countMmapBadProt = 0;
        countMmapBadFlags = 0;
    }

    /**
     * Open a new file descriptor. [ILLEGAL OPERATION ON EMULATED FILES]
     *
     * @param path  Not used.
     * @param flags Not used.
     * @param mode  Not used.
     * @return Nothing.
     * @throws UnsupportedOperationException always
     */
    @Override
    public int open(String path, int flags, int mode) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This should not be called");
    }

    /**
     * Signal the opening of a new file descriptor.
     *
     * @param fd    Associated file descriptor number, provided by the EmulatedLibc class.
     * @param path  Path to the emulated file, provided by the tested code.
     * @param flags Opening flags, provided by the tested code.
     * @param mode  Opening mode, provided by the tested code.
     * @return File descriptor number.
     */
    @Override
    public int open(int fd, String path, int flags, int mode) {
        return fd;
    }

    /**
     * File descriptor control. [UNUSED OPERATION]
     *
     * @param fd  Not used.
     * @param cmd Not used.
     * @param arg Not used.
     * @return Nothing.
     * @throws UnsupportedOperationException always
     */
    @Override
    public int fcntl(int fd, int cmd, int arg) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This is not implemented");
    }

    /**
     * Invoke i/o control action with integer argument [UNUSED OPERATION].
     *
     * @param fd  Not used.
     * @param cmd Not used.
     * @param arg Not used.
     * @return Nothing.
     * @throws LastErrorException EINVAL always.
     */
    @Override
    public int ioctl(int fd, int cmd, int arg) throws LastErrorException {
        throw new LastErrorException(NativeConstants.EINVAL);
    }

    /**
     * Invoke i/o control action with pointer argument.
     *
     * @param fd  Not used.
     * @param cmd IO control command.
     * @param arg IO control argument ({@link fb_con2fbmap}, {@link fb_fix_screeninfo}, {@link fb_var_screeninfo})
     * @return Zero.
     * @throws LastErrorException EINVAL when the argument or command is invalid.
     */
    @Override
    public int ioctl(int fd, int cmd, Pointer arg) throws LastErrorException {
        fb_con2fbmap map;
        byte[] buf;
        fb_var_screeninfo vInfo;
        switch (cmd) {
            case NativeConstants.FBIOGET_CON2FBMAP:
                map = new fb_con2fbmap(arg);
                if (!con2fb.containsKey(map.console)) {
                    throw new LastErrorException(NativeConstants.EINVAL);
                }
                map.framebuffer = con2fb.get(map.console);
                map.write();
                break;
            case NativeConstants.FBIOGET_FSCREENINFO:
                buf = fixinfo.getPointer().getByteArray(0, fixinfo.size());
                arg.write(0, buf, 0, buf.length);
                break;
            case NativeConstants.FBIOGET_VSCREENINFO:
                buf = varinfo.getPointer().getByteArray(0, varinfo.size());
                arg.write(0, buf, 0, buf.length);
                break;
            case NativeConstants.FBIOPUT_VSCREENINFO:
                vInfo = new fb_var_screeninfo(arg);
                takeVarInfo(vInfo);
                break;
            default:
                throw new LastErrorException(NativeConstants.EINVAL);
        }
        return 0;
    }

    /**
     * Close the framebuffer file descriptor.
     *
     * @param fd Not used.
     * @return Zero.
     */
    @Override
    public int close(int fd) {
        return 0;
    }

    /**
     * Write bytes to the framebuffer [INVALID OPERATION].
     *
     * @param fd     Not used.
     * @param buffer Not used.
     * @param count  Not used.
     * @return Nothing.
     * @throws LastErrorException EIO always.
     */
    @Override
    public int write(int fd, Buffer buffer, int count) throws LastErrorException {
        throw new LastErrorException(NativeConstants.EIO);
    }

    /**
     * Read bytes from the framebuffer [INVALID OPERATION].
     *
     * @param fd     Not used.
     * @param buffer Not used.
     * @param count  Not used.
     * @return Nothing.
     * @throws LastErrorException EIO always.
     */
    @Override
    public int read(int fd, Buffer buffer, int count) throws LastErrorException {
        throw new LastErrorException(NativeConstants.EIO);
    }

    /**
     * Give a framebuffer memory pointer to the application.
     *
     * @param addr   Not used.
     * @param natLen Length of the memory to be mapped.
     * @param prot   Mapping protocol for verification.
     * @param flags  Mapping flags for verification.
     * @param fd     Not used.
     * @param natOff Mapping offset.
     * @return Address to the mapped memory.
     */
    @Override
    public Pointer mmap(Pointer addr, NativeLong natLen, int prot, int flags, int fd, NativeLong natOff) {
        long len = natLen.longValue();
        long off = natOff.longValue();
        if ((prot & NativeConstants.PROT_WRITE) == 0 || (prot & NativeConstants.PROT_READ) == 0) {
            countMmapBadProt++;
        }
        if ((flags & NativeConstants.MAP_SHARED) == 0) {
            countMmapBadFlags++;
        }
        return memory.share(off, len);
    }

    /**
     * Unmap the mapped framebuffer memory (noop).
     *
     * @param addr Not used.
     * @param len  Not used.
     * @return Zero.
     */
    @Override
    public int munmap(Pointer addr, NativeLong len) {
        return 0;
    }

    /**
     * Save the rendered image.
     *
     * @param addr  Not used.
     * @param len   Not used.
     * @param flags Not used.
     * @return Zero.
     */
    @Override
    public int msync(Pointer addr, NativeLong len, int flags) {
        transferImage();

        BufferedImage snap = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gfx = snap.createGraphics();
        gfx.drawImage(imageView, 0, 0, null);
        gfx.dispose();
        snapshots.add(snap);
        return 0;
    }

    /**
     * Calculate the screen buffer size.
     *
     * @return Scanline stride multiplied by height.
     */
    private int bufferSize() {
        return stride * height;
    }

    /**
     * Tranfer image from {@link EmulatedFramebuffer#memory} to {@link EmulatedFramebuffer#imageView}.
     */
    private void transferImage() {
        byte[] buffer = ImageUtils.getImageBytes(imageView);
        memory.read(0, buffer, 0, bufferSize());
    }
}
