package ev3dev.utils.io;

/**
 * JVM-wide standard c library implementation.
 */
public final class DefaultLibc {
    /**
     * Reference to the standard c library.
     */
    private static ILibc global;

    /**
     * Get global libc implementation.
     *
     * @return {@link NativeLibc} or other implementation if set.
     */
    public static synchronized ILibc get() {
        if (global == null)
            global = new NativeLibc();
        return global;
    }

    /**
     * Set global libc implementation.
     *
     * @param value Libc to use, typically a mocked implementation for testing.
     */
    public static synchronized void set(ILibc value) {
        global = value;
    }
}
