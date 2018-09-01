package ev3dev.utils.io;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import java.io.Closeable;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * <p>This class provides access to Linux files using native I/O operations. It is
 * implemented using the JNA package. The class is required because certain
 * operations (like ioctl) that are required by the Lego kernel module interface are
 * not support by standard Java methods. In addition standard Java memory mapped
 * files do not seem to function correctly when used with Linux character devices.</p>
 *
 * <p>Only JNA is used, the original interface used combination of Java and JNA.</p>
 *
 * @author andy, Jakub Vaněk
 */
public class NativeFile implements Closeable, AutoCloseable {
    private static libc_main libc = new libc_main();
    protected int fd = -1;

    protected NativeFile() {

    }

    /**
     * Create a NativeFile object and open the associated file/device
     * for native access.
     *
     * @param fname the name of the file to open
     * @param flags Linux style file access flags
     * @throws ErrnoException when operations fails
     */
    public NativeFile(String fname, int flags) throws ErrnoException {
        open(fname, flags);
    }

    /**
     * Create a NativeFile object and open the associated file/device
     * for native access.
     *
     * @param fname the name of the file to open
     * @param flags Linux style file access flags
     * @param mode  Linux style file access mode
     * @throws ErrnoException when operations fails
     */
    public NativeFile(String fname, int flags, int mode) throws ErrnoException {
        open(fname, flags, mode);
    }

    /**
     * Open the specified file/device for native access.
     *
     * @param fname the name of the file to open
     * @param flags Linux style file access flags
     * @throws ErrnoException when operations fails
     */
    public void open(String fname, int flags) throws ErrnoException {
        fd = ErrnoException.wrap(libc.open(fname, flags),
                "open(" + fname + ") failed");
    }

    /**
     * Open the specified file/device for native access.
     *
     * @param fname the name of the file to open
     * @param flags Linux style file access flags
     * @param mode  Linux style file access mode
     * @throws ErrnoException when operations fails
     */
    public void open(String fname, int flags, int mode) throws ErrnoException {
        fd = ErrnoException.wrap(libc.open(fname, flags, mode),
                "open(" + fname + ") failed");
    }

    /**
     * Attempt to read the requested number of bytes from the associated file.
     *
     * @param buf location to store the read bytes
     * @param len number of bytes to attempt to read
     * @return number of bytes read
     * @throws ErrnoException when operations fails
     */
    public int read(byte[] buf, int len) throws ErrnoException {
        return ErrnoException.wrap(libc.read(fd, ByteBuffer.wrap(buf), len),
                "read() failed");
    }

    /**
     * Attempt to write the requested number of bytes to the associated file.
     *
     * @param buf    location to store the read bytes
     * @param offset the offset within buf to take data from for the write
     * @param len    number of bytes to attempt to read
     * @return number of bytes read
     * @throws ErrnoException when operations fails
     */
    public int write(byte[] buf, int offset, int len) throws ErrnoException {
        return ErrnoException.wrap(libc.write(fd, ByteBuffer.wrap(buf, offset, len), len),
                "write() failed");
    }

    /**
     * Attempt to read the requested number of byte from the associated file.
     *
     * @param buf    location to store the read bytes
     * @param offset offset with buf to start storing the read bytes
     * @param len    number of bytes to attempt to read
     * @return number of bytes read
     * @throws ErrnoException when operations fails
     */
    public int read(byte[] buf, int offset, int len) throws ErrnoException {
        return ErrnoException.wrap(libc.read(fd, ByteBuffer.wrap(buf, offset, len), len),
                "read() failed");
    }

    /**
     * Attempt to write the requested number of bytes to the associated file.
     *
     * @param buf location to store the read bytes
     * @param len number of bytes to attempt to read
     * @return number of bytes read
     * @throws ErrnoException when operations fails
     */
    public int write(byte[] buf, int len) throws ErrnoException {
        return ErrnoException.wrap(libc.write(fd, ByteBuffer.wrap(buf), len),
                "write() failed");
    }

    /**
     * Perform a Linux style ioctl operation on the associated file.
     *
     * @param req  ioctl operation to be performed
     * @param info output as integer
     * @return Linux style ioctl return
     * @throws ErrnoException when operations fails
     */
    public int ioctl(int req, IntByReference info) throws ErrnoException {
        return ErrnoException.wrap(libc.ioctl(fd, req, info),
                "ioctl(" + req + ") failed");
    }

    /**
     * Perform a Linux style ioctl operation on the associated file.
     *
     * @param req  ioctl operation to be performed
     * @param info input as integer
     * @return Linux style ioctl return
     * @throws ErrnoException when operations fails
     */
    public int ioctl(int req, int info) throws ErrnoException {
        return ErrnoException.wrap(libc.ioctl(fd, req, info),
                "ioctl(" + req + ") failed");
    }

    /**
     * Perform a Linux style ioctl operation on the associated file.
     *
     * @param req ioctl operation to be performed
     * @param buf pointer to ioctl parameters
     * @return Linux style ioctl return
     * @throws ErrnoException when operations fails
     */
    public int ioctl(int req, Pointer buf) throws ErrnoException {
        return ErrnoException.wrap(libc.ioctl(fd, req, buf),
                "ioctl(" + req + ") failed");
    }

    /**
     * Perform a Linux style ioctl operation on the associated file.
     *
     * @param req ioctl operation to be performed
     * @param buf byte array containing the ioctl parameters if any
     * @return Linux style ioctl return
     * @throws ErrnoException when operations fails
     */
    public int ioctl(int req, byte[] buf) throws ErrnoException {
        return ErrnoException.wrap(libc.ioctl(fd, req, buf),
                "ioctl(" + req + ") failed");
    }

    /**
     * Close the associated file
     *
     * @throws ErrnoException when operations fails
     */
    @Override
    public void close() throws ErrnoException {
        if (fd != -1) {
            int copy = fd;
            fd = -1;
            ErrnoException.wrap(libc.close(copy), "close() failed");
        }
    }

    /**
     * Map a portion of the associated file into memory and return a pointer
     * that can be used to access that memory.
     *
     * @param len   size of the region to map
     * @param prot  protection for the memory region
     * @param flags Linux mmap flags
     * @param off   offset within the file for the start of the region
     * @return a pointer that can be used to access the mapped data
     * @throws ErrnoException when operations fails
     */
    public Pointer mmap(long len, int prot, int flags, long off) throws ErrnoException {
        Pointer p = libc.mmap(new Pointer(0), new NativeLong(len), prot, flags, fd, new NativeLong(off));
        if (p.equals(new Pointer(-1))) {
            throw new ErrnoException(Native.getLastError(), "mmap() failed");
        }
        return p;
    }

    /**
     * Unmap mapped memory region.
     *
     * @param addr Mapped address.
     * @param len  Region length.
     * @throws ErrnoException when operations fails
     */
    public void munmap(Pointer addr, long len) throws ErrnoException {
        ErrnoException.wrap(libc.munmap(addr, new NativeLong(len)), "munmap() failed");
    }

    /**
     * Basic POSIX C Library bindings for file operations
     */
    private static class libc_main {
        static {
            try {
                Native.register(Platform.C_LIBRARY_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // file descriptor operations
        native public int fcntl(int fd, int cmd, int arg);

        // ioctls
        native public int ioctl(int fd, int cmd, int arg);

        native public int ioctl(int fd, int cmd, byte[] arg);

        native public int ioctl(int fd, int cmd, Pointer arg);

        native public int ioctl(int fd, int cmd, IntByReference arg);

        // open/close
        native public int open(String path, int flags);

        native public int open(String path, int flags, int mode);

        native public int close(int fd);

        // read/write
        native public int write(int fd, Buffer buffer, int count);

        native public int read(int fd, Buffer buffer, int count);

        // map/unmap
        native public Pointer mmap(Pointer addr, NativeLong len, int prot, int flags, int fd, NativeLong off);

        native public int munmap(Pointer addr, NativeLong len);
    }

}
