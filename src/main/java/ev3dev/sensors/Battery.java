package ev3dev.sensors;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.Sysfs;
import lejos.hardware.Power;
import org.slf4j.Logger;

/**
 * The class Battery interacts with EV3Dev to get information about battery used. 
 * 
 * @see <a href="https://www.kernel.org/doc/Documentation/power/power_supply_class.txt">https://www.kernel.org/doc/Documentation/power/power_supply_class.txt</a>
 * @see <a href="https://github.com/ev3dev/ev3dev-lang/blob/develop/wrapper-specification.md#direct-attribute-mappings-5">https://github.com/ev3dev/ev3dev-lang/blob/develop/wrapper-specification.md#direct-attribute-mappings-5</a>
 * 
 * @author Juan Antonio Breña Moral
 *
 */
public class Battery extends EV3DevDevice implements Power {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Battery.class);

    //TODO Review test to decouple paths from Class. Use Properties
    public static final String BATTERY = ev3DevProperties.getProperty("battery");
    public static final String BATTERY_EV3 =  ev3DevProperties.getProperty("ev3.battery");;
    public static final String BATTERY_PISTORMS =  ev3DevProperties.getProperty("pistorms.battery");
    public static final String BATTERY_BRICKPI = ev3DevProperties.getProperty("brickpi.battery");;
    public static final String BATTERY_BRICKPI3 =  ev3DevProperties.getProperty("brickpi3.battery");

    private static String BATTERY_PATH;
    public static final String VOLTAGE = "voltage_now";
    public static final String CURRENT = "current_now";

    private String BATTERY_PATH_LOCAL = "";

	private static Battery instance;

    public static Battery getInstance() {
        if (instance == null) {
            instance = new Battery();
        }
        return instance;
    }

    // Prevent duplicate objects
    private Battery() {
        init();
    }

    private void init(){
        LOGGER.debug("Init sensor");

        //TODO Create separator variable for the whole project
        BATTERY_PATH = EV3DevFileSystem.getRootPath() + "/" + BATTERY;
        if(CURRENT_PLATFORM.equals(EV3DevPlatform.EV3BRICK)) {
            BATTERY_PATH_LOCAL += BATTERY_PATH + "/" + BATTERY_EV3;
        } else if(CURRENT_PLATFORM.equals(EV3DevPlatform.PISTORMS)) {
            BATTERY_PATH_LOCAL += BATTERY_PATH + "/" + BATTERY_PISTORMS;
        } else if(CURRENT_PLATFORM.equals(EV3DevPlatform.BRICKPI)) {
            BATTERY_PATH_LOCAL += BATTERY_PATH + "/" + BATTERY_BRICKPI;
        } else if(CURRENT_PLATFORM.equals(EV3DevPlatform.BRICKPI3)) {
            BATTERY_PATH_LOCAL += BATTERY_PATH + "/" + BATTERY_BRICKPI3;
        }
    }

    @Override
    public int getVoltageMilliVolt() {
        return (int) Sysfs.readFloat(BATTERY_PATH_LOCAL + "/" +  VOLTAGE) / 1000;
    }

    /**
	 * Returns voltage of the battery in microvolts.
	 * @return voltage
	 */
	public float getVoltage() {
	    LOGGER.debug(Sysfs.getElements(BATTERY_PATH_LOCAL).toString());
		return Sysfs.readFloat(BATTERY_PATH_LOCAL + "/" +  VOLTAGE) / 1000000;
	}

	//TODO Review output
    //TODO Review units
	/**
	 * Returns the current of the battery in amps.
	 * @return current
	 */
	public float getBatteryCurrent() {
    	if (CURRENT_PLATFORM.equals(EV3DevPlatform.EV3BRICK)){
    		return Sysfs.readFloat(BATTERY_PATH + "/" + BATTERY_EV3 + "/" +  CURRENT);
    	} else {
            LOGGER.warn("This method is not available for {} & {}", EV3DevPlatform.PISTORMS, EV3DevPlatform.BRICKPI);
            return -1f;
        }
	}

	//TODO Review this method in the future.
    @Override
    public float getMotorCurrent() {
        throw new UnsupportedOperationException("This feature is not implemented");
    }

}
