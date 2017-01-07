package ev3dev.hardware;

/**
 * Exception thrown when the device is not supported in a platform.
 * 
 * @author Juan Antonio Brenha Moral
 *
 */
public class DeviceNotSupportedException extends RuntimeException {

    public DeviceNotSupportedException(String message) {
        super (message);
    }

}
