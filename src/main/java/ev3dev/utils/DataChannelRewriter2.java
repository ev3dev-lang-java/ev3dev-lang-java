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
public class DataChannelRewriter2 implements Closeable {

    private final Path path;
    private final FileChannel channel;

    /**
     * Create a DataChannelRewriter for path with a bufferLength byte buffer
     *
     * @param path path to the file to reread
     */
    public DataChannelRewriter2(Path path) {
        this.path = path;
        try {
            this.channel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            this.channel.position(0);
        } catch (IOException e) {
            throw new RuntimeException("While opening " + path, e);
        }
    }

    /**
     * Create a DataChannelRewriter for pathString with the default 32-byte buffer.
     *
     * @param pathString Path to the file to reread
     */
    public DataChannelRewriter2(String pathString) {
        this(Paths.get(pathString));
    }

    /**
     * @param string to write. A new line character
     */
    public void writeString(String string) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(string.getBytes(StandardCharsets.UTF_8));
            channel.truncate(0);
            channel.write(buffer, 0);
        } catch (IOException e) {
            throw new RuntimeException("Problem writing path: " + path, e);
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

