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
     */
    public DataChannelRereader(Path path, int bufferLength) {
        this.path = path;
        this.byteBuffer = ByteBuffer.allocate(bufferLength);
        try {
            this.channel = FileChannel.open(path);
        } catch (IOException e) {
            throw new RuntimeException("Problem opening path: " + path, e);
        }
    }

    /**
     * Create a DataChannelRereader for pathString with the default 32-byte buffer.
     *
     * @param pathString Path to the file to reread
     */
    public DataChannelRereader(String pathString) {
        this(Paths.get(pathString),32);
    }

    /**
     * @return a string made from the bytes in the file;
     */
    public synchronized String readString() {
        try {
            byteBuffer.clear();
            int n = channel.read(byteBuffer,0);
            if ((n == -1) || (n == 0)) {
                return "";
            } else if (n < -1) {
                throw new RuntimeException("Unexpected read byte count of " + n + " while reading " + path);
            }

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

    /**
     * @return an int from parsing a UTF_8 string in the file
     */
    public int readIntFromAscii() {
        return Integer.parseInt(readString());
    }

    public Path getPath() {
        return path;
    }

    @Override
    public synchronized void close() throws IOException {
        channel.close();
    }
}
