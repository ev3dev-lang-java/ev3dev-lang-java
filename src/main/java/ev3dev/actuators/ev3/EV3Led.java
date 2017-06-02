package ev3dev.actuators.ev3;

import ev3dev.utils.Sysfs;
import lejos.hardware.LED;
import org.slf4j.Logger;

public class EV3Led implements LED {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(EV3Led.class);

	/**
	 * Left EV3 Button
	 */
	public static final int LEFT = 0;

    final private int direction;

	/**
	 * Right EV3 Button
	 */
	public static final int RIGHT = 1;

	public EV3Led(final int button) {

		if (button != 0 && button != 1){
			throw new RuntimeException("You are not specifying a EV3_LEFT_LED or EV3_RIGHT_LED field!");
		}

		direction = button == 0 ? LEFT : RIGHT;
	}

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
	public void setPattern(int pattern) {
		//Off
		if(pattern == 0) {
			if(direction == LEFT){
				Sysfs.writeInteger("/sys/class/leds/ev3:left:red:ev3dev/brightness", 0);
				Sysfs.writeInteger("/sys/class/leds/ev3:left:green:ev3dev/brightness", 0);
			}else{
				Sysfs.writeInteger("/sys/class/leds/ev3:right:red:ev3dev/brightness", 0);
				Sysfs.writeInteger("/sys/class/leds/ev3:right:green:ev3dev/brightness", 0);
			}
		}else if(pattern == 1) {
			if(direction == LEFT){
				Sysfs.writeInteger("/sys/class/leds/ev3:left:green:ev3dev/brightness", 255);
				Sysfs.writeInteger("/sys/class/leds/ev3:left:red:ev3dev/brightness", 0);
			}else{
				Sysfs.writeInteger("/sys/class/leds/ev3:right:green:ev3dev/brightness", 255);
				Sysfs.writeInteger("/sys/class/leds/ev3:right:red:ev3dev/brightness", 0);
			}
		}else if(pattern == 2) {
			if(direction == LEFT){
				Sysfs.writeInteger("/sys/class/leds/ev3:left:green:ev3dev/brightness", 0);
				Sysfs.writeInteger("/sys/class/leds/ev3:left:red:ev3dev/brightness", 255);
			}else{
				Sysfs.writeInteger("/sys/class/leds/ev3:right:green:ev3dev/brightness", 0);
				Sysfs.writeInteger("/sys/class/leds/ev3:right:red:ev3dev/brightness", 255);
			}
		}else if(pattern == 3) {
			if (direction == LEFT) {
				Sysfs.writeInteger("/sys/class/leds/ev3:left:green:ev3dev/brightness", 255);
				Sysfs.writeInteger("/sys/class/leds/ev3:left:red:ev3dev/brightness", 255);
			} else {
				Sysfs.writeInteger("/sys/class/leds/ev3:right:green:ev3dev/brightness", 255);
				Sysfs.writeInteger("/sys/class/leds/ev3:right:red:ev3dev/brightness", 255);
			}
		}else if(pattern > 3) {
			log.debug("This feature is not implemented");
		}
	}

}