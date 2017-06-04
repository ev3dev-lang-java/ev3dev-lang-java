package ev3dev.actuators.ev3;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.Sysfs;
import lejos.hardware.LED;
import org.slf4j.Logger;

public class EV3Led extends EV3DevDevice implements LED {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(EV3Led.class);

	public static final int LEFT = 0;
    public static final int RIGHT = 1;

    final private int direction;

	public EV3Led(final int button) {

        if(!this.getPlatform().equals(EV3DevPlatform.EV3BRICK)){
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

	//TODO Refactor paths
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