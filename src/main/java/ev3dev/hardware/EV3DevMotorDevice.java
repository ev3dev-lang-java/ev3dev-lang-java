package ev3dev.hardware;

import ev3dev.utils.Sysfs;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * Base class to interact with EV3Dev sysfs
 * 
 * @author Juan Antonio Breña Moral
 *
 */
public @Slf4j class EV3DevMotorDevice extends EV3DevDevice {

	private final String DEVICE_ROOT_PATH = "/sys/class/";
    private final String ADDRESS = "address"; 
	protected File PATH_DEVICE = null;
    private boolean connected = false;   
	
	/**
	 * Every device connected in a EV3 Brick with EV3Dev appears in /sys/class in a determinated category.
	 * It is necessary to indicate the type and ports.
	 * 
	 * @param type A valid type. Example: tacho-motors, lego-sensors, etc...
	 * @param portName The ports where is connected the sensors or the actuators.
	 * @throws DeviceException
	 */
    public EV3DevMotorDevice(final String type, final String portName) throws DeviceException {

		//This method is oriented for EV3Brick, but for Pi Boards, it is necessary to detect in a previous action
        if(!this.getPlatform().equals(SupportedPlatform.EV3BRICK)) {
            this.connect(type, portName);
        }
    }
    
    //TODO Rename method to detect
    /**
     * This method matches a input with the internal position in EV3Dev.
     * @param type
     * @param portName
     */
    public void connect(final String type, final String portName){
    	log.debug("Detecting motors on port/tacho-motors: {}", portName);
        final String devicePath = DEVICE_ROOT_PATH + type;
    	List<File> deviceAvailables = Sysfs.getElements(devicePath);

        this.connected = false;
    	String pathDeviceName = "";
    	for(int x=0; x < deviceAvailables.size(); x++) {
    		PATH_DEVICE = deviceAvailables.get(x);
    		pathDeviceName = PATH_DEVICE + "/" + ADDRESS;
    		if (Sysfs.readString(pathDeviceName).equals(portName)){
    			log.debug("Detected port: {} on path: {}", portName, pathDeviceName);
                this.connected = true;
    			break;
    		}
    	}

        if(this.connected == false){
            throw new DeviceException("The device was not detected in: " + portName);
        }
    }
    
    /**
     * Returns the value of an attribute supported for a Device
     * 
     * @param attribute
     * @return
     */
    public String getStringAttribute(final String attribute){
        return Sysfs.readString(PATH_DEVICE + "/" +  attribute);
    }

    /**
     * Returns the value of an attribute supported for a Device
     * 
     * @param attribute
     * @return
     */
    public int getIntegerAttribute(final String attribute){
        return Sysfs.readInteger(PATH_DEVICE + "/" +  attribute);
    }
    
    /**
     * Set a value on an attribute
     * 
     * @param attribute
     * @param value
     */
    public void setStringAttribute(final String attribute, final String value){
		final boolean result = Sysfs.writeString(this.PATH_DEVICE + "/" +  attribute, value);
		if(!result){
			throw new RuntimeException("Operation not executed:" + attribute + value);
		}
    }
   
    /**
     * Set a value on an attribute
     * 
     * @param attribute
     * @param value
     */
    public void setIntegerAttribute(final String attribute, final int value){
		final boolean result = Sysfs.writeInteger(this.PATH_DEVICE + "/" +  attribute, value);
		if(!result){
			throw new RuntimeException("Operation not executed:" + attribute + value);
		}
    }
   
}
