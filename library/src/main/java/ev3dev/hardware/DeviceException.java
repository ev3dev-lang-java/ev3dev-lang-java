package ev3dev.hardware;

public class DeviceException extends Exception {

    public DeviceException()
    {
    }

    public DeviceException(final String message)
    {
        super (message);
    }

    public DeviceException(Throwable cause)
    {
        super (cause);
    }

    public DeviceException(final String message, Throwable cause)
    {
        super (message, cause);
    }
	
}
