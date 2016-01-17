package ev3dev.hardware.sensor.ev3;

import java.io.File;

import lejos.robotics.SampleProvider;
import ev3dev.hardware.EV3DevSysfs;
import ev3dev.hardware.sensor.BaseSensor;
import ev3dev.hardware.sensor.SensorMode;

/**
 * <b>EV3 Gyro sensor</b><br>
 * The digital EV3 Gyro Sensor measures the sensors rotational motion and changes in its orientation. 
 * 
 * 
 * <p>
 * <table border=1>
 * <tr>
 * <th colspan=4>Supported modes</th>
 * </tr>
 * <tr>
 * <th>Mode name</th>
 * <th>Description</th>
 * <th>unit(s)</th>
 * <th>Getter</th>
 * </tr>
 * <tr>
 * <td>Angle</td>
 * <td>Measures the orientation of the sensor</td>
 * <td>Degrees</td>
 * <td> {@link #getAngleMode() }</td>
 * </tr>
 * <tr>
 * <td>Rate</td>
 * <td>Measures the angular velocity of the sensor</td>
 * <td>Degrees / second</td>
 * <td> {@link #getRateMode() }</td>
 * </tr>
 * <tr>
 * <td>Rate and Angle</td>
 * <td>Measures both angle and angular velocity</td>
 * <td>Degrees, Degrees / second</td>
 * <td> {@link #getAngleAndRateMode() }</td>
 * </tr>
 * </table>
 * 
 * 
 * <p>
 * <b>Sensor configuration</b><br>
 * Use {@link #reset()} to recalibrate the sensor and to reset accumulated angle to zero. Keep the sensor motionless during a reset. 
 * The sensor shuld also be motionless during initialization.
 * 
 * <p>
 * 
 * See <a href="http://www.ev-3.net/en/archives/849"> Sensor Product page </a>
 * See <a href="http://sourceforge.net/p/lejos/wiki/Sensor%20Framework/"> The
 *      leJOS sensor framework</a>
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
	
	public EV3GyroSensor(String sensorPort) {
		super(sensorPort);
		setModes(new SensorMode[] { new AngleMode(this.PATH_DEVICE) });
	}

	  /**
	   * <b>EV3 Gyro sensor, Angle mode</b><br>
	   * Measures the orientation of the sensor in respect to its start orientation. 
	   * 
	   * <p>
	   * <b>Size and content of the sample</b><br>
	   * The sample contains one elements representing the orientation (in Degrees) of the sensor in respect to its start position. 
	   * 
	   * <p>
	   * <b>Configuration</b><br>
	   * The start position can be set to the current position using the reset method of the sensor.
	   * 
	   * @return A sampleProvider
	   * See {@link lejos.robotics.SampleProvider leJOS conventions for
	   *      SampleProviders}
	   */
	  public SampleProvider getAngleMode() {
	    return getMode(0);
	  }
	
	  /**
	   * Hardware calibration of the Gyro sensor and reset off accumulated angle to zero. <br>
	   * The sensor should be motionless during calibration.
	   */
	  public void reset() {
	    // Reset mode (4) is not used here as it behaves eratically. Instead the reset is done implicitly by going to mode 1.
	    switchMode(1, SWITCHDELAY);
	    // And back to 3 to prevent another reset when fetching the next sample
	    switchMode(3, SWITCHDELAY);
	  }
	  
	public int getAngle(){
		String attribute = "value0";
		return readInteger(this.PATH_DEVICE + "/" +  attribute);
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
		      //port.getShorts(raw, 0, raw.length);
				String attribute = "value0";
				float raw = readFloat(this.pathDevice + "/" +  attribute);
				sample[offset] = raw * toSI;
		    }

		    @Override
		    public String getName() {
		      return "Angle";
		    }

		  }
	
}
