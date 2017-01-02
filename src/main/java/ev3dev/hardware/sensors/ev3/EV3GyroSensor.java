package ev3dev.hardware.sensors.ev3;

import ev3dev.hardware.sensors.BaseSensor;
import ev3dev.hardware.sensors.SensorMode;
import ev3dev.utils.Sysfs;
import lejos.robotics.SampleProvider;

import java.io.File;

/**
 * <b>EV3 Gyro sensors</b><br>
 * The digital EV3 Gyro Sensor measures the sensors rotational motion and changes in its orientation. 
 *
 * 
 * 
 * <p>
 * <b>Sensor configuration</b><br>
 * Use {@link #reset()} to recalibrate the sensors and to reset accumulated angle to zero. Keep the sensors motionless during a reset.
 * The sensors shuld also be motionless during initialization.
 * 
 * <p>
 * 
 * See <a href="http://www.ev-3.net/en/archives/849"> Sensor Product page </a>
 * See <a href="http://sourceforge.net/p/lejos/wiki/Sensor%20Framework/"> The
 *      leJOS sensors framework</a>
 * See {@link lejos.robotics.SampleProvider leJOS conventions for
 *      SampleProviders}
 * 
 *      <p>
 * 
 * 
 * @author Andy Shaw
 * @author Aswin Bouwmeester
 * @author Juan Antonio Breña Moral
 * 
 */
public class EV3GyroSensor extends BaseSensor {

	private static final long SWITCHDELAY = 200;
	
	public EV3GyroSensor(String portName) {
		super(portName, "ev3-uart", "lego-ev3-gyro");
		setModes(new SensorMode[] {
				new RateMode(this.PATH_DEVICE),
				new AngleMode(this.PATH_DEVICE),
				new RateAndAngleMode(this.PATH_DEVICE)
		});
	}

	/**
	 * <b>EV3 Gyro sensor, Rate mode</b><br>
	 * Measures angular velocity of the sensor.
	 *
	 * <p>
	 * <b>Size and content of the sample</b><br>
	 * The sample contains one elements representing the angular velocity (in Degrees / second) of the sensor.
	 *
	 * <p>
	 * <b>Configuration</b><br>
	 * The sensor can be recalibrated using the reset method of the sensor.
	 *
	 * @return A sampleProvider
	 * See {@link lejos.robotics.SampleProvider leJOS conventions for
	 *      SampleProviders}
	 */
	public SampleProvider getRateMode() {
		return getMode(0);
	}

	/**
	* <b>EV3 Gyro sensors, Angle mode</b><br>
	* Measures the orientation of the sensors in respect to its start orientation.
	*
	* <p>
	* <b>Size and content of the sample</b><br>
	* The sample contains one elements representing the orientation (in Degrees) of the sensors in respect to its start position.
	*
	* <p>
	* <b>Configuration</b><br>
	* The start position can be set to the current position using the reset method of the sensors.
	*
	* @return A sampleProvider
	* See {@link lejos.robotics.SampleProvider leJOS conventions for
	*      SampleProviders}
	*/
	public SampleProvider getAngleMode() {
		return getMode(1);
	}

	/**
	 * <b>EV3 Gyro sensor, Rate mode</b><br>
	 * Measures both angle and angular velocity of the sensor.
	 *
	 * <p>
	 * <b>Size and content of the sample</b><br>
	 * The sample contains two elements. The first element contains angular velocity (in degrees / second). The second element contain angle (in degrees).
	 *
	 * <p>
	 * <b>Configuration</b><br>
	 * The sensor can be recalibrated using the reset method of the sensor.
	 *
	 * @return A sampleProvider
	 * See {@link lejos.robotics.SampleProvider leJOS conventions for
	 *      SampleProviders}
	 */
	public SampleProvider getAngleAndRateMode() {
		return getMode(2);
	}

	/**
	* Hardware calibration of the Gyro sensors and reset off accumulated angle to zero. <br>
	* The sensors should be motionless during calibration.
	*/
	public void reset() {
		// Reset mode (4) is not used here as it behaves erratically. Instead the reset is done implicitly by going to mode 1.
		switchMode("GYRO-RATE", SWITCHDELAY);
		// And back to 3 to prevent another reset when fetching the next sample
		switchMode("GYRO-G&A", SWITCHDELAY);
	}

	public int getAngle(){
		String attribute = "value0";
		return Sysfs.readInteger(this.PATH_DEVICE + "/" +  attribute);
	}

	private class RateMode extends EV3DevSensorMode {
		private static final String MODE = "GYRO-RATE";
		private static final float toSI = -1;

		private File pathDevice = null;

		public RateMode(File pathDevice) {
			this.pathDevice = pathDevice;
		}

		@Override
		public int sampleSize() {
			return 1;
		}

		@Override
		public void fetchSample(float[] sample, int offset) {
			switchMode(MODE, SWITCHDELAY);
			//port.getShorts(raw, 0, raw.length);
			String attribute = "value0";
			float raw = Sysfs.readFloat(this.pathDevice + "/" +  attribute);
			sample[offset] = raw * toSI;
		}

		@Override
		public String getName() {
			return "Rate";
		}

	}

	private class AngleMode extends EV3DevSensorMode {

		private static final String MODE = "GYRO-ANG";
		private static final float toSI = -1;

		private File pathDevice = null;

		public AngleMode(File pathDevice) {
			this.pathDevice = pathDevice;
		}

		@Override
		public int sampleSize() {
		  return 1;
		}

		@Override
		public void fetchSample(float[] sample, int offset) {
			switchMode(MODE, SWITCHDELAY);
			//ports.getShorts(raw, 0, raw.length);
			String attribute = "value0";
			float raw = Sysfs.readFloat(this.pathDevice + "/" +  attribute);
			sample[offset] = raw * toSI;
		}

		@Override
		public String getName() {
		  return "Angle";
		}
	}

	private class RateAndAngleMode extends EV3DevSensorMode {
		private static final String MODE = "GYRO-G&A";
		private static final float toSI = -1;

		private File pathDevice = null;

		public RateAndAngleMode(File pathDevice) {
			this.pathDevice = pathDevice;
		}

		@Override
		public int sampleSize() {
			return 2;
		}

		@Override
		public void fetchSample(float[] sample, int offset) {
			switchMode(MODE, SWITCHDELAY);
			String attribute = "value0";
			float raw = Sysfs.readFloat(this.pathDevice + "/" +  attribute);
			sample[0] = raw * toSI;
			attribute = "value1";
			raw = Sysfs.readFloat(this.pathDevice + "/" +  attribute);
			sample[1] = raw * toSI;
		}

		@Override
		public String getName() {
			return "Angle and Rate";
		}

	}

}
