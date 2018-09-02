package ev3dev.hardware.display;

import ev3dev.hardware.display.spi.FramebufferProvider;
import ev3dev.utils.AllImplFailedException;
import ev3dev.utils.io.ILibc;
import ev3dev.utils.io.NativeFramebuffer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Class to allow running programs over SSH
 *
 * @author Jakub Vaněk
 * @since 2.4.7
 */
@Slf4j
class StolenDisplay implements DisplayInterface {
    private JavaFramebuffer fbInstance = null;
    private ILibc libc;

    /**
     * noop
     */
    public StolenDisplay(ILibc libc) {
        this.libc = libc;
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
    public void close() throws IOException {
        LOGGER.trace("Display close");
    }

    @Override
    public synchronized JavaFramebuffer openFramebuffer() {
        if (fbInstance == null) {
            LOGGER.debug("Initialing framebuffer in fake console");
            Brickman.disable();
            try {
                NativeFramebuffer fbfd = new NativeFramebuffer("/dev/fb0", libc);
                fbInstance = FramebufferProvider.load(fbfd);
            } catch (AllImplFailedException e) {
                throw new RuntimeException("System framebuffer opening failed", e);
            }
            fbInstance.setFlushEnabled(true);
            fbInstance.clear();
            fbInstance.storeData();
        }
        return fbInstance;
    }
}
