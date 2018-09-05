package fake_ev3dev.ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import ev3dev.utils.io.ILibc;
import ev3dev.utils.io.NativeConstants;

import java.nio.Buffer;
import java.util.HashMap;

public class EmulatedLibc implements ILibc {
    private HashMap<String, IFile> mapping;
    private HashMap<Integer, String> opened;
    private HashMap<Pointer, String> mmaps;
    private int filecounter;

    public EmulatedLibc() {
        mapping = new HashMap<>();
        opened = new HashMap<>();
        mmaps = new HashMap<>();
        filecounter = 0;
    }

    public void install(String path, IFile mock) {
        mapping.put(path, mock);
    }

    public void remove(String path) {
        mapping.remove(path);
    }

    private String path(int fd) {
        if (opened.containsKey(fd)) {
            return opened.get(fd);
        } else {
            throw new LastErrorException(NativeConstants.EBADF);
        }
    }

    private String path(Pointer mem) {
        if (mmaps.containsKey(mem)) {
            return mmaps.get(mem);
        } else {
            throw new LastErrorException(NativeConstants.EINVAL);
        }
    }

    private IFile impl(String path) {
        if (!mapping.containsKey(path)) {
            throw new LastErrorException(NativeConstants.ENOENT);
        } else {
            return mapping.get(path);
        }
    }

    @Override
    public int fcntl(int fd, int cmd, int arg) throws LastErrorException {
        return impl(path(fd)).fcntl(fd, cmd, arg);
    }

    @Override
    public int ioctl(int fd, int cmd, int arg) throws LastErrorException {
        return impl(path(fd)).ioctl(fd, cmd, arg);
    }

    @Override
    public int ioctl(int fd, int cmd, Pointer arg) throws LastErrorException {
        return impl(path(fd)).ioctl(fd, cmd, arg);
    }

    @Override
    public int open(String path, int flags, int mode) throws LastErrorException {
        IFile impl = impl(path);

        int fd = filecounter++;
        opened.put(fd, path);
        try {
            return impl.open(fd, path, flags, mode);
        } catch (Exception e) {
            opened.remove(fd);
            throw e;
        }
    }

    @Override
    public int close(int fd) throws LastErrorException {
        try {
            return impl(path(fd)).close(fd);
        } finally {
            opened.remove(fd);
        }
    }

    @Override
    public int write(int fd, Buffer buffer, int count) throws LastErrorException {
        return impl(path(fd)).write(fd, buffer, count);
    }

    @Override
    public int read(int fd, Buffer buffer, int count) throws LastErrorException {
        return impl(path(fd)).read(fd, buffer, count);
    }

    @Override
    public Pointer mmap(Pointer addr, NativeLong natLen, int prot, int flags, int fd, NativeLong off) throws LastErrorException {
        addr = impl(path(fd)).mmap(addr, natLen, prot, flags, fd, off);
        String path = opened.get(fd);
        mmaps.put(addr, path);
        return addr;
    }

    @Override
    public int munmap(Pointer addr, NativeLong len) throws LastErrorException {
        try {
            return impl(path(addr)).munmap(addr, len);
        } finally {
            mmaps.remove(addr);
        }
    }

    @Override
    public int msync(Pointer addr, NativeLong len, int flags) throws LastErrorException {
        return impl(path(addr)).msync(addr, len, flags);
    }
}
