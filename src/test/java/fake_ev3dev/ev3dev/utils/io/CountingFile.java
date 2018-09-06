package fake_ev3dev.ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import lombok.Getter;

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
public class CountingFile implements ICounter {
    /**
     * Actual file implementation.
     */
    private IFile sub;

    /**
     * Number of calls to open()
     */
    @Getter
    private int countOpen;
    /**
     * Number of calls to close()
     */
    @Getter
    private int countClose;
    /**
     * Number of calls to mmap()
     */
    @Getter
    private int countMmap;
    /**
     * Number of calls to munmap()
     */
    @Getter
    private int countMunmap;
    /**
     * Number of calls to msync()
     */
    @Getter
    private int countMsync;
    /**
     * Number of calls to read()
     */
    @Getter
    private int countRead;
    /**
     * Number of calls to write()
     */
    @Getter
    private int countWrite;
    /**
     * Number of calls to ioctl() with integer argument
     */
    @Getter
    private int countIoctl_int;
    /**
     * Number of calls to ioctl() with pointer argument
     */
    @Getter
    private int countIoctl_ptr;
    /**
     * Number of calls to fcntl()
     */
    @Getter
    private int countFcntl;

    /**
     * Initialize new call counter.
     *
     * @param sub Where to forward the function calls.
     */
    public CountingFile(IFile sub) {
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
    public int open(int fd, String path, int flags, int mode) throws LastErrorException {
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
    public int ioctl(int fd, int cmd, Pointer arg) throws LastErrorException {
        countIoctl_ptr++;
        return sub.ioctl(fd, cmd, arg);
    }

    @Override
    public int close(int fd) throws LastErrorException {
        countClose++;
        return sub.close(fd);
    }

    @Override
    public int write(int fd, Buffer buffer, int count) throws LastErrorException {
        countWrite++;
        return sub.write(fd, buffer, count);
    }

    @Override
    public int read(int fd, Buffer buffer, int count) throws LastErrorException {
        countRead++;
        return sub.read(fd, buffer, count);
    }

    @Override
    public Pointer mmap(Pointer addr, NativeLong natLen, int prot, int flags, int fd, NativeLong natOff) throws LastErrorException {
        countMmap++;
        return sub.mmap(addr, natLen, prot, flags, fd, natOff);
    }

    @Override
    public int munmap(Pointer addr, NativeLong len) throws LastErrorException {
        countMunmap++;
        return sub.munmap(addr, len);
    }

    @Override
    public int msync(Pointer addr, NativeLong len, int flags) throws LastErrorException {
        countMsync++;
        return sub.msync(addr, len, flags);
    }
}
