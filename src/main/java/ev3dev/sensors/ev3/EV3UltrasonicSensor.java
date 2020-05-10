package ev3dev.sensors.ev3;


import ev3dev.sensors.BaseSensor;
import ev3dev.sensors.GenericMode;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

import java.util.Objects;

/**
 * <b>Lego EV3 Ultrasonic sensors</b><br>
 * The EV3 Ultrasonic sensors measures distance to an object in front of the
 * sensors. It can also be used to detect other (active) Ultrasonic sensors in
 * the vicinity.
 *
 *
 *
 *
 * <p><b>Sensor configuration</b><br>
 * The sensors can be switched off and on using the {@link #enable} and
 * {@link #disable} methods. Disabling the sensors also shuts down the lights.
 *
 * <p>See <a href="http://www.ev-3.net/en/archives/844"> Sensor Product page </a>
 * See <a href="http://sourceforge.net/p/lejos/wiki/Sensor%20Framework/"> The
 * leJOS sensors framework</a>
 * See {@link lejos.robotics.SampleProvider leJOS conventions for SampleProviders}
 *
 * @author Aswin Bouwmeester
 * @author Juan Antonio Breña Moral
 */
public class EV3UltrasonicSensor extends BaseSensor {

    private static final String LEGO_EV3_US = "lego-ev3-us";

    public static float MIN_RANGE = 5f; //cm
    public static float MAX_RANGE = 255f; //cm

    private static final String MODE_DISTANCE = "US-DIST-CM";
    private static final String MODE_LISTEN = "US-LISTEN";
    private static final String MODE_SINGLE_MEASURE = "US-SI-CM";


    /**
     * Create the Ultrasonic sensors class.
     *
     * @param portName port
     */
    public EV3UltrasonicSensor(final Port portName) {
        super(portName, LEGO_UART_SENSOR, LEGO_EV3_US);

        setModes(new SensorMode[]{
            new GenericMode(this.PATH_DEVICE, 1, "Distance", MIN_RANGE, MAX_RANGE, 0.1f),
            new GenericMode(this.PATH_DEVICE, 1, "Listen")
        });
    }

    /**
     * <b>Lego EV3 Ultrasonic sensors, Listen mode</b><br>
     * Listens for the presence of other ultrasonic sensors.
     *
     * <p><b>Size and content of the sample</b><br>
     * The sample contains one elements indicating the presence of another ultrasonic sensors.
     * A value of 1 indicates that the sensors detects another ultrasonic sensors.
     *
     * @return A sampleProvider
     */
    public SampleProvider getListenMode() {
        switchMode(MODE_LISTEN, SWITCH_DELAY);
        return getMode(1);
    }

    /**
     * <b>Lego EV3 Ultrasonic sensors, Distance mode</b><br>
     * Measures distance to an object in front of the sensors
     *
     * <p><b>Size and content of the sample</b><br>
     * The sample contains one elements representing the distance (in metres)
     * to an object in front of the sensors unit.
     *
     * @return A sampleProvider
     */
    public SampleProvider getDistanceMode() {
        switchMode(MODE_DISTANCE, SWITCH_DELAY);
        return getMode(0);
    }

    /**
     * Enable the sensor. This puts the indicater LED on.
     *
     * <p>Note: this function switches the sensor mode.
     * This means that reads from SensorModes different from the one
     * returned by getDistanceMode() will be invalid.
     * See {@link GenericMode#fetchSample(float[], int)}</p>
     */
    public void enable() {
        switchMode(MODE_DISTANCE, SWITCH_DELAY);
    }

    /**
     * Disable the sensor. This puts the indicater LED off.
     *
     * <p>Note: this function switches the sensor mode.
     * This means that reads from SensorModes will be invalid
     * until enable() or get*Mode() is called.
     * See {@link GenericMode#fetchSample(float[], int)}</p>
     */
    public void disable() {
        switchMode(MODE_SINGLE_MEASURE, SWITCH_DELAY);
    }

    /**
     * Indicate that the sensors is enabled.
     *
     * @return True, when the sensors is enabled. <br>
     *     False, when the sensors is disabled.
     */
    public boolean isEnabled() {
        return !Objects.equals(this.getSystemMode(), MODE_SINGLE_MEASURE);
    }

}
