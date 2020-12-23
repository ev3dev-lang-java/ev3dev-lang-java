package ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;

import java.nio.Buffer;

/**
 * Native library bindings for standard C library
 *
 * @author leJOS, Jakub Vaněk
 * @since 2.4.7
 */
public class NativeLibc implements ILibc {
    private static boolean initialized = false;

    /**
     * Constructor
     */
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
    public native int fcntl(int fd, int cmd, int arg) throws LastErrorException;

    // ioctls
    public native int ioctl(int fd, int cmd, int arg) throws LastErrorException;

    public native int ioctl(int fd, int cmd, Pointer arg) throws LastErrorException;

    // open/close
    public native int open(String path, int flags, int mode) throws LastErrorException;

    public native int close(int fd) throws LastErrorException;

    // read/write
    public native int write(int fd, Buffer buffer, int count) throws LastErrorException;

    public native int read(int fd, Buffer buffer, int count) throws LastErrorException;

    // map/unmap
    public native Pointer mmap(Pointer addr, NativeLong len, int prot, int flags, int fd, NativeLong off)
        throws LastErrorException;

    public native int munmap(Pointer addr, NativeLong len) throws LastErrorException;

    public native int msync(Pointer addr, NativeLong len, int flags) throws LastErrorException;
}
