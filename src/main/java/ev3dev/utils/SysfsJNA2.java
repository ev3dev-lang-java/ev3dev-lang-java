package ev3dev.utils;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import ev3dev.utils.io.ILibc;
import ev3dev.utils.io.NativeConstants;
import ev3dev.utils.io.DefaultLibc;
import org.slf4j.Logger;

import java.io.File;
import java.nio.ByteBuffer;
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
public class SysfsJNA2 {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SysfsJNA2.class);
    private static final int FAST_ATTRIBUTE_SIZE = 16;
    private static final int MAX_ATTRIBUTE_SIZE = 4096;
    private static final ILibc libc = DefaultLibc.get();

    /**
     * Write a value in a file.
     *
     * @param filePath File path
     * @param value    value to write
     * @return A boolean value if the operation was written or not.
     */
    public static boolean writeString(final String filePath, final String value) {
        log.trace("echo {} > {}", value, filePath);
        try {
            ByteBuffer data = encodeString(value);

            //noinspection OctalInteger
            int fd = libc.open(filePath,
                NativeConstants.O_WRONLY | NativeConstants.O_CREAT | NativeConstants.O_TRUNC, 00644);
            if (fd < 0) {
                throw new LastErrorException(Native.getLastError());
            }
            int count = libc.write(fd, data, data.remaining());
            if (count < 0) {
                throw new LastErrorException(Native.getLastError());
            }
            libc.close(fd);
        } catch (LastErrorException e) {
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }

    public static boolean writeInteger(final String filePath, final int value) {
        return writeString(filePath, Integer.toString(value));
    }

    /**
     * Read an Attribute in the Sysfs with containing String values
     *
     * @param filePath path
     * @return value from attribute
     */
    public static String readString(final String filePath) {
        log.trace("cat {}", filePath);
        try {
            ByteBuffer data = ByteBuffer.allocate(FAST_ATTRIBUTE_SIZE);

            int fd = libc.open(filePath, NativeConstants.O_RDONLY, 0);
            if (fd < 0) {
                throw new LastErrorException(Native.getLastError());
            }
            int count = libc.pread(fd, data, data.remaining(), 0);
            if (count < 0) {
                throw new LastErrorException(Native.getLastError());
            }
            // check for overflow: if overflowed, retry with max buffer
            if (count == FAST_ATTRIBUTE_SIZE) {
                data = ByteBuffer.allocate(MAX_ATTRIBUTE_SIZE);
                count = libc.pread(fd, data, data.remaining(), 0);
                if (count < 0) {
                    throw new LastErrorException(Native.getLastError());
                }
            }
            libc.close(fd);
            data.limit(count);

            String value = decodeString(data);
            log.trace("value: {}", value);
            return value;
        } catch (LastErrorException e) {
            log.error(e.getLocalizedMessage(), e);
            throw new RuntimeException("Problem reading path: " + filePath, e);
        }
    }

    /**
     * Read an Attribute in the Sysfs with containing Integer values
     *
     * @param filePath path
     * @return value from attribute
     */
    public static int readInteger(final String filePath) {
        return Integer.parseInt(readString(filePath));
    }

    public static float readFloat(final String filePath) {
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
     * Method to write bytes in a path
     *
     * @param path  path
     * @param value value to write
     * @return Result
     */
    public static boolean writeBytes(final String path, final byte[] value) {
        ByteBuffer buffer = ByteBuffer.wrap(value);
        try {
            int fd = libc.open(path, NativeConstants.O_WRONLY | NativeConstants.O_CREAT | NativeConstants.O_TRUNC, 0);
            if (fd < 0) {
                throw new LastErrorException(Native.getLastError());
            }
            int count = libc.write(fd, buffer, buffer.limit());
            if (count < 0) {
                throw new LastErrorException(Native.getLastError());
            }
            libc.close(fd);
        } catch (LastErrorException e) {
            throw new RuntimeException("Unable to draw the LCD", e);
        }
        return true;
    }

    /**
     * Sysfs string encoding routine.
     *
     * @param value String for encoding.
     * @return ByteBuffer ready for reading.
     */
    public static ByteBuffer encodeString(String value) {
        return StandardCharsets.ISO_8859_1.encode(value);
    }

    /**
     * Sysfs string decoding routine.
     *
     * @param bytes Input buffer with character data from ev3dev sysfs.
     * @return Decoded string.
     */
    public static String decodeString(final ByteBuffer bytes) {
        // set correct limit (strip '\n')
        bytes.limit(scanForEnd(bytes));

        // decode
        return StandardCharsets.ISO_8859_1.decode(bytes).toString();
    }

    /**
     * Find a new buffer limit for a sysfs-originated char string.
     *
     * <p>ev3dev sysfs strings end with '\n' and that would interfere
     * with integer parsing. This method tries to find one of the following: </p>
     * <ul>
     *     <li>End of buffer, in which case the current limit is returned.</li>
     *     <li>Null termination/0x00 byte, in which case the index of this byte is returned.</li>
     *     <li>Newline/0x0A byte, in which case the index of this byte is returned.</li>
     * </ul>
     *
     * @param in ByteBuffer to scan.
     * @return New limit for the buffer.
     */
    private static int scanForEnd(final ByteBuffer in) {
        int pos = in.position();

        if (in.hasArray()) {
            // we can access the array directly :)
            byte[] array = in.array();
            for (; pos < in.limit(); pos++) {
                if (array[pos] == 0x0A || array[pos] == 0x00) {
                    return pos;
                }
            }

        } else {
            // we have to use a function call :(
            for (; pos < in.limit(); pos++) {
                byte b = in.get(pos);
                if (b == 0x0A || b == 0x00) {
                    return pos;
                }
            }
        }
        return in.limit();
    }
}
