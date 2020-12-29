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
 * Writer of streams that can rewrite the same channel for structured data of
 * known length. The focus of this class is on performance.
 *
 * @author David Walend
 */
public class DataChannelRewriter implements Closeable {

    private final Path path;
    private final ByteBuffer byteBuffer;
    private final FileChannel channel;

    /**
     * Create a DataChannelRewriter for path with a bufferLength byte buffer
     *
     * @param path path to the file to reread
     * @param bufferLength length of the buffer to hold the structure
     */
    public DataChannelRewriter(Path path, int bufferLength) {
        this.path = path;
        this.byteBuffer = ByteBuffer.allocate(bufferLength);
        try {
            this.channel = FileChannel.open(path, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException("While opeing " + path,e);
        }
    }

    /**
     * Create a DataChannelRewriter for pathString with the default 32-byte buffer.
     *
     * @param pathString Path to the file to reread
     */
    public DataChannelRewriter(String pathString) {
        this(Paths.get(pathString),32);
    }

    /**
     * @param string to write. A new line character
     */
    public void writeString(String string) {
        try {
            byteBuffer.clear();
            byteBuffer.put(string.getBytes(StandardCharsets.UTF_8));
            byteBuffer.put(((byte)'\n'));
            byteBuffer.flip();
            channel.position(0);
            channel.write(byteBuffer);
            channel.truncate(byteBuffer.position());
            channel.force(false);
        } catch (IOException e) {
            throw new RuntimeException("Problem reading path: " + path, e);
        }
    }

    public Path getPath() {
        return path;
    }

    @Override
    public void close() throws IOException {
        channel.close();
    }
}

