package ev3dev.utils;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import ev3dev.utils.io.ILibc;
import ev3dev.utils.io.NativeConstants;
import ev3dev.utils.io.DefaultLibc;
import org.slf4j.Logger;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
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
    private static final int MAX_ATTRIBUTE_SIZE = 4096; // kernel page size
    private static final ThreadLocal<IoHelper> ioHelper = new ThreadLocal<>();
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
        IoHelper io = getIoHelper();
        try {
            ByteBuffer data = io.encodeString(value);

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
        IoHelper io = getIoHelper();
        try {
            ByteBuffer data = io.getTemporaryByteBuffer(MAX_ATTRIBUTE_SIZE);

            int fd = libc.open(filePath, NativeConstants.O_RDONLY, 0);
            if (fd < 0) {
                throw new LastErrorException(Native.getLastError());
            }
            int count = libc.read(fd, data, data.remaining());
            if (count < 0) {
                throw new LastErrorException(Native.getLastError());
            }
            libc.close(fd);
            data.limit(count);

            String value = io.decodeString(data);
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
     * Get IO helper for this thread. Allocates a new one if necessary.
     * @return IO helper instance for this thread.
     */
    public static IoHelper getIoHelper() {
        IoHelper ref = ioHelper.get();
        if (ref == null) {
            ref = new IoHelper(MAX_ATTRIBUTE_SIZE, MAX_ATTRIBUTE_SIZE, StandardCharsets.ISO_8859_1);
            ioHelper.set(ref);
        }
        return ref;
    }

    /**
     * Helper class for IO and string coding.
     */
    public static class IoHelper {
        private final CharsetDecoder stringDecoder;
        private final CharsetEncoder stringEncoder;
        private ByteBuffer byteBuffer;
        private CharBuffer charBuffer;

        /**
         * Initialize a new instance of IO helper.
         *
         * @param initialBytes Initial byte buffer size.
         * @param initialChars Initial character buffer size.
         * @param cs           Charset to use for decoding. <b>WARNING: the encoding must be single-byte.</b>
         */
        public IoHelper(int initialBytes, int initialChars, Charset cs) {
            this.stringEncoder = cs.newEncoder();
            this.stringDecoder = cs.newDecoder();
            this.stringEncoder.onMalformedInput(CodingErrorAction.REPLACE);
            this.stringDecoder.onMalformedInput(CodingErrorAction.REPLACE);
            this.stringEncoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
            this.stringDecoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
            this.byteBuffer = ByteBuffer.allocate(initialBytes);
            this.charBuffer = CharBuffer.allocate(initialChars);
        }

        /**
         * Get a cached byte buffer.
         *
         * @param requiredCapacity Minimum buffer capacity in bytes.
         * @return Cleared ByteBuffer.
         */
        public ByteBuffer getTemporaryByteBuffer(int requiredCapacity) {
            if (byteBuffer.capacity() < requiredCapacity) {
                byteBuffer = ByteBuffer.allocate(roundUp(requiredCapacity));
            }
            byteBuffer.clear();
            return byteBuffer;
        }

        /**
         * Get a cached byte buffer.
         *
         * @param requiredCapacity Minimum buffer capacity in bytes.
         * @return Cleared ByteBuffer.
         */
        public CharBuffer getTemporaryCharBuffer(int requiredCapacity) {
            if (charBuffer.capacity() < requiredCapacity) {
                charBuffer = CharBuffer.allocate(roundUp(requiredCapacity));
            }
            charBuffer.clear();
            return charBuffer;
        }

        /**
         * Optimized string encoding routine.
         *
         * @param value String for encoding.
         * @return Temporary ByteBuffer ready for reading.
         */
        public ByteBuffer encodeString(String value) {
            // wrap source buffer (required by CharsetEncoder)
            CharBuffer chars = CharBuffer.wrap(value);

            // destination buffer needs to be large enough
            // note: this is single-byte encoding, so we can equate bytes to chars
            ByteBuffer bytes = getTemporaryByteBuffer(value.length());

            // perform encoding process
            // no error handling: malformed input is replaced, underflow is OK, overflow shouldn't happen (see above)
            stringEncoder.reset();
            stringEncoder.encode(chars, bytes, true);
            stringEncoder.flush(bytes);
            bytes.flip();
            return bytes;
        }

        /**
         * Optimized string decoding routine.
         *
         * @param bytes Input buffer with character data from ev3dev sysfs.
         * @return Decoded string.
         */
        public String decodeString(final ByteBuffer bytes) {
            // set correct limit (strip '\n')
            bytes.limit(scanForEnd(bytes));

            // destination buffer needs to be large enough
            // note: this is single-byte encoding, so we can equate bytes to chars
            CharBuffer chars = getTemporaryCharBuffer(bytes.remaining());

            stringDecoder.reset();
            stringDecoder.decode(bytes, chars, true);
            stringDecoder.flush(chars);
            chars.flip();
            return chars.toString();
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
        private int scanForEnd(final ByteBuffer in) {
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

        /**
         * Round the given number up to the nearest power of two.
         *
         * @param value Value to round up.
         * @return Power of two that is greater or equal than the argument.
         */
        private int roundUp(int value) {
            int highestOneBit = Integer.highestOneBit(value);
            if (value == highestOneBit) {
                return value;
            }
            return highestOneBit << 1;
        }
    }
}
