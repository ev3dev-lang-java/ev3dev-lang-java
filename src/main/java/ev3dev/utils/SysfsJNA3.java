package ev3dev.utils;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import ev3dev.utils.io.ILibc;
import ev3dev.utils.io.DefaultLibc;
import org.slf4j.Logger;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static ev3dev.utils.io.NativeConstants.*;

/**
 * The class responsible to interact with Sysfs on EV3Dev
 *
 * @author Juan Antonio Breña Moral
 */
public class SysfsJNA3 {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SysfsJNA3.class);
    private static final int FAST_ATTRIBUTE_SIZE = 16;
    private static final int MAX_ATTRIBUTE_SIZE = 4096;
    private static final ILibc libc = DefaultLibc.get();
    // LRU caches for reducing the per-call overhead
    // second argument: when we have this many files open, trigger the eviction process
    // first argument: evict files until we have only this many files open
    private static final FileCache readCache = new FileCache(16, 32, libc, false);
    private static final FileCache writeCache = new FileCache(16, 32, libc, true);

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

            int fd = writeCache.open(filePath);
            int count = libc.pwrite(fd, data, data.remaining(), 0);
            if (count < 0) {
                throw new LastErrorException(Native.getLastError());
            }
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

            int fd = readCache.open(filePath);
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
            int fd = writeCache.open(path);
            int count = libc.write(fd, buffer, buffer.limit());
            if (count < 0) {
                throw new LastErrorException(Native.getLastError());
            }
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

    /**
     * LRU cache for caching file descriptors
     */
    private static class FileCache {
        private final LinkedList<String> age;
        private final HashMap<String, Integer> fdMap;
        private final int capacity;
        private final int threshold;
        private final ILibc libc;
        private final boolean writable;

        /**
         * Initialize new LRU cache.
         * @param capacity How many files to keep open.
         * @param libc C library implementation to use for opening/closing files.
         * @param writable Whether to open files as read-only or write-only.
         */
        public FileCache(int capacity, int threshold, ILibc libc, boolean writable) {
            this.age = new LinkedList<>();
            this.fdMap = new HashMap<>();
            this.capacity = capacity;
            this.threshold = threshold;
            this.libc = libc;
            this.writable = writable;
        }

        /**
         * Get a file descriptor for the given path. If necessary, opens a new descriptor.
         * @param path Path to open.
         * @return File descriptor for reading/writing depending on writable mode of this instance.
         * @throws LastErrorException if the open() call fails
         */
        @SuppressWarnings("OctalInteger")
        public synchronized int open(String path) {
            // try if we already have the file
            Integer fd = fdMap.get(path);
            if (fd != null) {
                // yes, let's move it up in the expiration queue
                age.remove(path);
                age.add(path);
            } else {
                // nope, let's open a new one
                if (writable) {
                    fd = libc.open(path, O_WRONLY | O_CREAT | O_TRUNC, 0644);
                } else {
                    fd = libc.open(path, O_RDONLY, 0644);
                }
                if (fd < 0) {
                    throw new LastErrorException(Native.getLastError());
                }
                // register it
                fdMap.put(path, fd);
                // add it to expiration queue
                age.add(path);
                // expire/remove too old files once we reach threshold
                if (fdMap.size() > threshold) {
                    compact();
                }
            }
            return fd;
        }

        /**
         * Remove excess files from the cache.
         */
        private void compact() {
            // while we have too many files
            while (fdMap.size() > capacity) {
                // drop it from expiration queue & registry
                String oldKey = age.remove();
                int fd = fdMap.remove(oldKey);
                // and close it (and don't crash unrelated threads)
                try {
                    libc.close(fd);
                } catch (LastErrorException e) {
                    log.warn("Cannot close FD from Sysfs: ", e);
                }
            }
        }
    }
}
