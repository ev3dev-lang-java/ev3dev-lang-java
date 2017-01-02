package ev3dev.hardware;

import ev3dev.utils.Sysfs;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * Base class to interact with EV3Dev Sensors
 * 
 * @author Juan Antonio Breña Moral
 *
 */
public @Slf4j class EV3DevSensorDevice extends EV3DevDevice {

	private final String DEVICE_ROOT_PATH = "/sys/class/";
	private final String LEGO_PORT = "lego-ports";
	private final String LEGO_SENSOR = "lego-sensor";
    private final String ADDRESS = "address";
	private final String MODE = "mode";
	private final String DEVICE = "set_device";

	protected File PATH_DEVICE = null;
    private boolean connected = false;

	/**
	 * Every device connected in a EV3 Brick with EV3Dev appears in /sys/class in a determinated category.
	 * It is necessary to indicate the type and ports.
	 *
	 * @param portName The ports where is connected the sensors or the actuators.
	 * @throws DeviceException
	 */
    public EV3DevSensorDevice(final String portName, final String mode, final String device) throws DeviceException {

		//EV3 Brick detect in a automatic way the sensors
		if(this.getPlatform().equals(SupportedPlatform.EV3BRICK)){

			if(!this.detect(LEGO_SENSOR, portName)) {
				throw new DeviceException("The device was not detected in: " + portName);
			}
		}else {

			//With Pi Boards, it is necessary to detect in 2 paths the sensors
			if(this.detect(LEGO_PORT, portName)){
				log.info("detected lego ports");
				log.info("" + this.PATH_DEVICE);

				Sysfs.writeString(this.PATH_DEVICE + "/" +  MODE, mode);
				Sysfs.writeString(this.PATH_DEVICE + "/" +  DEVICE, device);

				if(this.detect(LEGO_SENSOR, portName)) {
					throw new DeviceException("The device was not detected in: " + portName);
				}
			}else {
				throw new DeviceException("The device was not detected in: " + portName);
			}
		}

    }

    /**
     * This method matches a input with the internal position in EV3Dev.
	 *
	 * @param type
	 * @param portName
     * @return
     */
    public boolean detect(final String type, final String portName){
    	final String devicePath = DEVICE_ROOT_PATH + type;
    	List<File> deviceAvailables = Sysfs.getElements(devicePath);

    	String pathDeviceName = "";
    	for(int x=0; x < deviceAvailables.size(); x++) {
    		PATH_DEVICE = deviceAvailables.get(x);
    		pathDeviceName = PATH_DEVICE + "/" + ADDRESS;
    		if (Sysfs.readString(pathDeviceName).equals(portName)){
    			this.connected = true;
				return true;
    		}
    	}
		return false;
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
		Sysfs.writeString(this.PATH_DEVICE + "/" +  attribute, value);
    }
   
    /**
     * Set a value on an attribute
     * 
     * @param attribute
     * @param value
     */
    public void setIntegerAttribute(final String attribute, final int value){
		Sysfs.writeInteger(this.PATH_DEVICE + "/" +  attribute, value);
    }
   
}
