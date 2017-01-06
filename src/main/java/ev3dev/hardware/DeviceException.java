package ev3dev.hardware;

/**
 * Exception thrown when errors are detected in a sensors device state.
 * 
 * @author Andy Shaw
 * @author Juan Antonio Brenha Moral
 *
 */
class DeviceException extends RuntimeException {

    public DeviceException(String message)
    {
        super (message);
    }

    public DeviceException(String message, Throwable cause) {
        super (message, cause);
    }
}
