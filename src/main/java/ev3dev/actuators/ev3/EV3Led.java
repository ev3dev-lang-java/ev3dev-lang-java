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

    public static final String LEFT_LED = "leds/ev3:left";
    public static final String RIGHT_LED = "leds/ev3:right";
    public static final String RED_LED = ":red:ev3dev";
    public static final String GREEN_LED = ":green:ev3dev";
    public static final String BRIGHTNESS = "brightness";

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
				Sysfs.writeInteger("/sys/class/leds/led0:red:brick-status/brightness", 0);
				Sysfs.writeInteger("/sys/class/leds/led0:green:brick-status/brightness", 0);
			} else {
				Sysfs.writeInteger("/sys/class/leds/led1:red:brick-status/brightness", 0);
				Sysfs.writeInteger("/sys/class/leds/led1:green:brick-status/brightness", 0);
			}
		} else if (pattern == 1) {
			if (direction == LEFT) {
				Sysfs.writeInteger("/sys/class/leds/led0:red:brick-status/brightness", 0);
				Sysfs.writeInteger("/sys/class/leds/led0:green:brick-status/brightness", 255);
			} else {
				Sysfs.writeInteger("/sys/class/leds/led1:red:brick-status/brightness", 0);
				Sysfs.writeInteger("/sys/class/leds/led1:green:brick-status/brightness", 255);
			}
		} else if (pattern == 2) {
			if (direction == LEFT){
				Sysfs.writeInteger("/sys/class/leds/led0:red:brick-status/brightness", 255);
				Sysfs.writeInteger("/sys/class/leds/led0:green:brick-status/brightness", 0);
			} else {
				Sysfs.writeInteger("/sys/class/leds/led1:red:brick-status/brightness", 255);
				Sysfs.writeInteger("/sys/class/leds/led1:green:brick-status/brightness", 0);
			}
		} else if (pattern == 3) {
			if (direction == LEFT) {
				Sysfs.writeInteger("/sys/class/leds/led0:red:brick-status/brightness", 255);
				Sysfs.writeInteger("/sys/class/leds/led0:green:brick-status/brightness", 255);
			} else {
				Sysfs.writeInteger("/sys/class/leds/led1:red:brick-status/brightness", 255);
				Sysfs.writeInteger("/sys/class/leds/led1:green:brick-status/brightness", 255);
			}
		} else if (pattern > 3) {
			log.debug("This feature is not implemented");
		}
	}

}