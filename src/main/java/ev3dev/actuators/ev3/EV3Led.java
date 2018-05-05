package ev3dev.actuators.ev3;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.hardware.EV3DevPlatforms;
import ev3dev.utils.Sysfs;
import lejos.hardware.LED;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EV3Led extends EV3DevDevice implements LED {

	private static final Logger log = LoggerFactory.getLogger(EV3Led.class);

	public static final int LEFT = 0;
    public static final int RIGHT = 1;

    final private int direction;

    private final String LED_LEFT_RED = ev3DevProperties.getProperty("ev3.led.left.red");
	private final String LED_LEFT_GREEN = ev3DevProperties.getProperty("ev3.led.left.green");
	private final String LED_RIGHT_RED = ev3DevProperties.getProperty("ev3.led.right.red");
	private final String LED_RIGHT_GREEN = ev3DevProperties.getProperty("ev3.led.right.green");

	public EV3Led(final int button) {

        if(!EV3DevPlatforms.getPlatform().equals(EV3DevPlatform.EV3BRICK)){
            log.error("This actuator is specific of: {}", EV3DevPlatform.EV3BRICK);
            throw new RuntimeException("This actuator is specific of: " + EV3DevPlatform.EV3BRICK);
        }

        //TODO Use ENUM
		if (button != 0 && button != 1){
            log.error("You are not specifying any button.");
			throw new RuntimeException("You are not specifying any button.");
		}

		direction = button == 0 ? LEFT : RIGHT;
	}

	//TODO Add Enums for patterns
	/**
	 *
	 * 0: turn off button lights
	 * 1/2/3: static light green/red/yellow
	 * 4/5/6: normal blinking light green/red/yellow
	 * 7/8/9: fast blinking light green/red/yellow
	 * >9: same as 9.
	 *
	 * @param pattern
	 */
	@Override
	public void setPattern(final int pattern) {
		//Off
		if (pattern == 0) {
			if (direction == LEFT){
				Sysfs.writeInteger(LED_LEFT_RED, 0);
				Sysfs.writeInteger(LED_LEFT_GREEN, 0);
			} else {
				Sysfs.writeInteger(LED_RIGHT_RED, 0);
				Sysfs.writeInteger(LED_RIGHT_GREEN, 0);
			}
		} else if (pattern == 1) {
			if (direction == LEFT) {
				Sysfs.writeInteger(LED_LEFT_RED, 0);
				Sysfs.writeInteger(LED_LEFT_GREEN, 255);
			} else {
				Sysfs.writeInteger(LED_RIGHT_RED, 0);
				Sysfs.writeInteger(LED_RIGHT_GREEN, 255);
			}
		} else if (pattern == 2) {
			if (direction == LEFT){
				Sysfs.writeInteger(LED_LEFT_RED, 255);
				Sysfs.writeInteger(LED_LEFT_GREEN, 0);
			} else {
				Sysfs.writeInteger(LED_RIGHT_RED, 255);
				Sysfs.writeInteger(LED_RIGHT_GREEN, 0);
			}
		} else if (pattern == 3) {
			if (direction == LEFT) {
				Sysfs.writeInteger(LED_LEFT_RED, 255);
				Sysfs.writeInteger(LED_LEFT_GREEN, 255);
			} else {
				Sysfs.writeInteger(LED_RIGHT_RED, 255);
				Sysfs.writeInteger(LED_RIGHT_GREEN, 255);
			}
		} else if (pattern > 3) {
			log.debug("This feature is not implemented");
		}
	}

}