package ev3dev.utils;

import org.junit.Assert;
import org.junit.Test;

public class LinkedQueueTest {

    @Test
    public void insert() {
        final LinkedQueue<String> queue = new LinkedQueue<>();
        final var node1 = queue.insertNewLatest("node1");

        Assert.assertEquals(queue.size(), 1);
        Assert.assertEquals(node1.key, "node1");
    }

    @Test
    public void delete() {
        final LinkedQueue<String> queue = new LinkedQueue<>();
        final var node1in = queue.insertNewLatest("node1");
        final var node2in = queue.insertNewLatest("node2");
        final var node1out = queue.removeLast();
        final var node2out = queue.removeLast();
        Assert.assertEquals(node1in.key, node1out.key);
        Assert.assertEquals(node2in.key, node2out.key);
    }

    @Test
    public void deleteEmpty() {
        final LinkedQueue<String> queue = new LinkedQueue<>();
        final var empty = queue.removeLast();
        Assert.assertNull(empty);
    }

    @Test
    public void makeLatest() {
        final LinkedQueue<String> queue = new LinkedQueue<>();
        final var node1in = queue.insertNewLatest("node1");
        final var node2in = queue.insertNewLatest("node2");
        queue.makeLatest(node1in);
        final var node2out = queue.removeLast();
        final var node1out = queue.removeLast();
        Assert.assertEquals(node1in.key, node1out.key);
        Assert.assertEquals(node2in.key, node2out.key);
    }

    @Test
    public void makeFirstLatest() {
        final LinkedQueue<String> queue = new LinkedQueue<>();
        final var node1in = queue.insertNewLatest("node1");
        final var node2in = queue.insertNewLatest("node2");
        queue.makeLatest(node2in);
        final var node1out = queue.removeLast();
        final var node2out = queue.removeLast();
        Assert.assertEquals(node1in.key, node1out.key);
        Assert.assertEquals(node2in.key, node2out.key);
    }

    @Test
    public void makeFirstInTheMiddle() {
        final LinkedQueue<String> queue = new LinkedQueue<>();
        final var node1in = queue.insertNewLatest("node1");
        final var node2in = queue.insertNewLatest("node2");
        final var node3in = queue.insertNewLatest("node3");
        queue.makeLatest(node2in);
        final var node1out = queue.removeLast();
        final var node3out = queue.removeLast();
        final var node2out = queue.removeLast();
        Assert.assertEquals(node1in.key, node1out.key);
        Assert.assertEquals(node2in.key, node2out.key);
        Assert.assertEquals(node3in.key, node3out.key);
    }

    @Test
    public void clear() {
        final LinkedQueue<String> queue = new LinkedQueue<>();
        queue.insertNewLatest("node1");
        queue.clear();
        Assert.assertEquals(queue.size(), 0);
    }
}
