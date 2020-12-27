package ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import java.nio.Buffer;

/**
 * POSIX Standard C Library wrapper interface
 * For detailed reference, please see the
 * <a href="https://man7.org/linux/man-pages/dir_section_2.html">Linux syscall manual pages</a>
 * @author Jakub Vaněk
 * @since 2.4.7
 */
public interface ILibc {
    // file descriptor operations

    /**
     * Manipulate file descriptor
     * @param fd File descriptor to operate upon.
     * @param cmd Command code, see manpages for details.
     * @param arg Command argument, command-speciic. See manpages for details.
     * @return Depends on the command. On failure, -1 is returned.
     * @see <a href="https://man7.org/linux/man-pages/man2/fcntl.2.html">man 2 fcntl</a>
     * @throws LastErrorException If errno is set during the operation. Use Native#getLastError to query the error code.
     */
    int fcntl(int fd, int cmd, int arg) throws LastErrorException;

    // ioctls

    /**
     * Invoke an I/O control request
     * @param fd Opened file descriptor to act upon.
     * @param cmd IO command code; this is device-specific (see manpages and/or kernel sources for details).
     * @param arg IOCTL integer argument, this is device-specific (see manpages and/or kernel sources for details).
     * @return -1 on failure, 0 otherwise (usually).
     * @see <a href="https://man7.org/linux/man-pages/man2/ioctl.2.html">man 2 ioctl</a>
     * @throws LastErrorException If errno is set during the operation. Use Native#getLastError to query the error code.
     */
    int ioctl(int fd, int cmd, int arg) throws LastErrorException;

    /**
     * Invoke an I/O control request
     * @param fd Opened file descriptor to act upon.
     * @param cmd IO command code; this is device-specific (see manpages and/or kernel sources for details).
     * @param arg IOCTL integer argument, this is device-specific (see manpages and/or kernel sources for details).
     * @return -1 on failure, 0 otherwise (usually).
     * @see <a href="https://man7.org/linux/man-pages/man2/ioctl.2.html">man 2 ioctl</a>
     * @throws LastErrorException If errno is set during the operation. Use Native#getLastError to query the error code.
     */
    int ioctl(int fd, int cmd, Pointer arg) throws LastErrorException;

    // open/close

    /**
     * Try to open (or create) a file from the specified path.
     * @param path Path to try to open.
     * @param flags Open mode; typically O_RDONLY/O_WRONLY/O_RDWR combined with other modifiers (see POSIX).
     * @param mode Permissions to be set on the file if it is going to be created (O_CREAT).
     * @return File descriptor or -1 if the file cannot be opened.
     * @see <a href="https://man7.org/linux/man-pages/man2/open.2.html">man 2 open</a>
     * @throws LastErrorException If errno is set during the operation. Use Native#getLastError to query the error code.
     */
    int open(String path, int flags, int mode) throws LastErrorException;

    /**
     * Close the specified file descriptor.
     * @param fd File descriptor to close.
     * @return -1 on failure, 0 otherwise.
     * @see <a href="https://man7.org/linux/man-pages/man2/close.2.html">man 2 close</a>
     * @throws LastErrorException If errno is set during the operation. Use Native#getLastError to query the error code.
     */
    int close(int fd) throws LastErrorException;

    // read/write

    /**
     * Request a write to the current position in the file referred by a file descriptor.
     * @param fd File descriptor of the file to write.
     * @param buffer Buffer with bytes to write.
     * @param count Size of the buffer.
     * @return Number of bytes written or -1 if an error occurred.
     * @see <a href="https://man7.org/linux/man-pages/man2/write.2.html">man 2 write</a>
     * @throws LastErrorException If errno is set during the operation. Use Native#getLastError to query the error code.
     */
    int write(int fd, Buffer buffer, int count) throws LastErrorException;

    /**
     * Request a read from the current position in the file referred by a file descriptor.
     * @param fd File descriptor of the file to read.
     * @param buffer Buffer where to put the data.
     * @param count Size of the buffer.
     * @return Number of bytes read or -1 if an error occurred.
     * @see <a href="https://man7.org/linux/man-pages/man2/read.2.html">man 2 read</a>
     * @throws LastErrorException If errno is set during the operation. Use Native#getLastError to query the error code.
     */
    int read(int fd, Buffer buffer, int count) throws LastErrorException;

    // map/unmap

    /**
     * Map a file to memory.
     * @param addr Address hint from the userspace where to put the mapping in memory. Pass NULL to ignore the hint.
     * @param len Length of the memory to map.
     * @param prot Memory protection flags (PROT_READ/PROT_WRITE/PROT_EXEC). See manpages for details.
     * @param flags Memory mapping flags (MAP_SHARED/MAP_FILE/...). See manpages for deatils.
     * @param fd Opened file descriptor of the file that needs to be mapped to memory.
     * @param off Offset in the file from which to start the mapping.
     * @return On success, address of the mapped memory. On failure, -1 (MAP_FAILED) is returned.
     * @see <a href="https://man7.org/linux/man-pages/man2/mmap.2.html">man 2 mmap</a>
     * @throws LastErrorException If errno is set during the operation. Use Native#getLastError to query the error code.
     */
    Pointer mmap(Pointer addr, NativeLong len, int prot, int flags, int fd, NativeLong off) throws LastErrorException;

    /**
     * Unmap a file from memory.
     * @param addr Address where the file was mapped.
     * @param len Length of the mapped area.
     * @return -1 on failure, 0 otherwise.
     * @see <a href="https://man7.org/linux/man-pages/man2/munmap.2.html">man 2 munmap</a>
     * @throws LastErrorException If errno is set during the operation. Use Native#getLastError to query the error code.
     */
    int munmap(Pointer addr, NativeLong len) throws LastErrorException;

    /**
     * Synchronize memory-mapped data with file contents.
     * @param addr Address where the file was mapped.
     * @param len Length of the mapped area.
     * @param flags Synchronization type (MS_SYNC/MS_ASYNC/MS_INVALIDATE). See manpages for details.
     * @return -1 on failure, 0 otherwise.
     * @see <a href="https://man7.org/linux/man-pages/man2/msync.2.html">man 2 msync</a>
     * @throws LastErrorException If errno is set during the operation. Use Native#getLastError to query the error code.
     */
    int msync(Pointer addr, NativeLong len, int flags) throws LastErrorException;
}
