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
	private String BATTERY_PATH = DEVICE_ROOT_PATH + "power_supply/";
	private final String EV3BRICK_BATTERY_OBJECT = "legoev3-battery/";
	private final String PISTORMS_BATTERY_OBJECT = "pistorm-battery/";
	private final String BRICKPI_BATTERY_OBJECT = "brickpi-battery/";
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
    	final String platform = this.getPlatform();
    	if(platform.equals(EV3BRICK)){
    		BATTERY_PATH += EV3BRICK_BATTERY_OBJECT;
    	} else if(platform.equals(PISTORMS)){
    		BATTERY_PATH += PISTORMS_BATTERY_OBJECT;
    	} else if(platform.equals(BRICKPI)){
    		BATTERY_PATH += BRICKPI_BATTERY_OBJECT;
    	}
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
    	if(this.getPlatform().equals(EV3BRICK)){
    		return readInteger(BATTERY_PATH +  CURRENT);
    	}
    	return -1f;
	}

}
