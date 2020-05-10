package ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;

import static ev3dev.utils.io.NativeConstants.FBIOGET_CON2FBMAP;
import static ev3dev.utils.io.NativeConstants.FBIOGET_FSCREENINFO;
import static ev3dev.utils.io.NativeConstants.FBIOGET_VSCREENINFO;
import static ev3dev.utils.io.NativeConstants.FBIOPUT_VSCREENINFO;

/**
 * Linux framebuffer wrapper class
 *
 * @since 2.4.7
 */
public class NativeFramebuffer extends NativeDevice {

    /**
     * Create a native device to provide access to the specified character device
     *
     * @param dname name of the character device
     * @throws LastErrorException when operations fails
     */
    public NativeFramebuffer(@NonNull String dname) throws LastErrorException {
        super(dname);
    }

    /**
     * Create a native device to provide access to the specified character device
     *
     * @param dname name of the character device
     * @param flags Opening mode, e.g. read, write or both.
     * @throws LastErrorException when operations fails
     */
    public NativeFramebuffer(@NonNull String dname, int flags) throws LastErrorException {
        super(dname, flags);
    }

    /**
     * Create a native device to provide access to the specified character device
     *
     * @param dname name of the character device
     * @param libc  standard C library interface to be used.
     * @throws LastErrorException when operations fails
     */
    public NativeFramebuffer(@NonNull String dname, @NonNull ILibc libc) throws LastErrorException {
        super(dname, libc);
    }

    /**
     * Create a native device to provide access to the specified character device
     *
     * @param dname name of the character device
     * @param flags Opening mode, e.g. read, write or both.
     * @param libc  standard C library interface to be used.
     * @throws LastErrorException when operations fails
     */
    public NativeFramebuffer(@NonNull String dname, int flags, @NonNull ILibc libc) throws LastErrorException {
        super(dname, flags, libc);
    }

    /**
     * Fetch fixed screen info.
     *
     * @return Non-changing info about the display.
     */
    public fb_fix_screeninfo getFixedScreenInfo() throws LastErrorException {
        fb_fix_screeninfo info = new fb_fix_screeninfo();
        super.ioctl(FBIOGET_FSCREENINFO, info.getPointer());
        info.read();
        return info;
    }

    /**
     * Fetch variable screen info.
     *
     * @return Changeable info about the display.
     * @throws LastErrorException when operations fails
     */
    public fb_var_screeninfo getVariableScreenInfo() throws LastErrorException {
        fb_var_screeninfo info = new fb_var_screeninfo();
        super.ioctl(FBIOGET_VSCREENINFO, info.getPointer());
        info.read();
        return info;
    }

    /**
     * Send variable screen info.
     *
     * @param info Changeable info about the display.
     * @throws LastErrorException when operations fails
     */
    public void setVariableScreenInfo(@NonNull fb_var_screeninfo info) throws LastErrorException {
        info.write();
        super.ioctl(FBIOPUT_VSCREENINFO, info.getPointer());
    }

    /**
     * Identify which framebuffer is connected to a specified VT.
     *
     * @param console VT number.
     * @return Framebuffer number or -1 if console has no framebuffer.
     * @throws LastErrorException when operations fails
     */
    public int mapConsoleToFramebuffer(int console) throws LastErrorException {
        fb_con2fbmap map = new fb_con2fbmap();
        map.console = console;
        map.write();
        super.ioctl(FBIOGET_CON2FBMAP, map.getPointer());
        map.read();
        return map.framebuffer;
    }

    /**
     * fb_fix_screeninfo mapping
     */
    public static class fb_fix_screeninfo extends Structure {
        /**
         * identification string eg "TT Builtin"
         */
        public byte[] id = new byte[16];
        /**
         * Start of frame buffer mem (physical address)
         */
        public NativeLong smem_start;
        /**
         * Length of frame buffer mem
         */
        public int smem_len;
        /**
         * see FB_TYPE_*
         */
        public int type;
        /**
         * Interleave for interleaved Planes
         */
        public int type_aux;
        /**
         * see FB_VISUAL_*
         */
        public int visual;
        /**
         * zero if no hardware panning
         */
        public short xpanstep;
        /**
         * zero if no hardware panning
         */
        public short ypanstep;
        /**
         * zero if no hardware ywrap
         */
        public short ywrapstep;
        /**
         * length of a line in bytes
         */
        public int line_length;
        /**
         * Start of Memory Mapped I/O (physical address)
         */
        public NativeLong mmio_start;
        /**
         * Length of Memory Mapped I/O
         */
        public int mmio_len;
        /**
         * Indicate to driver which specific chip/card we have
         */
        public int accel;
        /**
         * see FB_CAP_*
         */
        public short capabilities;
        /**
         * Reserved for future compatibility
         */
        public short[] reserved = new short[2];

        /**
         * Initialize this structure.
         */
        public fb_fix_screeninfo() {
            super(ALIGN_GNUC);
        }

        public fb_fix_screeninfo(Pointer p) {
            super(p, ALIGN_GNUC);
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("id", "smem_start", "smem_len",
                    "type", "type_aux", "visual",
                    "xpanstep", "ypanstep", "ywrapstep", "line_length",
                    "mmio_start", "mmio_len", "accel", "capabilities", "reserved");
        }

        /**
         * Reference wrapper
         */
        public static class ByReference extends fb_fix_screeninfo implements Structure.ByReference {
        }

        /**
         * Value wrapper
         */
        public static class ByValue extends fb_fix_screeninfo implements Structure.ByValue {
        }
    }

    /**
     * fb_bitfield mapping
     */
    public static class fb_bitfield extends Structure {
        /**
         * beginning of bitfield
         */
        public int offset;
        /**
         * length of bitfield
         */
        public int length;
        /**
         * != 0 : Most significant bit is right
         */
        public int msb_right;

        /**
         * Initialize this structure.
         */
        public fb_bitfield() {
            super(ALIGN_GNUC);
        }

        public fb_bitfield(Pointer p) {
            super(p, ALIGN_GNUC);
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("offset", "length", "msb_right");
        }

        /**
         * Calculate little-endian byte offset from the bitshift number.
         *
         * @return Offset in byte array
         */
        public int toLEByteOffset() {
            if (length != 8) {
                throw new IllegalArgumentException("Byte offset is applicable only to 8bit sized components");
            }
            if ((offset % 8) != 0) {
                throw new IllegalArgumentException("Byte offset is applicable only to 8bit aligned components");
            }
            return offset / 8;
        }

        /**
         * Reference wrapper
         */
        public static class ByReference extends fb_bitfield implements Structure.ByReference {
        }

        /**
         * Value wrapper
         */
        public static class ByValue extends fb_bitfield implements Structure.ByValue {
        }
    }

    /**
     * fb_var_screeninfo mapping
     */
    public static class fb_var_screeninfo extends Structure {
        /**
         * visible X resolution
         */
        public int xres;
        /**
         * visible Y resolution
         */
        public int yres;
        /**
         * virtual X resolution
         */
        public int xres_virtual;
        /**
         * virtual Y resolution
         */
        public int yres_virtual;
        /**
         * offset from virtual to visible X resolution
         */
        public int xoffset;
        /**
         * offset from virtual to visible Y resolution
         */
        public int yoffset;
        /**
         * BPP value
         */
        public int bits_per_pixel;
        /**
         * 0 = color, 1 = grayscale, >1 = FOURCC
         */
        public int grayscale;
        /**
         * info about red channel
         */
        public fb_bitfield.ByValue red;
        /**
         * info about green channel
         */
        public fb_bitfield.ByValue green;
        /**
         * info about blue channel
         */
        public fb_bitfield.ByValue blue;
        /**
         * info about transparency channel
         */
        public fb_bitfield.ByValue transp;
        /**
         * != 0 Non standard pixel format
         */
        public int nonstd;
        /**
         * see FB_ACTIVATE_*
         */
        public int activate;
        /**
         * height of picture in mm
         */
        public int height;
        /**
         * width of picture in mm
         */
        public int width;
        /**
         * (OBSOLETE) see fb_info.flags
         */
        public int accel_flags;
        /**
         * pixel clock in ps (pico seconds)
         */
        public int pixclock;
        /**
         * time from sync to picture (pixclocks)
         */
        public int left_margin;
        /**
         * time from picture to sync (pixclocks)
         */
        public int right_margin;
        /**
         * time from sync to picture (pixclocks)
         */
        public int upper_margin;
        /**
         * time from sync to picture (pixclocks)
         */
        public int lower_margin;
        /**
         * length of horizontal sync (pixclocks)
         */
        public int hsync_len;
        /**
         * length of vertical sync (pixclocks)
         */
        public int vsync_len;
        /**
         * see FB_SYNC_*
         */
        public int sync;
        /**
         * see FB_VMODE_*
         */
        public int vmode;
        /**
         * angle we rotate counter clockwise
         */
        public int rotate;
        /**
         * colorspace for FOURCC-based modes
         */
        public int colorspace;
        /**
         * Reserved for future compatibility
         */
        public int[] reserved = new int[4];

        /**
         * Initialize this structure.
         */
        public fb_var_screeninfo() {
            super(ALIGN_GNUC);
        }

        public fb_var_screeninfo(Pointer p) {
            super(p, ALIGN_GNUC);
        }


        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("xres", "yres", "xres_virtual", "yres_virtual",
                    "xoffset", "yoffset", "bits_per_pixel", "grayscale",
                    "red", "green", "blue", "transp", "nonstd", "activate",
                    "height", "width", "accel_flags", "pixclock",
                    "left_margin", "right_margin", "upper_margin", "lower_margin",
                    "hsync_len", "vsync_len", "sync", "vmode", "rotate", "colorspace", "reserved");
        }

        /**
         * Reference wrapper
         */
        public static class ByReference extends fb_var_screeninfo implements Structure.ByReference {
        }

        /**
         * Value wrapper
         */
        public static class ByValue extends fb_var_screeninfo implements Structure.ByValue {
        }
    }

    public static class fb_con2fbmap extends Structure {
        public int console;
        public int framebuffer;

        public fb_con2fbmap() {
            super(ALIGN_GNUC);
        }

        public fb_con2fbmap(Pointer p) {
            super(p, ALIGN_GNUC);
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("console", "framebuffer");
        }
    }
}
