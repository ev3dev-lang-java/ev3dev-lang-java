package ev3dev.hardware.display;

import java.io.Closeable;

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
public interface DisplayInterface extends Closeable {

    /**
     * <p>Switch the display to a graphics mode.</p>
     *
     * @throws RuntimeException when the switch fails
     */
    void switchToGraphicsMode();

    /**
     * <p>Switch the display to a text mode.</p>
     *
     * @throws RuntimeException when the switch fails
     */
    void switchToTextMode();

    /**
     * <p>Get the framebuffer for the system display.</p>
     *
     * <p>The framebuffer is initialized only once, later calls
     * return references to the same instance.</p>
     *
     * @return Java framebuffer compatible with the system display.
     * @throws RuntimeException when switch to graphics mode or the framebuffer initialization fails.
     */
    JavaFramebuffer openFramebuffer();
}
