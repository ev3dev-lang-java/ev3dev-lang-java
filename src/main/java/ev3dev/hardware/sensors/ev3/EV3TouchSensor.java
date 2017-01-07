package ev3dev.hardware.sensors.ev3;

import ev3dev.hardware.sensors.BaseSensor;
import ev3dev.hardware.sensors.SensorMode;
import ev3dev.utils.Sysfs;

import java.io.File;

/**
 * Basic sensors driver for the Lego EV3 Touch sensors
 * @author andy
 *
 */
/**
 * <b>Lego EV3 Touch sensors</b><br>
 * The analog EV3 Touch Sensor is a simple but exceptionally precise tool that detects when its front button is pressed or released.
 *
 * 
 * 
 * See <a href="http://www.ev-3.net/en/archives/846"> Sensor Product page </a>
 * See <a href="http://sourceforge.net/p/lejos/wiki/Sensor%20Framework/"> The
 *      leJOS sensors framework</a>
 * See <a href="http://www.ev3dev.org/docs/sensors/#uart-sensors"> The UART Sensors</a>
 * See {@link lejos.robotics.SampleProvider leJOS conventions for
 *      SampleProviders}
 *
 * 
 * 
 */
public class EV3TouchSensor extends BaseSensor {

    private static final String LEGO_EV3_TOUCH = "lego-ev3-touch";

    public EV3TouchSensor(final String portName) {
		super(portName, LEGO_UART_SENSOR, LEGO_EV3_TOUCH);
		init();
	}

	private void init() {
      setModes(new SensorMode[]{ new TouchMode(this.PATH_DEVICE) }); 
    }

    /**
     * <b>Lego EV3 Touch sensors, Touch mode</b><br>
     * Detects when its front button is pressed
     * 
     * <p>
     * <b>Size and content of the sample</b><br>
     * The sample contains one element, a value of 0 indicates that the button is not presse, a value of 1 indicates the button is pressed.
     * 
     * </p>
     * 
     * @return A sampleProvider
     * See {@link lejos.robotics.SampleProvider leJOS conventions for
     *      SampleProviders}
     */
    public SensorMode getTouchMode() {
        return getMode(0);
    }

    
    private class TouchMode extends EV3DevSensorMode {
    	
    	private File pathDevice = null;
    	
        public TouchMode(File pathDevice) {
        	this.pathDevice = pathDevice;
		}

		@Override
		public int sampleSize() {
            return 1;
        }

		@Override
        public void fetchSample(float[] sample, int offset) {
            sample[offset] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0);
        }

		@Override
        public String getName() {
           return "Touch";
        }
     
    }

}
