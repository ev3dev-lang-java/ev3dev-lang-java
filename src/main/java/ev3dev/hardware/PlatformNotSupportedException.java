package ev3dev.hardware;

/**
 * Exception thrown when the device is not supported in a platform.
 * 
 * @author Juan Antonio Brenha Moral
 *
 */
public class PlatformNotSupportedException extends RuntimeException {

    public PlatformNotSupportedException(String message) {
        super (message);
    }

}
