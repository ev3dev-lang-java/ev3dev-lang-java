package ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import java.nio.Buffer;

/**
 * POSIX Standard C Library wrapper interface
 *
 * @author Jakub Vaněk
 * @since 2.4.7
 */
public interface ILibc {
    // file descriptor operations
    int fcntl(int fd, int cmd, int arg) throws LastErrorException;

    // ioctls
    int ioctl(int fd, int cmd, int arg) throws LastErrorException;

    int ioctl(int fd, int cmd, Pointer arg) throws LastErrorException;

    // open/close

    int open(String path, int flags, int mode) throws LastErrorException;

    int close(int fd) throws LastErrorException;

    // read/write
    int write(int fd, Buffer buffer, int count) throws LastErrorException;

    int read(int fd, Buffer buffer, int count) throws LastErrorException;

    // read/write with offset
    int pread(int fd, Buffer buffer, int count, int offset) throws LastErrorException;

    int pwrite(int fd, Buffer buffer, int count, int offset) throws LastErrorException;

    // map/unmap
    Pointer mmap(Pointer addr, NativeLong len, int prot, int flags, int fd, NativeLong off) throws LastErrorException;

    int munmap(Pointer addr, NativeLong len) throws LastErrorException;

    int msync(Pointer addr, NativeLong len, int flags) throws LastErrorException;
}
