package ev3dev.hardware.sensors.ev3;

import ev3dev.hardware.sensors.BaseSensor;
import ev3dev.hardware.sensors.SensorMode;
import ev3dev.utils.Sysfs;

import java.io.File;

/**
 * <b>EV3 Infra Red sensors</b><br>
 * The digital EV3 Infrared Seeking Sensor detects proximity to the robot and reads signals emitted by the EV3 Infrared Beacon. The sensors can alse be used as a receiver for a Lego Ev3 IR remote control.
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
 * <td>Distance</td>
 * <td>Measures the distance to an object in front of the sensors</td>
 * <td>Undefined</td>
 * <td> {@link #getDistanceMode() }</td>
 * </tr>
 * <tr>
 * <td>Seek</td>
 * <td>Locates up to four beacons</td>
 * <td>Undefined, undefined</td>
 * <td> {@link #getSeekMode() }</td>
 * </tr>
 * </table><p>
 * 
 * <b>EV3 Infra Red sensors</b><br>
 * 
 * The sensors can be used as a receiver for up to four Lego Ev3 IR remote controls using the {@link #getRemoteCommand} and {@link #getRemoteCommands} methods.
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

    protected final static int SWITCH_DELAY = 250;

	public EV3IRSensor(String portName) {
        super(portName, "ev3-uart", "lego-ev3-ir");
		init();
	}

    protected void init() {
        setModes(new SensorMode[] {new DistanceMode(this.PATH_DEVICE)});
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
            //TODO Set mode
            //switchMode(IR_PROX, SWITCH_DELAY);
    		float raw = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0);

            if (raw < 5) sample[offset]=0;
            else if (raw > 55) sample[offset]=Float.POSITIVE_INFINITY;
            else sample[offset]= raw * toSI;
        }

        @Override
        public String getName() {
            return "Distance";
        }
        
    }
}
