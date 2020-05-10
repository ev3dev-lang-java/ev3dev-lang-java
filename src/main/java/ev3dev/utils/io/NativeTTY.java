package ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;

import static ev3dev.utils.io.NativeConstants.KDGKBMODE;
import static ev3dev.utils.io.NativeConstants.KDSETMODE;
import static ev3dev.utils.io.NativeConstants.KDSKBMODE;
import static ev3dev.utils.io.NativeConstants.VT_GETMODE;
import static ev3dev.utils.io.NativeConstants.VT_GETSTATE;
import static ev3dev.utils.io.NativeConstants.VT_RELDISP;
import static ev3dev.utils.io.NativeConstants.VT_SETMODE;

/**
 * Wrapper for basic actions on Linux VT/TTY
 *
 * @author Jakub Vaněk
 * @since 2.4.7
 */
public class NativeTTY extends NativeDevice {

    /**
     * Initialize new TTY.
     *
     * @param dname Path to TTY device.
     * @throws LastErrorException when the operation fails.
     */
    public NativeTTY(@NonNull String dname) throws LastErrorException {
        super(dname, NativeConstants.O_RDWR);
    }

    /**
     * Initialize new TTY.
     *
     * @param dname Path to TTY device.
     * @param flags Opening mode, e.g. read, write or both.
     * @throws LastErrorException when the operation fails.
     */
    public NativeTTY(@NonNull String dname, int flags) throws LastErrorException {
        super(dname, flags);
    }

    /**
     * Initialize new TTY.
     *
     * @param dname Path to TTY device.
     * @param libc  standard C library interface to be used.
     * @throws LastErrorException when the operation fails.
     */
    public NativeTTY(@NonNull String dname, @NonNull ILibc libc) throws LastErrorException {
        super(dname, NativeConstants.O_RDWR, libc);
    }

    /**
     * Initialize new TTY.
     *
     * @param dname Path to TTY device.
     * @param flags Opening mode, e.g. read, write or both.
     * @param libc  standard C library interface to be used.
     * @throws LastErrorException when the operation fails.
     */
    public NativeTTY(@NonNull String dname, int flags, @NonNull ILibc libc) throws LastErrorException {
        super(dname, flags, libc);
    }

    /**
     * Get current TTY mode. TTY mode is mostly about VT switching.
     *
     * @return TTY mode.
     * @throws LastErrorException when the operation fails.
     */
    public vt_mode getVTmode() throws LastErrorException {
        vt_mode mode = new vt_mode();
        super.ioctl(VT_GETMODE, mode.getPointer());
        mode.read();
        return mode;
    }

    /**
     * Set current TTY mode. TTY mode is mostly about VT switching.
     *
     * @param mode TTY mode.
     * @throws LastErrorException when the operation fails.
     */
    public void setVTmode(@NonNull vt_mode mode) throws LastErrorException {
        mode.write();
        super.ioctl(VT_SETMODE, mode.getPointer());
    }

    /**
     * Get current TTY state.
     *
     * @return TTY state.
     * @throws LastErrorException when the operation fails.
     */
    public vt_stat getVTstate() throws LastErrorException {
        vt_stat stat = new vt_stat();
        super.ioctl(VT_GETSTATE, stat.getPointer());
        stat.read();
        return stat;
    }

    /**
     * Get current keyboard mode.
     *
     * @return Keyboard mode (raw, transformed or off) - K_* constants.
     * @throws LastErrorException when the operation fails.
     */
    public int getKeyboardMode() throws LastErrorException {
        IntByReference kbd = new IntByReference(0);
        super.ioctl(KDGKBMODE, kbd);
        return kbd.getValue();
    }

    /**
     * Set keyboard mode.
     *
     * @param mode Keyboard mode (raw, transformed or off) - K_* constants.
     * @throws LastErrorException when the operation fails.
     */
    public void setKeyboardMode(int mode) throws LastErrorException {
        super.ioctl(KDSKBMODE, mode);
    }

    /**
     * Set console mode.
     *
     * @param mode Console mode - graphics or text mode - KD_* constants.
     * @throws LastErrorException when the operation fails.
     */
    public void setConsoleMode(int mode) throws LastErrorException {
        super.ioctl(KDSETMODE, mode);
    }

    /**
     * Signal VT switch to the kernel.
     *
     * @param mode VT switching signal - VT_* constants.
     * @throws LastErrorException when the operation fails.
     */
    public void signalSwitch(int mode) throws LastErrorException {
        super.ioctl(VT_RELDISP, mode);
    }

    /**
     * Info about an active VT.
     */
    public static class vt_stat extends Structure {
        public short v_active; /* active vt */
        public short v_signal; /* signal to send */
        public short v_state; /* vt bitmask */

        public vt_stat() {
            super(ALIGN_GNUC);
        }

        public vt_stat(Pointer p) {
            super(p, ALIGN_GNUC);
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("v_active", "v_signal", "v_state");
        }
    }

    /**
     * Info about VT configuration.
     */
    public static class vt_mode extends Structure {
        public byte mode;        /* vt mode */
        public byte waitv;        /* if set, hang on writes if not active */
        public short relsig;        /* signal to raise on release req */
        public short acqsig;        /* signal to raise on acquisition */
        public short frsig;        /* unused (set to 0) */

        public vt_mode() {
            super(ALIGN_GNUC);
        }

        public vt_mode(Pointer p) {
            super(p, ALIGN_GNUC);
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("mode", "waitv", "relsig", "acqsig", "frsig");
        }
    }

}
