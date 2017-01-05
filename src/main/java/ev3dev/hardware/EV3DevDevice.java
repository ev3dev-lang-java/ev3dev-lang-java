package ev3dev.hardware;

import ev3dev.utils.Sysfs;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * This class been designed to discover if the library is used in:
 *
 * - EV3 Brick
 * - Raspberry Pi 1 + PiStorms
 * - Raspberry Pi 1 + BrickPi
 * 
 * @author Juan Antonio Breña Moral
 *
 * At the moment, the class extends from Device, but close method doesn´ close any real resource.
 */

@Slf4j
public class EV3DevDevice extends Device implements EV3DevDeviceCommands, SupportedPlatform {

    protected File PATH_DEVICE = null;
    protected boolean connected = false;

    /**
	 * This method returns the platform
	 * 
	 * @throws PlatformNotSupportedException
	 * @return
	 */
    @Override
	public String getPlatform() throws PlatformNotSupportedException{

		final String BATTERY_PATH = DEVICE_ROOT_PATH + BATTERY;
		final String EV3BRICK_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_EV3;
		final String PISTORMS_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_PISTORMS;
		final String BRICKPI_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_BRICKPI;

		if(Sysfs.existPath(EV3BRICK_DISCOVERY_PATTERN_PATH)){
			log.trace("Detected platform: " + SupportedPlatform.EV3BRICK);
			return EV3BRICK;
		} else if(Sysfs.existPath(PISTORMS_DISCOVERY_PATTERN_PATH)){
			log.trace("Detected platform: " + SupportedPlatform.PISTORMS);
			return PISTORMS;
		} else if(Sysfs.existPath(BRICKPI_DISCOVERY_PATTERN_PATH)){
			log.trace("Detected platform: " + SupportedPlatform.BRICKPI);
			return BRICKPI;
		} else {
			throw new PlatformNotSupportedException("Platform not supported");
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
