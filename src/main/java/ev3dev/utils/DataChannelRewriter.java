package ev3dev.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
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

    private static final Logger log = LoggerFactory.getLogger(DataChannelRewriter.class);

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
            this.channel = keepTryingFileChannel(path);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("While opening " + path,e);
        } //todo could also try Files.exists(pathToFind);
    }

    @SuppressWarnings("BusyWait")
    private static FileChannel keepTryingFileChannel(Path path) throws IOException, InterruptedException {
        for (;;) {
            try {
                return FileChannel.open(path, StandardOpenOption.WRITE);
            } catch (AccessDeniedException adx) {
                log.debug("Retrying after 10 ms due to access exception",adx);
                Thread.sleep(10);
            }
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
    public synchronized void writeString(String string) {
        try {
            byteBuffer.clear();
            byteBuffer.put(string.getBytes(StandardCharsets.UTF_8));
            byteBuffer.put(((byte)'\n'));
            byteBuffer.flip();
            channel.truncate(0);
            channel.write(byteBuffer,0);
            channel.force(false);
        } catch (IOException e) {
            throw new RuntimeException("Problem reading path: "+path, e);
        }
    }

    public void writeAsciiInt(int i) {
        writeString(Integer.toString(i));
    }

    public Path getPath() {
        return path;
    }

    @Override
    public synchronized void close() throws IOException {
        channel.close();
    }
}
