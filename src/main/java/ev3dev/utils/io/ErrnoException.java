package ev3dev.utils.io;

import com.sun.jna.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ErrnoException extends IOException {
    private static final int BUFFER_CAPACITY = 64;
    private static libc_err libc = new libc_err();
    private int err;

    public ErrnoException(int errno) {
        this(errno, "Native error occured");
    }

    public ErrnoException(int errno, String info) {
        super(cookMessage(errno, info));
        this.err = errno;
        LOGGER.debug("ErrnoException created, errno={}, message={}", errno, info);
    }

    public static int wrap(int retval, String what) throws ErrnoException {
        if (retval < 0) {
            throw new ErrnoException(Native.getLastError(), what);
        }
        return retval;
    }

    private static String cookMessage(int errno, String description) {
        String name = getErrorName(errno);
        return String.format("%s: %s (errno=%d)", description, name, errno);
    }

    private static String getErrorName(int errno) {
        Pointer ptr = new Memory(BUFFER_CAPACITY);
        Pointer data = libc.strerror_r(errno, ptr, new NativeLong(BUFFER_CAPACITY));
        return data.getString(0);
    }

    public int getErrno() {
        return err;
    }

    private static class libc_err {
        static {
            try {
                Native.register(Platform.C_LIBRARY_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public native Pointer strerror_r(int errno, Pointer buffer, NativeLong capacity);
    }

}
