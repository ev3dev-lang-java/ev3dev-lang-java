package ev3dev.utils;

import fake_ev3dev.BaseElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Path;

public class LRUFileCacheTest {
    private static final Path rootPath = Path.of(BaseElement.EV3DEV_FAKE_SYSTEM_PATH);

    @Before
    public void cleanSpace() {
        try {
            Files.delete(rootPath);
        } catch (IOException ignored) { }
        try {
            Files.createDirectories(rootPath);
        } catch (IOException ignored) { }
    }

    @Test
    public void simpleRead() throws IOException {
        Path experiment = rootPath.resolve("test.txt");
        Files.write(experiment, new byte[]{'t', 'e', 's', 't', '\n'});
        var cache = new LRUFileCache(8, 8, false);
        var channel = cache.open(experiment.toString()).channel;
        try (var stream = Channels.newInputStream(channel);
             var reader = new BufferedReader(new InputStreamReader(stream))) {
            Assert.assertEquals(reader.readLine(), "test");
        }
        cache.clear();
    }

    @Test
    public void simpleWrite() throws IOException {
        Path experiment = rootPath.resolve("test.txt");
        var cache = new LRUFileCache(8, 8, true);
        var channel = cache.open(experiment.toString()).channel;
        try (var stream = Channels.newOutputStream(channel);
             var writer = new OutputStreamWriter(stream)) {
            writer.write("test2");
            writer.flush();
        }
        Assert.assertEquals(Files.readString(experiment), "test2");
        cache.clear();
    }

    @Test(expected = IOException.class)
    public void nonexistentRead() throws IOException {
        Path experiment = rootPath.resolve("void.txt");
        var cache = new LRUFileCache(8, 8, false);
        cache.open(experiment.toString());
        cache.clear();
    }

    @Test
    public void clearing() throws IOException{
        String t1 = rootPath.resolve("test1.txt").toString();
        String t2 = rootPath.resolve("test2.txt").toString();
        String t3 = rootPath.resolve("test3.txt").toString();
        String t4 = rootPath.resolve("test4.txt").toString();

        var cache = new LRUFileCache(8, 8, true);
        var f1 = cache.open(t1).channel;
        var f2 = cache.open(t2).channel;
        var f3 = cache.open(t3).channel;
        var f4 = cache.open(t4).channel;

        cache.clear();

        Assert.assertFalse(f1.isOpen());
        Assert.assertFalse(f2.isOpen());
        Assert.assertFalse(f3.isOpen());
        Assert.assertFalse(f4.isOpen());
    }

    @Test
    public void compaction() throws IOException{
        String t1 = rootPath.resolve("test1.txt").toString();
        String t2 = rootPath.resolve("test2.txt").toString();
        String t3 = rootPath.resolve("test3.txt").toString();
        String t4 = rootPath.resolve("test4.txt").toString();

        var cache = new LRUFileCache(2, 3, true);
        var f1 = cache.open(t1).channel;
        var f2 = cache.open(t2).channel;
        var f3 = cache.open(t3).channel;

        Assert.assertTrue(f1.isOpen());
        Assert.assertTrue(f2.isOpen());
        Assert.assertTrue(f3.isOpen());

        var f4 = cache.open(t4).channel;

        Assert.assertFalse(f1.isOpen());
        Assert.assertFalse(f2.isOpen());
        Assert.assertTrue(f3.isOpen());
        Assert.assertTrue(f4.isOpen());
        cache.clear();
    }

    @Test
    public void deduplication() throws IOException{
        String t1 = rootPath.resolve("test1.txt").toString();
        String t2 = rootPath.resolve("test2.txt").toString();

        var cache = new LRUFileCache(2, 3, true);
        var f1 = cache.open(t1).channel;
        var f2 = cache.open(t2).channel;
        var f3 = cache.open(t1).channel;

        Assert.assertNotSame(f1, f2);
        Assert.assertSame(f1, f3);
        cache.clear();
    }
}
