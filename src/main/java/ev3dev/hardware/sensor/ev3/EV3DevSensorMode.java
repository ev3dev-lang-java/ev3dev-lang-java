package ev3dev.hardware.sensor.ev3;

import ev3dev.hardware.EV3DevSysfs;
import ev3dev.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

/**
 * 
 * @author Juan Antonio Breña Moral
 *
 */
public abstract class EV3DevSensorMode extends EV3DevSysfs  implements SampleProvider, SensorMode{

	protected final String VALUE0 = "value0";
	protected final String VALUE1 = "value1";
	protected final String VALUE2 = "value2";
	
}
