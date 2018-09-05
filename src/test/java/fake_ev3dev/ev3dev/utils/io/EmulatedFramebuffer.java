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

public class EmulatedFramebuffer implements ICounter {
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

    @Getter
    private List<BufferedImage> snapshots;
    @Getter
    private int countMmapBadFlags;
    @Getter
    private int countMmapBadProt;


    public EmulatedFramebuffer(int width, int height, int bpp, int stride) {
        this(width, height, bpp, stride, false, 2, 1, 0, 3);
    }

    public EmulatedFramebuffer(int width, int height, int bpp, int stride, boolean zeroblack) {
        this(width, height, bpp, stride, zeroblack, 2, 1, 0, 3);
    }

    public EmulatedFramebuffer(int width, int height, int bpp, int stride,
                               int rInd, int gInd, int bInd, int aInd) {
        this(width, height, bpp, stride, false, rInd, gInd, bInd, aInd);
    }

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

    private void transferImage() {
        byte[] buffer = ImageUtils.getImageBytes(imageView);
        memory.read(0, buffer, 0, bufferSize());
    }

    public void setNumber(int n) {
        this.number = n;
    }

    public void addMapping(int console, int fb) {
        con2fb.put(console, fb);
    }

    @Override
    public void resetCount() {
        countMmapBadProt = 0;
        countMmapBadFlags = 0;
    }

    @Override
    public int open(String path, int flags, int mode) throws LastErrorException {
        throw new UnsupportedOperationException("This should not be called");
    }

    @Override
    public int open(int fd, String path, int flags, int mode) throws LastErrorException {
        return fd;
    }

    @Override
    public int fcntl(int fd, int cmd, int arg) throws LastErrorException {
        throw new UnsupportedOperationException("This is not implemented");
    }

    @Override
    public int ioctl(int fd, int cmd, int arg) throws LastErrorException {
        throw new LastErrorException(NativeConstants.EINVAL);
    }

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

    private void takeVarInfo(fb_var_screeninfo info) {
        if (!info.dataEquals(varinfo)) {
            throw new LastErrorException(NativeConstants.EINVAL);
        }
    }

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

    @Override
    public int close(int fd) throws LastErrorException {
        return 0;
    }

    @Override
    public int write(int fd, Buffer buffer, int count) throws LastErrorException {
        throw new LastErrorException(NativeConstants.EIO);
    }

    @Override
    public int read(int fd, Buffer buffer, int count) throws LastErrorException {
        throw new LastErrorException(NativeConstants.EIO);
    }

    @Override
    public Pointer mmap(Pointer addr, NativeLong natLen, int prot, int flags, int fd, NativeLong natOff) throws LastErrorException {
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

    @Override
    public int munmap(Pointer addr, NativeLong len) throws LastErrorException {
        return 0;
    }

    @Override
    public int msync(Pointer addr, NativeLong len, int flags) throws LastErrorException {
        transferImage();

        BufferedImage snap = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gfx = snap.createGraphics();
        gfx.drawImage(imageView, 0, 0, null);
        gfx.dispose();
        snapshots.add(snap);
        return 0;
    }

    private int bufferSize() {
        return stride * height;
    }
}
