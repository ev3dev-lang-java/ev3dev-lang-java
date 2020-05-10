package ev3dev.sensors.ev3;

import ev3dev.sensors.BaseSensor;
import ev3dev.sensors.GenericMode;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Touch;

/**
 * <b>Lego EV3 Touch sensors</b><br>
 * The analog EV3 Touch Sensor is a simple but exceptionally precise tool that detects
 * when its front button is pressed or released.
 *
 * <p>See <a href="http://www.ev-3.net/en/archives/846"> Sensor Product page </a>
 * See <a href="http://sourceforge.net/p/lejos/wiki/Sensor%20Framework/"> The
 * leJOS sensors framework</a>
 * See <a href="http://www.ev3dev.org/docs/sensors/#uart-sensors"> The UART Sensors</a>
 * See {@link lejos.robotics.SampleProvider leJOS conventions for
 * SampleProviders}
 *
 * @author andy
 * @author Juan Antonio Breña Moral
 */
public class EV3TouchSensor extends BaseSensor implements Touch {

    private static final String LEGO_EV3_TOUCH = "lego-ev3-touch";

    public EV3TouchSensor(final Port portName) {
        super(portName, LEGO_ANALOG_SENSOR);
        setModes(new SensorMode[]{new GenericMode(this.PATH_DEVICE, 1, "Touch")});
    }

    /**
     * <b>Lego EV3 Touch sensors, Touch mode</b><br>
     * Detects when its front button is pressed
     *
     * <p>
     * <b>Size and content of the sample</b><br>
     * The sample contains one element, a value of 0 indicates that the button is not presse,
     * a value of 1 indicates the button is pressed.
     *
     * </p>
     *
     * @return A sampleProvider
     *     See {@link lejos.robotics.SampleProvider leJOS conventions for SampleProviders}
     */
    public SensorMode getTouchMode() {
        return getMode(0);
    }

    @Override
    public boolean isPressed() {
        float[] sample = new float[1];
        getTouchMode().fetchSample(sample, 0);
        return sample[0] != 0.0f;
    }

}
