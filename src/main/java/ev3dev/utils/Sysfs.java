package ev3dev.utils;

import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class responsible to interact with Sysfs on EV3Dev
 *
 * @author Juan Antonio Breña Moral
 */
public class Sysfs {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Sysfs.class);
    private static final LRUFileCache readCache = new LRUFileCache(64, 64, false);
    private static final LRUFileCache writeCache = new LRUFileCache(64, 64, true);
    private static final ByteBuffer buffer = ByteBuffer.allocate(256);
    private static final CharBuffer charBuffer = CharBuffer.allocate(256);
    private static final CharsetEncoder stringEnc = StandardCharsets.UTF_8.newEncoder()
        .onUnmappableCharacter(CodingErrorAction.REPLACE).onMalformedInput(CodingErrorAction.REPLACE);
    private static final CharsetDecoder stringDec = StandardCharsets.UTF_8.newDecoder()
        .onUnmappableCharacter(CodingErrorAction.REPLACE).onMalformedInput(CodingErrorAction.REPLACE);

    /**
     * Write a string to a sysfs attribute.
     *
     * @param filePath Full path to the attribute.
     * @param value    value to write
     * @return A boolean value if the operation was written or not.
     */
    public static synchronized boolean writeString(final String filePath, final String value) {
        FastCvt.serializeString(value, buffer, stringEnc);
        return writeBytes(filePath, buffer);
    }

    /**
     * Write an integer to a sysfs attribute.
     *
     * @param filePath Full path to the attribute.
     * @param value    value to write
     * @return A boolean value if the operation was written or not.
     */
    public static synchronized boolean writeInteger(final String filePath, final int value) {
        FastCvt.serializeInt(value, buffer);
        return writeBytes(filePath, buffer);
    }

    /**
     * Read a string from a sysfs attribute.
     *
     * @param filePath Full path to the attribute.
     * @return value from attribute
     */
    public static synchronized String readString(final String filePath) {
        buffer.clear();
        readBytes(filePath, buffer);
        buffer.flip();
        return FastCvt.parseString(buffer, charBuffer, stringDec);
    }

    /**
     * Read an integer from a sysfs attribute.
     *
     * @param filePath Full path to the attribute.
     * @return value from attribute
     */
    public static synchronized int readInteger(final String filePath) {
        buffer.clear();
        readBytes(filePath, buffer);
        buffer.flip();
        return FastCvt.parseInt(buffer);
    }

    /**
     * Read a float from a sysfs attribute.
     *
     * @param filePath Full path to the attribute.
     * @return value from attribute
     */
    public static float readFloat(final String filePath) {
        // slow datapath
        return Float.parseFloat(readString(filePath));
    }

    /**
     * @param filePath path
     * @return an List with options from a path
     */
    public static List<File> getElements(final String filePath) {
        final File f = new File(filePath);
        if (existPath(filePath) && (f.listFiles().length > 0)) {
            return new ArrayList<>(Arrays.asList(f.listFiles()));
        } else {
            throw new RuntimeException("The path doesn't exist: " + filePath);
        }
    }

    /**
     * This method is used to detect folders in /sys/class/
     *
     * @param filePath path
     * @return boolean
     */
    public static boolean existPath(final String filePath) {
        if (log.isTraceEnabled()) {
            log.trace("ls " + filePath);
        }
        final File f = new File(filePath);
        return f.exists() && f.isDirectory();
    }

    /**
     * Check if a file or directory exists in the system.
     *
     * @param pathToFind Path to check.
     * @return True if yes, false otherwise.
     */
    public static boolean existFile(Path pathToFind) {
        return Files.exists(pathToFind);
    }

    /**
     * Read a byte buffer directly from a sysfs attribute.
     *
     * @param filePath Path to the file.
     * @param data     Buffer with results (follows standard NIO behaviour).
     */
    public static synchronized void readBytes(final String filePath, final ByteBuffer data) {
        log.trace("read {}", filePath);
        try {
            FileChannel channel = readCache.open(filePath).channel;
            channel.read(data, 0);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            throw new RuntimeException("Problem reading path: " + filePath, e);
        }
    }

    /**
     * Write a byte buffer directly to a sysfs attribute.
     *
     * @param path  Path to the file.
     * @param value Byte array to write.
     * @return Whether the write succeeded or not.
     */
    public static synchronized boolean writeBytes(final String path, final byte[] value) {
        return writeBytes(path, ByteBuffer.wrap(value));
    }

    /**
     * Write a byte buffer directly to a file in sysfs.
     *
     * @param path   Path to the file.
     * @param buffer NIO ByteBuffer with the data two write (follows standard NIO behaviour).
     * @return Whether the write succeeded or not.
     */
    public static synchronized boolean writeBytes(final String path, final ByteBuffer buffer) {
        log.trace("write {}", path);
        try {
            LRUFileCache.Entry fd = writeCache.open(path);
            if (!fd.isSysfs) {
                fd.channel.position(0);
                fd.channel.truncate(0);
            }
            fd.channel.write(buffer, 0);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Clear all internal state of this class.
     */
    public static synchronized void reset() {
        readCache.clear();
        writeCache.clear();
        buffer.clear();
        charBuffer.clear();
        stringEnc.reset();
        stringDec.reset();
    }
}
