package ev3dev.sensors;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.EV3DevPlatforms;
import ev3dev.utils.Sysfs;
import lejos.hardware.Power;
import lombok.extern.slf4j.Slf4j;

/**
 * The class Battery interacts with EV3Dev to get information about battery used. 
 * 
 * @see <a href="https://www.kernel.org/doc/Documentation/power/power_supply_class.txt">https://www.kernel.org/doc/Documentation/power/power_supply_class.txt</a>
 * @see <a href="https://github.com/ev3dev/ev3dev-lang/blob/develop/wrapper-specification.md#direct-attribute-mappings-5">https://github.com/ev3dev/ev3dev-lang/blob/develop/wrapper-specification.md#direct-attribute-mappings-5</a>
 * 
 * @author Juan Antonio Breña Moral
 *
 */
public @Slf4j class Battery extends EV3DevDevice implements Power {

    private static final String BATTERY =  "power_supply";
    private static final String BATTERY_EV3 =  "legoev3-battery";
    private static final String BATTERY_PISTORMS =  "pistorms-battery";
    private static final String BATTERY_BRICKPI =  "brickpi-battery";
    private static String BATTERY_PATH;
    private static final String VOLTAGE = "voltage_now";
    private static final String CURRENT = "current_now";

    private String BATTERY_PATH_LOCAL = "";

	private static Battery Instance;

    public static Battery getInstance() {
        if (Instance == null) {
        	Instance = new Battery();
        }
        return Instance;
    }

    // Prevent duplicate objects
    private Battery() {
        //TODO Create separator variable for the whole project
        BATTERY_PATH = ROOT_PATH + "/" + BATTERY;
        final EV3DevPlatforms platform = this.getPlatform();
    	if(platform.equals(EV3DevPlatforms.EV3BRICK)) {
            BATTERY_PATH_LOCAL += BATTERY_PATH + "/" + BATTERY_EV3;
        } else if(platform.equals(EV3DevPlatforms.PISTORMS)) {
            BATTERY_PATH_LOCAL += BATTERY_PATH + "/" + BATTERY_PISTORMS;
        } else {
            BATTERY_PATH_LOCAL += BATTERY_PATH + "/" + BATTERY_BRICKPI;
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
		return Sysfs.readFloat(BATTERY_PATH_LOCAL + "/" +  VOLTAGE) / 1000000;
	}

	/**
	 * Returns the current of the battery in microamps.
	 * @return current
	 */
	public float getBatteryCurrent() {
    	if(this.getPlatform().equals(EV3DevPlatforms.EV3BRICK)){
    		return Sysfs.readFloat(BATTERY_PATH + "/" + BATTERY_EV3 + "/" +  CURRENT);
    	}else {
            log.warn("This method is not available for {} & {}", EV3DevPlatforms.PISTORMS, EV3DevPlatforms.BRICKPI);
            return -1f;
        }
	}

    @Override
    public float getMotorCurrent() {
        throw new UnsupportedOperationException("This feature is not implemented");
    }

}
