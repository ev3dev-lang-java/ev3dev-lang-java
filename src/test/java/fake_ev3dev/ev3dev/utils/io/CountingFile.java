package fake_ev3dev.ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import lombok.Getter;

import java.nio.Buffer;

public class CountingFile implements ICounter {
    private IFile sub;

    @Getter
    private int countOpen;
    @Getter
    private int countClose;
    @Getter
    private int countMmap;
    @Getter
    private int countMunmap;
    @Getter
    private int countMsync;
    @Getter
    private int countRead;
    @Getter
    private int countWrite;
    @Getter
    private int countIoctl_int;
    @Getter
    private int countIoctl_ptr;
    @Getter
    private int countFcntl;


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
