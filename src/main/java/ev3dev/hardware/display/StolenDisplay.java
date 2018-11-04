package ev3dev.hardware.display;

import ev3dev.utils.io.ILibc;
import ev3dev.utils.io.NativeFramebuffer;
import ev3dev.utils.Brickman;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Class to allow running programs over SSH
 *
 * @author Jakub Vaněk
 * @since 2.4.7
 */
@Slf4j
class StolenDisplay extends DisplayInterface {
    private ILibc libc;

    /**
     * noop
     */
    public StolenDisplay(@NonNull ILibc libc) {
        this.libc = libc;
        Brickman.disable();
    }

    /**
     * noop, graphics goes to the display
     */
    @Override
    public void switchToGraphicsMode() {
        LOGGER.trace("Switch to graphics mode");
    }

    /**
     * noop, text goes to SSH host
     */
    @Override
    public void switchToTextMode() {
        LOGGER.trace("Switch to text mode");
    }

    /**
     * noop, we do not have any resources
     */
    @Override
    public void close() {
        LOGGER.trace("Display close");
        // free objects
        closeFramebuffer();
        libc = null;
    }

    @Override
    public synchronized JavaFramebuffer openFramebuffer() {
        if (fbInstance == null) {
            LOGGER.debug("Initialing framebuffer in fake console");
            initializeFramebuffer(new NativeFramebuffer("/dev/fb0", libc), true);
        }
        return fbInstance;
    }
}
