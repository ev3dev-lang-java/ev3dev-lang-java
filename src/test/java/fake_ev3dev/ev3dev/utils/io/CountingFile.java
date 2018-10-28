package fake_ev3dev.ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import lombok.Getter;
import lombok.NonNull;

import java.nio.Buffer;

/**
 * <p>Function call counter.</p>
 *
 * <p>This class acts as a proxy, while counting individual invocations
 * of all functions.</p>
 *
 * @author Jakub Vaněk
 * @since 2.4.7
 */
@Getter
public class CountingFile implements ICounter {
    /**
     * Actual file implementation.
     */
    private IFile sub;

    /**
     * Number of calls to open()
     */

    private int countOpen;
    /**
     * Number of calls to close()
     */
    private int countClose;
    /**
     * Number of calls to mmap()
     */
    private int countMmap;
    /**
     * Number of calls to munmap()
     */

    private int countMunmap;
    /**
     * Number of calls to msync()
     */
    private int countMsync;
    /**
     * Number of calls to read()
     */
    private int countRead;
    /**
     * Number of calls to write()
     */
    private int countWrite;
    /**
     * Number of calls to ioctl() with integer argument
     */
    private int countIoctl_int;
    /**
     * Number of calls to ioctl() with pointer argument
     */
    private int countIoctl_ptr;
    /**
     * Number of calls to fcntl()
     */
    private int countFcntl;

    /**
     * Initialize new call counter.
     *
     * @param sub Where to forward the function calls.
     */
    public CountingFile(@NonNull IFile sub) {
        this.sub = sub;
        resetCount();
    }

    @Override
    public void resetCount() {
        countOpen = 0;
        countClose = 0;
        countMmap = 0;
        countMunmap = 0;
        countMsync = 0;
        countRead = 0;
        countWrite = 0;
        countIoctl_int = 0;
        countIoctl_ptr = 0;
        countFcntl = 0;
    }

    @Override
    public int open(String path, int flags, int mode) throws LastErrorException {
        throw new UnsupportedOperationException("This should not be called");
    }

    @Override
    public int open(int fd, @NonNull String path, int flags, int mode) throws LastErrorException {
        countOpen++;
        return sub.open(fd, path, flags, mode);
    }

    @Override
    public int fcntl(int fd, int cmd, int arg) throws LastErrorException {
        countFcntl++;
        return sub.fcntl(fd, cmd, arg);
    }

    @Override
    public int ioctl(int fd, int cmd, int arg) throws LastErrorException {
        countIoctl_int++;
        return sub.ioctl(fd, cmd, arg);
    }

    @Override
    public int ioctl(int fd, int cmd, @NonNull Pointer arg) throws LastErrorException {
        countIoctl_ptr++;
        return sub.ioctl(fd, cmd, arg);
    }

    @Override
    public int close(int fd) throws LastErrorException {
        countClose++;
        return sub.close(fd);
    }

    @Override
    public int write(int fd, @NonNull Buffer buffer, int count) throws LastErrorException {
        countWrite++;
        return sub.write(fd, buffer, count);
    }

    @Override
    public int read(int fd, @NonNull Buffer buffer, int count) throws LastErrorException {
        countRead++;
        return sub.read(fd, buffer, count);
    }

    @Override
    public Pointer mmap(@NonNull Pointer addr, @NonNull NativeLong natLen, int prot, int flags, int fd, @NonNull NativeLong natOff) throws LastErrorException {
        countMmap++;
        return sub.mmap(addr, natLen, prot, flags, fd, natOff);
    }

    @Override
    public int munmap(@NonNull Pointer addr, @NonNull NativeLong len) throws LastErrorException {
        countMunmap++;
        return sub.munmap(addr, len);
    }

    @Override
    public int msync(@NonNull Pointer addr, @NonNull NativeLong len, int flags) throws LastErrorException {
        countMsync++;
        return sub.msync(addr, len, flags);
    }
}
