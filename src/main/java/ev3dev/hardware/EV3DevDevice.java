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


public @Slf4j abstract class EV3DevDevice extends EV3DevPlatform  {

    protected static final String LEGO_PORT = "lego-port";
    protected static final String ADDRESS = "address";
    protected static final String LEGO_SENSOR = "lego-sensor";
    protected static final String MODE = "mode";
    protected static final String DEVICE = "set_device";

    protected File PATH_DEVICE = null;

    //TODO Rename method to detect
    /**
     * This method matches a input with the internal position in EV3Dev.
     * @param type type
     * @param portName port
     */
    protected void detect(final String type, final String portName) {
        log.debug("Detecting device on port: {}", portName);
        final String devicePath = ROOT_PATH + type;
        final List<File> deviceAvailables = Sysfs.getElements(devicePath);

        boolean connected = false;
        String pathDeviceName;
        for (File deviceAvailable : deviceAvailables) {
            PATH_DEVICE = deviceAvailable;
            pathDeviceName = PATH_DEVICE + "/" + ADDRESS;
            if (Sysfs.readString(pathDeviceName).equals(portName)) {
                log.debug("Detected port on path: {}", pathDeviceName);
                connected = true;
                break;
            }
        }

        if(!connected){
            throw new RuntimeException("The device was not detected in: " + portName);
        }
    }

    /**
     * Returns the value of an attribute supported for a Device
     *
     * @param attribute attribute
     * @return value
     */
    protected String getStringAttribute(final String attribute){
        return Sysfs.readString(PATH_DEVICE + "/" +  attribute);
    }

    /**
     * Returns the value of an attribute supported for a Device
     *
     * @param attribute attribute
     * @return value
     */
    protected int getIntegerAttribute(final String attribute){
        return Sysfs.readInteger(PATH_DEVICE + "/" +  attribute);
    }

    /**
     * Set a value on an attribute
     *
     * @param attribute attribute
     * @param value value
     */
    protected void setStringAttribute(final String attribute, final String value){
        final boolean result = Sysfs.writeString(this.PATH_DEVICE + "/" +  attribute, value);
        if(!result){
            throw new RuntimeException("Operation not executed: " + this.PATH_DEVICE + "/" +  attribute + " with value " + value);
        }
    }

    /**
     * Set a value on an attribute
     *
     * @param attribute attribute
     * @param value value
     */
    protected void setIntegerAttribute(final String attribute, final int value){
        final boolean result = Sysfs.writeInteger(this.PATH_DEVICE + "/" +  attribute, value);
        if(!result){
            throw new RuntimeException("Operation not executed: " + this.PATH_DEVICE + "/" +  attribute + " with value " + value);
        }
    }
	
}
