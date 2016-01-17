package ev3dev.hardware;

import java.io.File;
import java.util.ArrayList;

/**
 * Base class to interact with EV3Dev sysfs
 * 
 * @author Juan Antonio Breña Moral
 *
 */
public class EV3DevDevice extends EV3DevSysfs {

    private final String DEVICE_ROOT_PATH = "/sys/class/";
    private final String ADDRESS = "address"; 
	protected File PATH_DEVICE = null;
    private boolean connected = false;   
	
	/**
	 * Every device connected in a EV3 Brick with EV3Dev appears in /sys/class in a determinated category.
	 * It is necessary to indicate the type and port.
	 * 
	 * @param type A valid type. Example: tacho-motor, lego-sensor, etc...
	 * @param portName The port where is connected the sensor or the actuator.
	 * @throws DeviceException
	 */
    public EV3DevDevice(final String type, final String portName) throws DeviceException {
    	
    	this.connect(type, portName);

    	if(this.connected == false){
    		throw new DeviceException("The device was not detected in: " + portName);
    	}
    }
    
    /**
     * This method matches a input with the internal position in EV3Dev.
     * @param type
     * @param portName
     */
    public void connect(final String type, final String portName){
    	final String devicePath = DEVICE_ROOT_PATH + type;
    	ArrayList<File> deviceAvailables = this.getElements(devicePath);

    	String pathDeviceName = "";
    	for(int x=0; x < deviceAvailables.size(); x++) {
    		PATH_DEVICE = deviceAvailables.get(x);
    		pathDeviceName = PATH_DEVICE + "/" + ADDRESS;
    		if (this.readString(pathDeviceName).equals(portName)){
    			this.connected = true;
    			break;
    		}
    	}
    }
    
    /**
     * Returns the value of an attribute supported for a Device
     * 
     * @param attribute
     * @return
     */
    public String getStringAttribute(final String attribute){
        return this.readString(PATH_DEVICE + "/" +  attribute);
    }

    /**
     * Returns the value of an attribute supported for a Device
     * 
     * @param attribute
     * @return
     */
    public int getIntegerAttribute(final String attribute){
        return this.readInteger(PATH_DEVICE + "/" +  attribute);
    }
    
    /**
     * Set a value on an attribute
     * 
     * @param attribute
     * @param value
     */
    public void setStringAttribute(final String attribute, final String value){
    	this.writeString(this.PATH_DEVICE + "/" +  attribute, value);
    }
   
    /**
     * Set a value on an attribute
     * 
     * @param attribute
     * @param value
     */
    public void setIntegerAttribute(final String attribute, final int value){
    	this.writeInteger(this.PATH_DEVICE + "/" +  attribute, value);
    }
   
}
