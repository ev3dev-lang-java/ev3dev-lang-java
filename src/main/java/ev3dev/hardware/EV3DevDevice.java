package ev3dev.hardware;

import ev3dev.utils.Sysfs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Properties;

/**
 * This class been designed to discover if the library is used in:
 *
 * - EV3 Brick
 * - Raspberry Pi 1 + PiStorms
 * - Raspberry Pi 1 + BrickPi
 *
 *  At the moment, the class extends from Device,
 *  but close method doesn´t close any real resource.
 *
 * @author Juan Antonio Breña Moral
 *
 *
 */
public abstract class EV3DevDevice {

    private static final Logger LOGGER = LoggerFactory.getLogger(EV3DevDevice.class);

    protected final Properties ev3DevProperties;
    protected final EV3DevPlatform CURRENT_PLATFORM;

    protected static final String LEGO_PORT = "lego-port";
    protected static final String ADDRESS = "address";
    protected static final String LEGO_SENSOR = "lego-sensor";
    protected static final String MODE = "mode";
    protected static final String DEVICE = "set_device";

    protected File PATH_DEVICE = null;

    /**
     * Constructor
     */
    public EV3DevDevice() {

        final EV3DevPropertyLoader ev3DevPropertyLoader = new EV3DevPropertyLoader();
        ev3DevProperties = ev3DevPropertyLoader.getEV3DevProperties();
        final EV3DevPlatforms ev3DevPlatforms = EV3DevPlatforms.getInstance();
        CURRENT_PLATFORM = ev3DevPlatforms.getPlatform();
    }

    //TODO Rename method to detect

    /**
     * This method matches a input with the internal position in EV3Dev.
     *
     * @param type     type
     * @param portName port
     */
    protected void detect(final String type, final String portName) {
        final String devicePath = EV3DevFileSystem.getRootPath() + "/" + type;
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Retrieving devices in path: ", devicePath);
        }
        final List<File> deviceAvailables = Sysfs.getElements(devicePath);
        boolean connected = false;
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Checking devices on: {}", devicePath);
        }
        String pathDeviceName;
        int deviceCounter = 1;
        for (File deviceAvailable : deviceAvailables) {
            PATH_DEVICE = deviceAvailable;
            pathDeviceName = PATH_DEVICE + "/" + ADDRESS;
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Device {}:", deviceCounter);
            }
            String result = Sysfs.readString(pathDeviceName);
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Port expected: {}, actual: {}.", portName, result);
            }
            //TODO Review to use equals. It is more strict
            if (result.contains(portName)) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Detected device on path: {}, {}", pathDeviceName, result);
                }
                connected = true;
                break;
            } else {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Skipped device");
                }
            }
            deviceCounter++;
        }

        if (!connected) {
            throw new RuntimeException("The device was not detected in: " + portName);
        }
    }

    /**
     * Returns the value of an attribute supported for a Device
     *
     * @param attribute attribute
     * @return value
     */
    protected String getStringAttribute(final String attribute) {
        return Sysfs.readString(PATH_DEVICE + "/" + attribute);
    }

    /**
     * Returns the value of an attribute supported for a Device
     *
     * @param attribute attribute
     * @return value
     */
    protected int getIntegerAttribute(final String attribute) {
        return Sysfs.readInteger(PATH_DEVICE + "/" + attribute);
    }

    /**
     * Set a value on an attribute
     *
     * @param attribute attribute
     * @param value     value
     */
    protected void setStringAttribute(final String attribute, final String value) {
        final boolean result = Sysfs.writeString(this.PATH_DEVICE + "/" + attribute, value);
        if (!result) {
            throw new RuntimeException("Operation not executed: "
                + this.PATH_DEVICE + "/" + attribute + " with value " + value);
        }
    }

    /**
     * Set a value on an attribute
     *
     * @param attribute attribute
     * @param value     value
     */
    protected void setIntegerAttribute(final String attribute, final int value) {
        setStringAttribute(attribute, Integer.toString(value));
    }

}
