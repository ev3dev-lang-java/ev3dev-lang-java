package ev3dev.utils;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Reader of streams that can reread the same channel for structured data of
 * known length. The focus of this class is on performance.
 *
 * @author David Walend
 */
public class DataChannelRereader implements Closeable {

    private final Path path;
    private final ByteBuffer byteBuffer;
    private final FileChannel channel;

    /**
     * Create a DataChannelRereader for path with a bufferLength byte buffer
     *
     * @param path path to the file to reread
     * @param bufferLength length of the buffer to hold the structure
     * @throws IOException when things go wrong
     */
    public DataChannelRereader(Path path, int bufferLength) throws IOException {
        this.path = path;
        this.byteBuffer = ByteBuffer.allocate(bufferLength);
        this.channel = FileChannel.open(path);
    }

    /**
     * Create a DataChannelRereader for pathString with the default 32-byte buffer.
     *
     * @param pathString Path to the file to reread
     * @throws IOException when things go wrong
     */
    public DataChannelRereader(String pathString) throws IOException {
        this(Paths.get(pathString),32);
    }

    /**
     * @return a string made from the bytes in the file;
     */
    public String readString() {
        try {
            int n;
            do {
                byteBuffer.clear();
                channel.position(0);
                n = channel.read(byteBuffer);
                if (n == -1) {
                    throw new IOException("Premature end of file ");
                }
            } while (n <= 0);

            byte[] bytes = byteBuffer.array();
            if (bytes[n - 1] == '\n') {
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
