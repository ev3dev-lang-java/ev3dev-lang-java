package ev3dev.sensors.ev3;

import ev3dev.sensors.BaseSensor;
import ev3dev.sensors.EV3DevSensorMode;
import ev3dev.utils.Sysfs;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.SensorMode;

import java.io.File;

/**
 * <b>EV3 Infra Red sensors</b><br>
 * The digital EV3 Infrared Seeking Sensor detects proximity to the robot and reads signals emitted by the EV3 Infrared Beacon. The sensors can alse be used as a receiver for a Lego Ev3 IR remote control.
 * 
 *
 * 
 * <b>EV3 Infra Red sensors</b><br>
 * 
 * The sensors can be used as a receiver for up to four Lego Ev3 IR remote controls using the methods.
*  
 * 
 * See <a href="http://www.ev-3.net/en/archives/848"> Sensor Product page </a>
 * See <a href="http://sourceforge.net/p/lejos/wiki/Sensor%20Framework/"> The
 *      leJOS sensors framework</a>
 * See {@link lejos.robotics.SampleProvider leJOS conventions for
 *      SampleProviders}
 * 
 *      <p>
 * 
 * 
 * @author Andy Shaw
 * @author Juan Antonio Breña Moral
 * 
 */
public class EV3IRSensor extends BaseSensor {

    private static final String LEGO_EV3_IR = "lego-ev3-ir";

    public static float MIN_RANGE = 5f;//cm
    public static float MAX_RANGE = 55f;//cm

	public EV3IRSensor(final Port portName) {
        super(portName, LEGO_UART_SENSOR, LEGO_EV3_IR);
		init();
	}

    private void init() {
        setModes(new SensorMode[] {
                new DistanceMode(this.PATH_DEVICE),
                new SeekMode(this.PATH_DEVICE),
                new RemoteMode(this.PATH_DEVICE)
        });
    }
	
    /**
     * <b>EV3 Infra Red sensors, Distance mode</b><br>
     * Measures the distance to an object in front of the sensors.
     * 
     * <p>
     * <b>Size and content of the sample</b><br>
     * The sample contains one element giving the distance to an object in front of the sensors. The distance provided is very roughly equivalent to meters
     * but needs conversion to give better distance. See product page for details. <br>
     * The effective range of the sensors in Distance mode  is about 5 to 50 centimeters. Outside this range a zero is returned
     * for low values and positive infinity for high values.
     * 
     * 
     * @return A sampleProvider
     * See {@link lejos.robotics.SampleProvider leJOS conventions for
     *      SampleProviders}
     * See <a href="http://www.ev-3.net/en/archives/848"> Sensor Product page </a>
     */    
	public SensorMode getDistanceMode() {
        return getMode(0);
    }

    private class DistanceMode extends EV3DevSensorMode {

        private static final String MODE = "IR-PROX";
        private static final float toSI = 1f;

    	private File pathDevice = null;
    	
        public DistanceMode(File pathDevice) {
        	this.pathDevice = pathDevice;
		}

		@Override
        public int sampleSize() {
            return 1;
        }

        @Override
        public void fetchSample(float[] sample, int offset) {
            switchMode(MODE, SWITCH_DELAY);
    		float rawValue = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0);

            if (rawValue < MIN_RANGE) {
                sample[offset] = 0;
            } else if (rawValue > MAX_RANGE) {
                sample[offset] = Float.POSITIVE_INFINITY;
            } else {
                sample[offset] = rawValue * toSI;
            }
        }

        @Override
        public String getName() {
            return "Distance";
        }
        
    }

    /**
     * <b>EV3 Infra Red sensor, Seek mode</b><br>
     * In seek mode the sensor locates up to four beacons and provides bearing and distance of each beacon.
     *
     * <p>
     * <b>Size and content of the sample</b><br>
     * The sample contains four pairs of elements in a single sample. Each pair gives bearing of  and distance to the beacon.
     * The first pair of elements is associated with a beacon transmitting on channel 0, the second pair with a beacon transmitting on channel 1 etc.<br>
     * The bearing values range from -25 to +25 (with values increasing clockwise
     * when looking from behind the sensor). A bearing of 0 indicates the beacon is
     * directly in front of the sensor. <br>
     * Distance values are not to scale. Al increasing values indicate increasing distance. <br>
     * If no beacon is detected both bearing is set to zero, and distance to positive infinity.
     *
     * <p>
     *
     * @return A sampleProvider
     * See {@link lejos.robotics.SampleProvider leJOS conventions for
     *      SampleProviders}
     * See <a href="http://www.ev-3.net/en/archives/848"> Sensor Product page </a>
     */
    public SensorMode getSeekMode() {
        return getMode(1);
    }

    private class SeekMode extends EV3DevSensorMode {

        private static final String MODE = "IR-SEEK";

        private static final float toSI = 1f;

        private File pathDevice = null;

        public SeekMode(File pathDevice) {
            this.pathDevice = pathDevice;
        }

        @Override
        public int sampleSize() {
            return 8;
        }

        @Override
        public void fetchSample(float[] sample, int offset) {
            switchMode(MODE, SWITCH_DELAY);
            sample[0] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0);
            sample[1] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE1);
            sample[2] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE2);
            sample[3] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE3);
            sample[4] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE4);
            sample[5] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE5);
            sample[6] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE6);
            sample[7] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE7);
        }

        @Override
        public String getName() {
            return "Seek";
        }

    }

    public SensorMode getRemoteMode() {
        return getMode(2);
    }

    private class RemoteMode extends EV3DevSensorMode {

        private static final String MODE = "IR-REMOTE";

        private static final float toSI = 1f;

        private File pathDevice = null;

        public RemoteMode(File pathDevice) {
            this.pathDevice = pathDevice;
        }

        @Override
        public int sampleSize() {
            return 4;
        }

        @Override
        public void fetchSample(float[] sample, int offset) {
            switchMode(MODE, SWITCH_DELAY);
            sample[0] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0);
            sample[1] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE1);
            sample[2] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE2);
            sample[3] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE3);
        }

        @Override
        public String getName() {
            return "Remote";
        }

    }

}
