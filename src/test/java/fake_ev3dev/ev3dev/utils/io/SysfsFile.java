package fake_ev3dev.ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import ev3dev.utils.io.NativeConstants;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Proxy file suitable for ev3dev sysfs filesystem emulation.
 *
 * No real access via libc is used. Instead, NIO channels are used.
 * Also, the file is opened/truncated for each read/write operation separately.
 * This mimicks how sysfs behaves -- shorter pwrite() will not leave
 * extra data at the end.
 */
public class SysfsFile implements IFile {
    private Path filePath = null;

    @Override
    public int open(int fd, String path, int flags, int mode) throws LastErrorException {
        this.filePath = Paths.get(path);
        boolean shouldCreate = (flags & NativeConstants.O_CREAT) != 0;
        if (!filePath.toFile().exists() && !shouldCreate)
            throw new LastErrorException(NativeConstants.ENOENT);
        return fd;
    }

    @Override
    public int close(int fd) throws LastErrorException {
        return 0; // always succeeds - no open file
    }

    @Override
    public int write(int fd, Buffer buffer, int count) throws LastErrorException {
        try (WritableByteChannel chan = Files.newByteChannel(filePath,
            StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

            int oldLimit = buffer.limit();
            int oldPosition = buffer.position();
            buffer.limit(oldPosition + count);

            chan.write((ByteBuffer) buffer);

            int newPosition = buffer.position();
            buffer.limit(oldLimit);
            buffer.position(oldPosition);
            return (newPosition - oldPosition);
        } catch (IOException e) {
            throw new LastErrorException(NativeConstants.EIO);
        }
    }

    @Override
    public int read(int fd, Buffer buffer, int count) throws LastErrorException {
        try (ReadableByteChannel chan = Files.newByteChannel(filePath, StandardOpenOption.READ)) {
            int oldLimit = buffer.limit();
            int oldPosition = buffer.position();
            buffer.limit(oldPosition + count);

            chan.read((ByteBuffer) buffer);

            int newPosition = buffer.position();
            buffer.limit(oldLimit);
            buffer.position(oldPosition);
            return (newPosition - oldPosition);
        } catch (IOException e) {
            throw new LastErrorException(NativeConstants.EIO);
        }
    }

    @Override
    public int pread(int fd, Buffer buffer, int count, int offset) throws LastErrorException {
        if (offset != 0)
            throw new LastErrorException(NativeConstants.EINVAL);

        return read(fd, buffer, count);
    }

    @Override
    public int pwrite(int fd, Buffer buffer, int count, int offset) throws LastErrorException {
        if (offset != 0)
            throw new LastErrorException(NativeConstants.EINVAL);

        return write(fd, buffer, count);
    }

    @Override
    public int open(String path, int flags, int mode) throws LastErrorException {
        throw new UnsupportedOperationException("This should not be called");
    }

    @Override
    public int fcntl(int fd, int cmd, int arg) throws LastErrorException {
        throw new UnsupportedOperationException("This is not implemented");
    }

    @Override
    public int ioctl(int fd, int cmd, int arg) throws LastErrorException {
        throw new UnsupportedOperationException("This is not implemented");
    }

    @Override
    public int ioctl(int fd, int cmd, Pointer arg) throws LastErrorException {
        throw new UnsupportedOperationException("This is not implemented");
    }

    @Override
    public Pointer mmap(Pointer addr, NativeLong len, int prot, int flags, int fd, NativeLong off) throws LastErrorException {
        throw new UnsupportedOperationException("This is not implemented");
    }

    @Override
    public int munmap(Pointer addr, NativeLong len) throws LastErrorException {
        throw new UnsupportedOperationException("This is not implemented");
    }

    @Override
    public int msync(Pointer addr, NativeLong len, int flags) throws LastErrorException {
        throw new UnsupportedOperationException("This is not implemented");
    }
}
