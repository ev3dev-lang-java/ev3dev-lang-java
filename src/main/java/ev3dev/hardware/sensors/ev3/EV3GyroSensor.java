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
		setModes(new SensorMode[] { new AngleMode(this.PATH_DEVICE) });
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
	    return getMode(0);
	  }
	
	  /**
	   * Hardware calibration of the Gyro sensors and reset off accumulated angle to zero. <br>
	   * The sensors should be motionless during calibration.
	   */
	  public void reset() {
	    // Reset mode (4) is not used here as it behaves eratically. Instead the reset is done implicitly by going to mode 1.
	    switchMode(1, SWITCHDELAY);
	    // And back to 3 to prevent another reset when fetching the next sample
	    switchMode(3, SWITCHDELAY);
	  }
	  
	public int getAngle(){
		String attribute = "value0";
		return Sysfs.readInteger(this.PATH_DEVICE + "/" +  attribute);
	}
	
	  private class AngleMode extends EV3DevSensorMode {

		    private static final int   MODE = 3;
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
	
}
