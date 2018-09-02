package ev3dev.hardware.display;

import com.sun.jna.LastErrorException;
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
    public static synchronized DisplayInterface initializeRealDisplay() {
        ILibc libc = new NativeLibc();

        LOGGER.debug("initializing new real display");
        try {
            return new OwnedDisplay(libc);
        } catch (RuntimeException e) {
            if (e.getCause() instanceof LastErrorException &&
                    ((LastErrorException) e.getCause()).getErrorCode() == NativeConstants.ENOTTY) {
                LOGGER.debug("but the failure was caused by not having a real TTY, using fake console");
                // we do not run from Brickman
                return new StolenDisplay(libc);
            } else {
                throw e;
            }
        }
    }
}
