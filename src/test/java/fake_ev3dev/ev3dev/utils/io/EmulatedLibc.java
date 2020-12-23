package fake_ev3dev.ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import ev3dev.utils.io.ILibc;
import ev3dev.utils.io.NativeConstants;
import lombok.NonNull;

import java.nio.Buffer;
import java.util.HashMap;
import java.util.Optional;

/**
 * <p>Basic file hierarchy emulation.</p>
 *
 * <p>This class emulates native libc implementation.
 * To achieve this, it works as a mock file switch. The emulation
 * itself is not performed by this class, but instead it is routed
 * to the mocks the user registers. To install a mock class on a path,
 * call {@link EmulatedLibc#install(String, IFile)}. To remove it, call
 * {@link EmulatedLibc#remove(String)}. Beware - file descriptors
 * using this implementation might be still in use and removing the
 * emulation implementation will trigger an exception later.</p>
 *
 * @author Jakub Vaněk
 * @since 2.4.7
 */
public class EmulatedLibc implements ILibc {
    /**
     * Map of registered paths and their implementations.
     */
    private HashMap<String, IFile> mapping;
    /**
     * Map of file descriptors and the paths of files they belong to.
     */
    private HashMap<Integer, String> opened;
    /**
     * Map of memory-mapped pointers and the files they belong to.
     */
    private HashMap<Pointer, String> mmaps;
    /**
     * Current next file-descriptor
     */
    private int filecounter;

    /**
     * Initialize new filesystem emulator.
     */
    public EmulatedLibc() {
        mapping = new HashMap<>();
        opened = new HashMap<>();
        mmaps = new HashMap<>();
        filecounter = 0;
    }

    /**
     * Register new emulated path.
     *
     * @param path Path from which the calls should be routed.
     * @param mock Implementation where should the calls be routed to.
     */
    public void install(@NonNull String path, @NonNull IFile mock) {
        mapping.put(path, mock);
    }

    /**
     * Remove emulated path.
     *
     * @param path Which path to deregister.
     */
    public void remove(@NonNull String path) {
        mapping.remove(path);
    }

    /**
     * Get path for selected file descriptor.
     *
     * @param fd Queried file descriptor.
     * @return Path from where this file descriptor was opened.
     * @throws LastErrorException EBADF when the file descriptor was not found.
     */
    private String path(int fd) {
        return Optional
            .ofNullable(opened.get(fd))
            .orElseThrow(() -> new LastErrorException(NativeConstants.EBADF));
    }

    /**
     * Get path for selected memory-mapped pointer.
     *
     * @param mem Queried pointer.
     * @return Path from where this pointer was mapped.
     * @throws LastErrorException EINVAL when the pointer was not found.
     */
    private String path(@NonNull Pointer mem) {
        return Optional
            .ofNullable(mmaps.get(mem))
            .orElseThrow(() -> new LastErrorException(NativeConstants.EINVAL));
    }

    /**
     * Get backend implementation for the following file.
     *
     * @param path Queried file path.
     * @return Implementation of the file.
     * @throws LastErrorException ENOENT when the file isn't known.
     */
    private IFile impl(@NonNull String path) {
        return Optional
            .ofNullable(mapping.get(path))
            .orElseThrow(() -> new LastErrorException(NativeConstants.ENOENT));
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
    public int ioctl(int fd, int cmd, @NonNull Pointer arg) throws LastErrorException {
        return impl(path(fd)).ioctl(fd, cmd, arg);
    }

    @Override
    public int open(@NonNull String path, int flags, int mode) throws LastErrorException {
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
    public Pointer mmap(@NonNull Pointer addr, @NonNull NativeLong natLen, int prot, int flags, int fd, @NonNull NativeLong off) throws LastErrorException {
        addr = impl(path(fd)).mmap(addr, natLen, prot, flags, fd, off);
        String path = opened.get(fd);
        mmaps.put(addr, path);
        return addr;
    }

    @Override
    public int munmap(@NonNull Pointer addr, @NonNull NativeLong len) throws LastErrorException {
        try {
            return impl(path(addr)).munmap(addr, len);
        } finally {
            mmaps.remove(addr);
        }
    }

    @Override
    public int msync(@NonNull Pointer addr, @NonNull NativeLong len, int flags) throws LastErrorException {
        return impl(path(addr)).msync(addr, len, flags);
    }
}
