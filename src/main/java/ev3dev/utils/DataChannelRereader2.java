package ev3dev.utils;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Reader of streams that can reread the same channel for structured data of
 * known length. The focus of this class is on performance.
 *
 * <p>Because of its specialization to ev3dev sysfs attributes, it is able
 * to read at most 4096 bytes.
 *
 * <p>Objects of this class are safe to use from multiple threads when used in
 * a standard manner, see documentation of individual methods for details.
 *
 * <p>Note: this class contains a static ThreadLocal&lt;ByteBuffer&gt;.
 * This may cause problems with massive and long-lived thread pools.
 *
 * @author David Walend
 */
public class DataChannelRereader2 implements Closeable {
    /**
     * Thread-local buffer for reading data from sysfs.
     *
     * <p>Each NIO read() needs a pre-allocated buffer into which
     * data are stored. This makes the code utilizing it responsible
     * for buffer management. Here it is solved by keeping around
     * a generously sized buffer (4096 = memory page size).
     * By making the buffer thread-local, we avoid data races
     * on buffer contents. The buffer can also be made static,
     * as there is little benefit to keeping it local to class instances.
     */
    private static final ThreadLocal<ByteBuffer> byteBuffer =
        ThreadLocal.withInitial(() -> ByteBuffer.allocate(4096));

    /**
     * Real file path, primarily for logging purposes.
     */
    private final Path path;

    /**
     * NIO channel opened for the path.
     */
    private final FileChannel channel;

    /**
     * Create a DataChannelRereader for the specified path.
     *
     * @param path path to the file to reread
     */
    public DataChannelRereader2(Path path) {
        this.path = path;
        try {
            this.channel = FileChannel.open(path, StandardOpenOption.READ);
        } catch (IOException e) {
            throw new RuntimeException("Problem opening path: " + path, e);
        }
    }

    /**
     * Create a DataChannelRereader for the specified path.
     *
     * @param pathString path to the file to reread
     */
    public DataChannelRereader2(String pathString) {
        this(Paths.get(pathString));
    }

    /**
     * Read the current file contents and return them as a string.
     *
     * <p>You can safely call this function from multiple threads in parallel.
     * However, you must not call this function in parallel with close() or
     * after close() was called.
     *
     * @return String contents of the file without a trailing newline.
     */
    public String readString() {
        try {
            // prepare the thread-local buffer
            ByteBuffer buffer = byteBuffer.get();
            buffer.clear();

            // try to do the read
            // only one try, as:
            // - value >0 indicates success
            // - value =0 does not tell us much, as there's a possibility of reading an empty attribute
            // - value <0 indicates failure
            int n = channel.read(buffer, 0);
            if (n == -1) {
                throw new IOException("Premature end of file " + path);
            }

            // strip trailing newline & return data as a string
            // rationale: ev3dev sysfs often appends \n, but this breaks parseFloat/parseInt/...
            byte[] bytes = buffer.array();
            if (n > 0 && bytes[n - 1] == '\n') {
                return new String(bytes, 0, n - 1, StandardCharsets.UTF_8);
            } else {
                return new String(bytes, 0, n, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new RuntimeException("Problem reading path: " + path, e);
        }
    }

    @Override
    public void close() throws IOException {
        channel.close();
    }
}
