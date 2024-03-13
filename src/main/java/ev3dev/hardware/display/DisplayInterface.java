package ev3dev.hardware.display;

import com.sun.jna.LastErrorException;
import ev3dev.utils.io.NativeFramebuffer;
import lombok.NonNull;

import java.io.Closeable;
import java.io.IOException;

/**
 * <p>Display manager interface.</p>
 *
 * <p>This class provides interface for switching between text and
 * graphics display modes. It also provides interface for opening
 * graphics framebuffer. </p>
 *
 * @author Jakub Vaněk
 * @since 2.4.7
 */
public abstract class DisplayInterface implements Closeable {
    protected JavaFramebuffer fbInstance = null;

    /**
     * <p>Switch the display to a graphics mode.</p>
     *
     * @throws RuntimeException when the switch fails
     */
    public abstract void switchToGraphicsMode();

    /**
     * <p>Switch the display to a text mode.</p>
     *
     * @throws RuntimeException when the switch fails
     */
    public abstract void switchToTextMode();

    /**
     * <p>Get the framebuffer for the system display.</p>
     *
     * <p>The framebuffer is initialized only once, later calls
     * return references to the same instance.</p>
     *
     * @return Java framebuffer compatible with the system display.
     * @throws RuntimeException when switch to graphics mode or the framebuffer initialization fails.
     */
    public abstract JavaFramebuffer openFramebuffer();

    /**
     * <p>Remove all references to this framebuffer.</p>
     *
     * @param fb Framebuffer to remove.
     */
    public void releaseFramebuffer(JavaFramebuffer fb) {
        if (fb != null && fb == fbInstance) {
            fbInstance = null;
        } else {
            throw new IllegalArgumentException("Framebuffer must be non-null and identical to the builtin framebuffer");
        }
    }

    /**
     * Close the internal framebuffer.
     */
    protected void closeFramebuffer() {
        if (fbInstance != null) {
            try {
                fbInstance.close();
            } catch (IOException | LastErrorException e) {
                System.err.println("Error occured during framebuffer shutdown: " + e.getMessage());
                e.printStackTrace();
            } finally {
                fbInstance = null;
            }
        }
    }

    /**
     * Initialize new internal instance of JavaFramebuffer.
     * @param backend Device behind JavaFramebuffer.
     * @param enable Whether to enable framebuffer flushing from the beginning.
     */
    protected void initializeFramebuffer(@NonNull NativeFramebuffer backend, boolean enable) {
        fbInstance = new RGBFramebuffer(backend, this);
        fbInstance.setFlushEnabled(enable);
        fbInstance.clear();
        fbInstance.storeData();
    }
}
