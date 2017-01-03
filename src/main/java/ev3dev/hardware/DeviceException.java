package ev3dev.hardware;

/**
 * Exception thrown when errors are detected in a sensors device state.
 * 
 * @author Andy Shaw
 * @author Juan Antonio Brenha Moral
 *
 */
public class DeviceException extends RuntimeException {

    public DeviceException() {
    }

    public DeviceException(String message)
    {
        super (message);
    }

    public DeviceException(Throwable cause)
    {
        super (cause);
    }

    public DeviceException(String message, Throwable cause)
    {
        super (message, cause);
    }
}
