package ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

import java.util.Arrays;
import java.util.List;

import static ev3dev.utils.io.NativeConstants.*;

public class NativeTTY extends NativeDevice {

    public NativeTTY(String dname) throws LastErrorException {
        super(dname, NativeConstants.O_RDWR);
    }

    public NativeTTY(String dname, int flags) throws LastErrorException {
        super(dname, flags);
    }

    public vt_mode getVTmode() throws LastErrorException {
        vt_mode mode = new vt_mode();
        super.ioctl(VT_GETMODE, mode.getPointer());
        mode.read();
        return mode;
    }

    public void setVTmode(vt_mode mode) throws LastErrorException {
        mode.write();
        super.ioctl(VT_SETMODE, mode.getPointer());
    }

    public vt_stat getVTstate() throws LastErrorException {
        vt_stat stat = new vt_stat();
        super.ioctl(VT_GETSTATE, stat.getPointer());
        stat.read();
        return stat;
    }

    public int getKeyboardMode() throws LastErrorException {
        IntByReference kbd = new IntByReference(0);
        super.ioctl(KDGKBMODE, kbd);
        return kbd.getValue();
    }

    public void setKeyboardMode(int mode) throws LastErrorException {
        super.ioctl(KDSKBMODE, mode);
    }

    public void setConsoleMode(int mode) throws LastErrorException {
        super.ioctl(KDSETMODE, mode);
    }

    public void signalSwitch(int mode) throws LastErrorException {
        super.ioctl(VT_RELDISP, mode);
    }

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
