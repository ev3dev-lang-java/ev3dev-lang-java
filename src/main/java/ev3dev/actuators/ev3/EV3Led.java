package ev3dev.actuators.ev3;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.Sysfs;
import lejos.hardware.LED;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EV3Led extends EV3DevDevice implements LED {

	private static final Logger log = LoggerFactory.getLogger(EV3Led.class);

	public static final int LEFT = 0;
    public static final int RIGHT = 1;

    final private int direction;

    private final String LEFT_LED = "/leds/ev3:left";
    private final String RIGHT_LED = "/leds/ev3:right";
    private final String RED_LED = ":red";
    private final String GREEN_LED = ":green";
    private final String BRIGHTNESS = ":ev3dev/brightness";

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
	public void setPattern(int pattern) {
		//Off
		if(pattern == 0) {
			if(direction == LEFT){
				setBrighness(LEFT_LED, RED_LED, 0);
				setBrighness(LEFT_LED, GREEN_LED, 0);
			}else{
				setBrighness(RIGHT_LED, RED_LED, 0);
				setBrighness(RIGHT_LED, GREEN_LED, 0);
			}
		}else if(pattern == 1) {
			if(direction == LEFT){
				setBrighness(LEFT_LED, RED_LED, 0);
				setBrighness(LEFT_LED, GREEN_LED, 255);
			}else{
				setBrighness(RIGHT_LED, RED_LED, 0);
				setBrighness(RIGHT_LED, GREEN_LED, 255);
			}
		}else if(pattern == 2) {
			if(direction == LEFT){
				setBrighness(LEFT_LED, RED_LED, 255);
				setBrighness(LEFT_LED, GREEN_LED, 0);
			}else {
				setBrighness(RIGHT_LED, RED_LED, 255);
				setBrighness(RIGHT_LED, GREEN_LED, 0);
			}
		}else if(pattern == 3) {
			if (direction == LEFT) {
				setBrighness(LEFT_LED, RED_LED, 255);
				setBrighness(LEFT_LED, GREEN_LED, 255);
			} else {
				setBrighness(RIGHT_LED, RED_LED, 255);
				setBrighness(RIGHT_LED, GREEN_LED, 255);
			}
		}else if(pattern > 3) {
			log.warn("This feature is not implemented");
		}
	}

	private void setBrighness(final String LED, final String color, final int brighnessLevel) {
		Sysfs.writeInteger(ROOT_PATH + LED + color + BRIGHTNESS, brighnessLevel);
	}

}