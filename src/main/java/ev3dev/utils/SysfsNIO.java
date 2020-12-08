package ev3dev.utils;

import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * The class responsible to interact with SysfsNIO on EV3Dev
 *
 * @author Juan Antonio Breña Moral
 */
public class SysfsNIO {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SysfsNIO.class);
    private static final ByteBuffer buffer = ByteBuffer.allocate(256);
    private static final CharBuffer charBuffer = CharBuffer.allocate(256);
    private static final CharsetEncoder stringEnc = StandardCharsets.UTF_8.newEncoder();
    private static final CharsetDecoder stringDec = StandardCharsets.UTF_8.newDecoder();

    /**
     * Write a value in a file.
     *
     * @param filePath File path
     * @param value    value to write
     * @return A boolean value if the operation was written or not.
     */
    public static synchronized boolean writeString(final String filePath, final String value) {
        serializeString(value);
        return writeBytes(filePath, buffer);
    }

    public static synchronized boolean writeInteger(final String filePath, final int value) {
        serializeInt(value);
        return writeBytes(filePath, buffer);
    }

    /**
     * Read an Attribute in the SysfsNIO with containing String values
     *
     * @param filePath path
     * @return value from attribute
     */
    public static synchronized String readString(final String filePath) {
        buffer.clear();
        readBytes(filePath, buffer);
        buffer.flip();
        return parseString();
    }

    /**
     * Read an Attribute in the SysfsNIO with containing Integer values
     *
     * @param filePath path
     * @return value from attribute
     */
    public static synchronized int readInteger(final String filePath) {
        buffer.clear();
        readBytes(filePath, buffer);
        buffer.flip();
        return parseInt();
    }

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

    public static boolean existFile(Path pathToFind) {
        return Files.exists(pathToFind);
    }

    /**
     * Read an Attribute in the SysfsNIO with containing String values
     *
     * @param filePath path
     * @return value from attribute
     */
    public static synchronized void readBytes(final String filePath, final ByteBuffer data) {
        log.trace("read {}", filePath);
        try (var channel = FileChannel.open(Path.of(filePath), StandardOpenOption.READ)) {
            channel.read(data, 0);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            throw new RuntimeException("Problem reading path: " + filePath, e);
        }
    }

    /**
     * Method to write bytes in a path
     *
     * @param path  path
     * @param value value to write
     * @return Result
     */
    public static synchronized boolean writeBytes(final String path, final byte[] value) {
        return writeBytes(path, ByteBuffer.wrap(value));
    }

    /**
     * Method to write bytes in a path
     *
     * @param path   path
     * @param buffer value to write
     * @return Result
     */
    public static synchronized boolean writeBytes(final String path, final ByteBuffer buffer) {
        log.trace("write {}", path);
        try (var channel = FileChannel.open(Path.of(path), StandardOpenOption.WRITE,
            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            channel.position(0);
            channel.truncate(0);
            channel.write(buffer, 0);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Quickly decode an integer from the internal ByteBuffer of this class.
     *
     * @return Decoded integer.
     */
    private static int parseInt() {
        if (!SysfsNIO.buffer.hasArray())
            throw new IllegalStateException("cannot parse direct buffers");

        int result = 0;
        boolean negative = false;
        byte[] arr = SysfsNIO.buffer.array();

        int i = SysfsNIO.buffer.position();
        if (arr[i] == 0x2D /* - */) {
            negative = true;
            i++;
        }
        for (; i < SysfsNIO.buffer.limit(); i++) {
            if (arr[i] < 0x30 || arr[i] > 0x39)
                break;
            result = (result * 10) + (arr[i] & 0x0F);
        }
        if (negative)
            return -result;
        else
            return result;
    }

    /**
     * Quickly encode an integer to the internal ByteBuffer of this class.
     *
     * @param value Integer to encode.
     */
    private static void serializeInt(int value) {
        if (!buffer.hasArray())
            throw new IllegalStateException("cannot parse direct buffers");

        buffer.limit(buffer.capacity());
        byte[] arr = buffer.array();

        int remainder = value;
        if (remainder < 0)
            remainder = -remainder;

        int i = arr.length - 1;
        if (remainder == 0) {
            arr[i] = '0';
            i--;
        } else {
            for (; remainder != 0; i--) {
                arr[i] = (byte) ((remainder % 10) + 0x30 /* 0 */);
                remainder /= 10;
            }
        }

        if (value < 0)
            arr[i--] = '-';

        buffer.position(i + 1);
    }

    /**
     * Quickly decode a String from the internal ByteBuffer of this class.
     *
     * @return Decoded string.
     */
    private static String parseString() {
        charBuffer.clear();
        stringDec.reset();
        stringDec.decode(buffer, charBuffer, true);
        stringDec.flush(charBuffer);

        int pos = charBuffer.position();
        if (pos > 0 && charBuffer.get(pos - 1) == '\n') {
            charBuffer.position(pos - 1);
        }

        charBuffer.flip();
        return charBuffer.toString();
    }

    /**
     * Quickly encode a String to the internal ByteBuffer of this class.
     *
     * @param value String to encode.
     */
    private static void serializeString(String value) {
        buffer.clear();
        stringEnc.reset();
        stringEnc.encode(CharBuffer.wrap(value), buffer, true);
        stringEnc.flush(buffer);
        buffer.flip();
    }
}
