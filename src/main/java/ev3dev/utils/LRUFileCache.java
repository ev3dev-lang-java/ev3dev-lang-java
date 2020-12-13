package ev3dev.utils;

import com.sun.jna.LastErrorException;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

/**
 * LRU cache for caching file descriptors
 *
 * @author Jakub Vanek
 */
class LRUFileCache {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LRUFileCache.class);
    private final LinkedQueue<String> expirationQueue;
    private final HashMap<String, Entry> openFiles;
    private final int capacity;
    private final int compactionThreshold;
    private final boolean writable;

    /**
     * Initialize a new LRU file cache.
     *
     * @param capacity            How many files to keep open.
     * @param compactionThreshold With how many files open to trigger cache eviction.
     * @param writable            Whether to open the files for writing or reading.
     */
    public LRUFileCache(int capacity, int compactionThreshold, boolean writable) {
        this.expirationQueue = new LinkedQueue<>();
        this.openFiles = new HashMap<>();
        this.capacity = capacity;
        this.compactionThreshold = compactionThreshold;
        this.writable = writable;
    }

    /**
     * Get a file descriptor for the given path. If necessary, opens a new descriptor.
     *
     * @param path Path to open.
     * @return File descriptor for reading/writing depending on writable mode of this instance.
     * @throws LastErrorException if the open() call fails
     */
    public Entry open(String path) throws IOException {
        // check if we already have the file open
        Entry file = openFiles.get(path);
        if (file != null) {
            // yes, let's move it up in the expiration queue
            expirationQueue.makeLatest(file.queueNode);
        } else {
            // nope, let's open a new one
            FileChannel channel;
            if (writable) {
                channel = FileChannel.open(Path.of(path), StandardOpenOption.WRITE,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } else {
                channel = FileChannel.open(Path.of(path), StandardOpenOption.READ);
            }
            var node = expirationQueue.insertNewLatest(path);
            file = new Entry(channel, node, path);
            openFiles.put(path, file);
            // expire/remove too old files once we reach threshold
            if (expirationQueue.size() > compactionThreshold) {
                compact();
            }
        }
        return file;
    }

    /**
     * Remove excess files from the cache.
     */
    private void compact() {
        // while we have too many files
        while (openFiles.size() > capacity) {
            // drop it from expiration queue & registry
            var oldest = expirationQueue.removeLast();
            Entry file = openFiles.remove(oldest.key);
            // and close it (and don't crash unrelated threads)
            try {
                file.channel.close();
            } catch (IOException e) {
                log.warn("Cannot close FD from Sysfs: ", e);
            }
        }
    }

    /**
     * Close all files.
     */
    public void clear() {
        for (Entry file : openFiles.values()) {
            try {
                file.channel.close();
            } catch (IOException e) {
                log.warn("Cannot close FD from Sysfs: ", e);
            }
        }
        openFiles.clear();
        expirationQueue.clear();
    }

    /**
     * File entry for the main LRU hashtable.
     */
    public static final class Entry {
        /**
         * Expiration queue node associated with this entry.
         */
        public final LinkedQueue<String>.Node queueNode;
        /**
         * Channel opened for that node.
         */
        public final FileChannel channel;
        /**
         * If this file is a part of real sysfs.
         */
        public final boolean isSysfs;

        /**
         * Create a new hashtable entry for a LRU cache.
         *
         * @param fc  File channel for this file.
         * @param lru Node in the expiration queue.
         */
        public Entry(FileChannel fc, LinkedQueue<String>.Node lru, String path) {
            this.channel = fc;
            this.queueNode = lru;
            this.isSysfs = path.startsWith("/sys/");
        }
    }
}
