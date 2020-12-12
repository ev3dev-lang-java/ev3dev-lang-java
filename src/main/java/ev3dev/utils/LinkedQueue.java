package ev3dev.utils;

import java.util.LinkedList;

/**
 * Linked list with movable nodes.
 *
 * <p>This is an intentional {@link LinkedList} replacement, as that
 * does not support moving nodes without (de)allocations.</p>
 *
 * @author Jakub Vanek
 */
class LinkedQueue<T> {
    /**
     * Newest entry in the list.
     */
    private Node first;
    /**
     * Oldest entry in the list.
     */
    private Node last;
    /**
     * How many items there are in this list.
     */
    private int entries;

    /**
     * Create an empty linked list.
     */
    public LinkedQueue() {
        this.first = null;
        this.last = null;
        this.entries = 0;
    }

    /**
     * Insert a new node at the beginning.
     *
     * @param key Path in a filesystem.
     * @return Node representing that path.
     */
    public Node insertNewLatest(T key) {
        // create node
        Node node = new Node(key);
        // prepare local pointers
        node.prev = null;
        node.next = first;
        // first item already exists -> update its parent pointer
        if (first != null) {
            first.prev = node;
        } else { // this is the first item -> set it to last
            last = node;
        }
        // swap with first
        first = node;
        // update counter
        entries++;
        // done
        return node;
    }

    /**
     * Move a node to the front of the list.
     *
     * @param item Item to move.
     */
    public void makeLatest(Node item) {
        // if we're already first, we're done
        if (item.prev == null) {
            return;
        }
        // if we're not last, update our successor's parent pointer
        if (item.next != null) {
            item.next.prev = item.prev;
        } else { // else just update the global last pointer, as we're the last one
            last = item.prev;
        }
        // update our predecessor's next pointer (we're surely not first)
        item.prev.next = item.next;
        // update local node
        item.prev = null;
        item.next = first;
        // update first node's parent pointer
        first.prev = item;
        // update global first pointer
        first = item;
    }

    /**
     * Remove last node.
     *
     * @return Value of the removed node.
     */
    public Node removeLast() {
        // if no last node exists, return null
        if (last == null) {
            return null;
        }
        Node result = last;
        // if we have a predecessor, update its next pointer
        if (last.prev != null) {
            last.prev.next = null;
        } else { // else update the global first pointer
            first = null;
        }
        // update the global last pointer
        last = last.prev;
        // drop entry count
        entries--;
        // update internal pointers (not strictly necessary)
        result.next = result.prev = null;
        // return
        return result;
    }

    /**
     * Get the number of items in the list.
     *
     * @return Number of live items.
     */
    public int size() {
        return entries;
    }

    /**
     * Drop all items from the list.
     */
    public void clear() {
        // let gc do the hard work
        first = null;
        last = null;
        entries = 0;
    }

    /**
     * LRU linked list node
     */
    public final class Node {
        /**
         * Key associated with this entry.
         */
        public final T key;
        /**
         * Link to previous node.
         */
        public Node prev;
        /**
         * Link to next node.
         */
        public Node next;

        /**
         * Create a new linked list node.
         *
         * @param key Key associated with this node.
         */
        public Node(T key) {
            this.key = key;
            this.prev = null;
            this.next = null;
        }
    }
}
