package ev3dev.utils.io;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import ev3dev.hardware.display.NativeFramebuffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.Buffer;
import java.nio.channels.FileChannel;

/**
 * This class provides access to Linux files using native I/O operations. It is
 * implemented using the JNA package. The class is required because certain
 * operations (like ioctl) that are required by the Lego kernel module interface are
 * not support by standard Java methods. In addition standard Java memory mapped
 * files do not seem to function correctly when used with Linux character devices.
 * <p><p>
 * Where possible standard Java classes and methods are used. JNA is used to extended
 * the standard Java interface as required.
 *
 * @author andy
 */
public class NativeFile {
    static final int O_ACCMODE = 0003;
    static final int O_RDONLY = 00;
    static final int O_WRONLY = 01;
    static final int O_RDWR = 02;
    static final int O_CREAT = 0100;
    static final int O_EXCL = 0200;
    static final int O_NOCTTY = 0400;
    static final int O_TRUNC = 01000;
    static final int O_APPEND = 02000;
    static final int O_NONBLOCK = 04000;
    static final int O_NDELAY = O_NONBLOCK;
    static final int O_SYNC = 04010000;
    static final int O_FSYNC = O_SYNC;
    static final int O_ASYNC = 020000;
    static final int PROT_READ = 1;
    static final int PROT_WRITE = 2;
    static final int MAP_SHARED = 1;
    static final int MAP_PRIVATE = 2;
    static final int MAP_FILE = 0;



    private static class Linux_C_lib_DirectMapping {
        native public int fcntl(int fd, int cmd, int arg);
        native public int ioctl(int fd, int cmd, byte[] arg);
        native public int ioctl(int fd, int cmd, Pointer arg);
        native public int ioctl(int fd, int cmd, NativeFramebuffer.fb_fix_screeninfo p);
        native public int ioctl(int fd, int cmd, NativeFramebuffer.fb_var_screeninfo p);
        native public int open(String path, int flags);
        native public int close(int fd);
        native public int write(int fd, Buffer buffer, int count);
        native public int read(int fd, Buffer buffer, int count);
        native public Pointer mmap(Pointer addr, NativeLong len, int prot, int flags, int fd, NativeLong off);
        native public int munmap(Pointer addr, NativeLong len);

        static {
            try {
                Native.register(Platform.C_LIBRARY_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static Linux_C_lib_DirectMapping clib = new Linux_C_lib_DirectMapping();

    protected int fd;
    protected RandomAccessFile jfd;
    protected FileChannel fc;

    protected NativeFile() {

    }

    /**
     * Create a NativeFile object and open the associated file/device
     * for native access.
     *
     * @param fname the name of the file to open
     * @param flags Linux style file access flags
     * @param mode  Linux style file access mode
     * @throws FileNotFoundException
     */
    public NativeFile(String fname, int flags, int mode) throws FileNotFoundException {
        open(fname, flags, mode);
    }

    /**
     * Open the specified file/device
     * for native access.
     *
     * @param fname the name of the file to open
     * @param flags Linux style file access flags
     * @param mode  Linux style file access mode
     * @throws FileNotFoundException
     */
    public void open(String fname, int flags, int mode) throws FileNotFoundException {
        fd = clib.open(fname, flags);
        if (fd < 0)
            throw new FileNotFoundException("File: " + fname + " errno " + Native.getLastError());
        jfd = new RandomAccessFile(fname, "rw");
        fc = jfd.getChannel();
    }

    /**
     * Attempt to read the requested number of bytes from the associated file.
     *
     * @param buf location to store the read bytes
     * @param len number of bytes to attempt to read
     * @return number of bytes read or -1 if there is an error
     */
    public int read(byte[] buf, int len) {
        try {
            return fc.read(ByteBuffer.wrap(buf, 0, len));
        } catch (IOException e) {
            return -1;
        }
    }

    /**
     * Attempt to write the requested number of bytes to the associated file.
     *
     * @param buf    location to store the read bytes
     * @param offset the offset within buf to take data from for the write
     * @param len    number of bytes to attempt to read
     * @return number of bytes read or -1 if there is an error
     */
    public int write(byte[] buf, int offset, int len) {
        try {
            return fc.write(ByteBuffer.wrap(buf, offset, len));
        } catch (IOException e) {
            return -1;
        }
    }

    /**
     * Attempt to read the requested number of byte from the associated file.
     *
     * @param buf    location to store the read bytes
     * @param offset offset with buf to start storing the read bytes
     * @param len    number of bytes to attempt to read
     * @return number of bytes read or -1 if there is an error
     */
    public int read(byte[] buf, int offset, int len) {
        try {
            return fc.read(ByteBuffer.wrap(buf, offset, len));
        } catch (IOException e) {
            return -1;
        }
    }

    /**
     * Attempt to write the requested number of bytes to the associated file.
     *
     * @param buf location to store the read bytes
     * @param len number of bytes to attempt to read
     * @return number of bytes read or -1 if there is an error
     */
    public int write(byte[] buf, int len) {
        try {
            return fc.write(ByteBuffer.wrap(buf, 0, len));
        } catch (IOException e) {
            return -1;
        }
    }

    /**
     * Perform a Linux style ioctl operation on the associated file.
     *
     * @param req ioctl operation to be performed
     * @param info output as fb_fix_screeninfo structure
     * @return Linux style ioctl return
     */
    public int ioctl(int req, NativeFramebuffer.fb_fix_screeninfo info) {
        return clib.ioctl(fd, req, info);
    }

    /**
     * Perform a Linux style ioctl operation on the associated file.
     *
     * @param req ioctl operation to be performed
     * @param info output as fb_var_screeninfo structure
     * @return Linux style ioctl return
     */
    public int ioctl(int req, NativeFramebuffer.fb_var_screeninfo info) {
        return clib.ioctl(fd, req, info);
    }

    /**
     * Perform a Linux style ioctl operation on the associated file.
     *
     * @param req ioctl operation to be performed
     * @param buf pointer to ioctl parameters
     * @return Linux style ioctl return
     */
    public int ioctl(int req, Pointer buf) {
        return clib.ioctl(fd, req, buf);
    }

    /**
     * Perform a Linux style ioctl operation on the associated file.
     *
     * @param req ioctl operation to be performed
     * @param buf byte array containing the ioctl parameters if any
     * @return Linux style ioctl return
     */
    public int ioctl(int req, byte[] buf) {
        return clib.ioctl(fd, req, buf);
    }

    /**
     * Close the associated file
     *
     * @return Linux style return
     */
    public int close() {
        try {
            fc.close();
            jfd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clib.close(fd);
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
     */
    public Pointer mmap(long len, int prot, int flags, long off) {
        Pointer p = clib.mmap(new Pointer(0), new NativeLong(len), prot, flags, fd, new NativeLong(off));
        if (p == null)
            return null;
        return p;
    }

    /**
     * Unmap mapped memory region.
     *
     * @param addr Mapped address.
     * @param len  Region length.
     * @return 0 if successful, -1 if not.
     */
    public int munmap(Pointer addr, long len) {
        return clib.munmap(addr, new NativeLong(len));
    }

}
