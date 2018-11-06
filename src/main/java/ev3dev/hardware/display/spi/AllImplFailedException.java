package ev3dev.hardware.display.spi;

/**
 * Situation when SPI is to be open, but none of
 * the available implementations worked.
 *
 * @author Jakub Vaněk
 * @since 2.4.7
 */
public class AllImplFailedException extends RuntimeException {
    /**
     * Initialize new exception.
     */
    public AllImplFailedException() {
        super();
    }

    /**
     * Initialize new exception with message.
     *
     * @param message Message detailing the problem.
     */
    public AllImplFailedException(String message) {
        super(message);
    }
}
