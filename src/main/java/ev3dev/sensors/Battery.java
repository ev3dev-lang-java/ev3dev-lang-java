package ev3dev.sensors;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.SupportedPlatform;
import ev3dev.utils.Sysfs;

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

    private static final String BATTERY =  "power_supply";
    private static final String BATTERY_EV3 =  "legoev3-battery";
    private static final String BATTERY_PISTORMS =  "pistorms-battery";
    private static final String BATTERY_BRICKPI =  "brickpi-battery";
    private static final String BATTERY_PATH = DEVICE_ROOT_PATH + BATTERY;
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
    	final String platform = this.getPlatform();
        switch (platform) {
            case SupportedPlatform.EV3BRICK:
                BATTERY_PATH_LOCAL += BATTERY_PATH + "/" + BATTERY_EV3;
                break;
            case SupportedPlatform.PISTORMS:
                BATTERY_PATH_LOCAL += BATTERY_PATH + "/" + BATTERY_PISTORMS;
                break;
            case SupportedPlatform.BRICKPI:
                BATTERY_PATH_LOCAL += BATTERY_PATH + "/" + BATTERY_BRICKPI;
                break;
        }
    }
	
	/**
	 * Returns voltage of the battery in microvolts.
	 * @return voltage
	 */
	public float getVoltage() {
		return Sysfs.readFloat(BATTERY_PATH_LOCAL + "/" +  VOLTAGE);
	}

	/**
	 * Returns the current of the battery in microamps.
	 * @return current
	 */
	public float getBatteryCurrent() {
    	if(this.getPlatform().equals(SupportedPlatform.EV3BRICK)){
    		return Sysfs.readFloat(BATTERY_PATH + "/" + BATTERY_EV3 + "/" +  CURRENT);
    	}
    	return -1f;
	}

}
