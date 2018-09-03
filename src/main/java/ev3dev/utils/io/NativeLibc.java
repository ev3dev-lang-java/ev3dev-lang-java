package ev3dev.utils.io;

import com.sun.jna.*;
import com.sun.jna.ptr.IntByReference;

import java.nio.Buffer;

/**
 * Native library bindings for standard C library
 *
 * @author leJOS, Jakub Vaněk
 * @since 2.4.7
 */
public class NativeLibc implements ILibc {
    private static boolean initialized = false;

    public NativeLibc() {
        synchronized (NativeLibc.class) {
            if (!initialized) {
                try {
                    Native.register(Platform.C_LIBRARY_NAME);
                    initialized = true;
                } catch (Exception e) {
                    throw new IllegalArgumentException("native libc load failed", e);
                }
            }
        }
    }

    // file descriptor operations
    native public int fcntl(int fd, int cmd, int arg) throws LastErrorException;

    // ioctls
    native public int ioctl(int fd, int cmd, int arg) throws LastErrorException;

    native public int ioctl(int fd, int cmd, Pointer arg) throws LastErrorException;

    // open/close
    native public int open(String path, int flags, int mode) throws LastErrorException;

    native public int close(int fd) throws LastErrorException;

    // read/write
    native public int write(int fd, Buffer buffer, int count) throws LastErrorException;

    native public int read(int fd, Buffer buffer, int count) throws LastErrorException;

    // map/unmap
    native public Pointer mmap(Pointer addr, NativeLong len, int prot, int flags, int fd, NativeLong off) throws LastErrorException;

    native public int munmap(Pointer addr, NativeLong len) throws LastErrorException;

    native public int msync(Pointer addr, NativeLong len, int flags) throws LastErrorException;
}
