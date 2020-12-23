package fake_ev3dev.ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import ev3dev.hardware.display.ImageUtils;
import ev3dev.utils.io.NativeConstants;
import java.awt.Graphics2D;
import lombok.Getter;

import java.awt.image.BufferedImage;
import java.nio.Buffer;
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
    private Pointer memory;
    private BufferedImage imageView;
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


    EmulatedFramebuffer(BufferedImage buffer,
                        fb_fix_screeninfo fixinfo,
                        fb_var_screeninfo varinfo,
                        HashMap<Integer, Integer> con2fb) {
        this.imageView = buffer;
        this.fixinfo = fixinfo;
        this.varinfo = varinfo;
        this.con2fb = con2fb;
        this.memory = new Memory(bufferSize());
        this.fixinfo.smem_start = new NativeLong(Pointer.nativeValue(this.memory));
        this.fixinfo.mmio_start = new NativeLong(Pointer.nativeValue(this.memory));
        this.snapshots = new LinkedList<>();
        resetCount();
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

        BufferedImage snap = new BufferedImage(varinfo.xres, varinfo.yres, BufferedImage.TYPE_INT_ARGB);
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
        return fixinfo.line_length * varinfo.yres;
    }

    /**
     * Tranfer image from {@link EmulatedFramebuffer#memory} to {@link EmulatedFramebuffer#imageView}.
     */
    private void transferImage() {
        byte[] buffer = ImageUtils.getImageBytes(imageView);
        memory.read(0, buffer, 0, bufferSize());
    }
}
