package ev3dev.hardware.port;

/**
 * Interface that provides a binding between a physical port and the different
 * types of sensor interfaces that can be used with it
 * @author andy
 *
 */
public interface Port
{
    /**
     * return the string name used to reference this physical port
     * @return a string representation of the port
     */
    public String getName();

    /**
     * Obtain access to a class that can be used to talk to the port hardware
     * @param portclass the required port interface
     * @return a class that implements the requested interface
     */
    //public <T extends IOPort> T open(Class<T> portclass);
    
}
