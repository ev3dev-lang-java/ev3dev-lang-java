package ev3dev.hardware;

/**
 * The class Battery interacts with EV3Dev to get information about battery used. 
 * 
 * @see <a href="https://www.kernel.org/doc/Documentation/power/power_supply_class.txt">https://www.kernel.org/doc/Documentation/power/power_supply_class.txt</a>
 * @see <a href="https://github.com/ev3dev/ev3dev-lang/blob/develop/wrapper-specification.md#direct-attribute-mappings-5">https://github.com/ev3dev/ev3dev-lang/blob/develop/wrapper-specification.md#direct-attribute-mappings-5</a>
 * 
 * @author Juan Antonio Breña Moral
 *
 */
public class Battery extends EV3DevSysfs implements Power{

	private final String DEVICE_ROOT_PATH = "/sys/class/";
	private final String BATTERY_PATH = DEVICE_ROOT_PATH + "power_supply/legoev3-battery/";
	private final String VOLTAGE = "voltage_now";
	private final String CURRENT = "current_now";
	
    private static Battery Instance;

    public static Battery getInstance() {
        if (Instance == null) {
        	Instance = new Battery();
        }

        return Instance;
    }

    // Prevent duplicate objects
    private Battery() {

    }
	
	/**
	 * Returns voltage of the battery in microvolts.
	 * @return voltage
	 */
	public float getVoltage() {
		return readInteger(BATTERY_PATH +  VOLTAGE);
	}

	/**
	 * Returns the current of the battery in microamps.
	 * @return current
	 */
	public float getBatteryCurrent() {
		return readInteger(BATTERY_PATH +  CURRENT);
	}

}
