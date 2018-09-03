package ev3dev.hardware.display;

import com.sun.jna.LastErrorException;
import ev3dev.actuators.LCD;
import ev3dev.utils.io.ILibc;
import ev3dev.utils.io.NativeConstants;
import ev3dev.utils.io.NativeLibc;
import lombok.extern.slf4j.Slf4j;

/**
 * Helper class for initializing real on-brick display.
 *
 * @author Jakub Vaněk
 * @since 2.4.7
 */
@Slf4j
public final class SystemDisplay {
    private SystemDisplay() {
    }

    /**
     * <p>Initialize real on-brick display.</p>
     * <p><b>BEWARE:</b> this function may be called only once,
     * otherwise the behavior is undefined.</p>
     *
     * @return new instance of display appropriate for the current session
     * @throws RuntimeException initialization of the display fails
     */
    public static DisplayInterface initializeRealDisplay() {
        ILibc libc = new NativeLibc();

        LOGGER.debug("initializing new real display");
        try {
            return new OwnedDisplay(libc);
        } catch (LastErrorException e) {
            if (e.getErrorCode() == NativeConstants.ENOTTY) {
                LOGGER.debug("but the failure was caused by not having a real TTY, using fake console");
                // we do not run from Brickman
                return new StolenDisplay(libc);
            } else {
                throw e;
            }
        }
    }

    /**
     * <p>Initialize real on-brick display with framebuffer.</p>
     * <p><b>BEWARE:</b> this function may be called only once,
     * otherwise the behavior is undefined.</p>
     *
     * @return new instance of framebuffer appropriate for the current session
     * @throws RuntimeException initialization of the display or framebuffer fails
     */
    public static JavaFramebuffer initializeRealFramebuffer() {
        return initializeRealDisplay().openFramebuffer();
    }

    /**
     * <p>Initialize real on-brick display with framebuffer and LCD.</p>
     * <p><b>BEWARE:</b> this function may be called only once,
     * otherwise the behavior is undefined.</p>
     *
     * @return new instance of framebuffer appropriate for the current session
     * @throws RuntimeException initialization of the display or framebuffer fails
     */
    public static LCD initializeRealLCD() {
        return new LCD(initializeRealFramebuffer());
    }
}
