package ev3dev.hardware;

/**
 * Exception thrown when the device is not supported in a platform.
 * 
 * @author Juan Antonio Brenha Moral
 *
 */
public class DeviceNotSupportedException extends RuntimeException
{
    /**
     * 
     */
    private static final long serialVersionUID = 5846698127613306496L;

    public DeviceNotSupportedException() {
    }

    public DeviceNotSupportedException(String message) {
        super (message);
    }

    public DeviceNotSupportedException(Throwable cause) {
        super (cause);
    }

    public DeviceNotSupportedException(String message, Throwable cause) {
        super (message, cause);
    }
}
