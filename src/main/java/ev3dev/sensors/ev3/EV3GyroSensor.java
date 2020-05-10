package ev3dev.sensors.ev3;

import ev3dev.sensors.BaseSensor;
import ev3dev.sensors.GenericMode;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

/**
 * <b>EV3 Gyro sensors</b><br>
 * The digital EV3 Gyro Sensor measures the sensors rotational motion and changes in its orientation.
 *
 * <p><b>Sensor configuration</b><br>
 * Use {@link #reset()} to recalibrate the sensors and to reset accumulated angle to zero.
 * Keep the sensors motionless during a reset.
 * The sensors should also be motionless during initialization.
 *
 * <p>See <a href="http://www.ev-3.net/en/archives/849"> Sensor Product page </a>
 * See <a href="http://sourceforge.net/p/lejos/wiki/Sensor%20Framework/"> The
 * leJOS sensors framework</a>
 * See {@link lejos.robotics.SampleProvider leJOS conventions for SampleProviders}
 *
 * @author Andy Shaw
 * @author Aswin Bouwmeester
 * @author Juan Antonio Breña Moral
 */
public class EV3GyroSensor extends BaseSensor {

    private static final String LEGO_EV3_GYRO = "lego-ev3-gyro";

    private static final String MODE_RATE = "GYRO-RATE";
    private static final String MODE_ANGLE = "GYRO-ANG";
    private static final String MODE_RATE_ANGLE = "GYRO-G&A";

    /**
     * Constructor
     *
     * @param portName portName
     */
    public EV3GyroSensor(final Port portName) {
        super(portName, LEGO_UART_SENSOR, LEGO_EV3_GYRO);

        setModes(new SensorMode[]{
            new GenericMode(this.PATH_DEVICE, 1, "Rate",
                -Float.MAX_VALUE, +Float.MAX_VALUE, 1.0f),
            new GenericMode(this.PATH_DEVICE, 1, "Angle",
                -Float.MAX_VALUE, +Float.MAX_VALUE, 1.0f),
            new GenericMode(this.PATH_DEVICE, 2, "Angle and Rate",
                -Float.MAX_VALUE, +Float.MAX_VALUE, 1.0f),
        });
    }

    /**
     * <b>EV3 Gyro sensor, Rate mode</b><br>
     * Measures angular velocity of the sensor.
     *
     * <p><b>Size and content of the sample</b><br>
     * The sample contains one elements representing the angular velocity (in Degrees / second) of the sensor.
     *
     * <p><b>Configuration</b><br>
     * The sensor can be recalibrated using the reset method of the sensor.
     *
     * @return A sampleProvider
     *     See {@link lejos.robotics.SampleProvider leJOS conventions for SampleProviders}
     */
    public SampleProvider getRateMode() {
        switchMode(MODE_RATE, SWITCH_DELAY);
        return getMode(0);
    }

    /**
     * <b>EV3 Gyro sensors, Angle mode</b><br>
     * Measures the orientation of the sensors in respect to its start orientation.
     *
     * <p><b>Size and content of the sample</b><br>
     * The sample contains one elements representing the orientation (in Degrees) of the sensors
     * in respect to its start position.
     *
     * <p><b>Configuration</b><br>
     * The start position can be set to the current position using the reset method of the sensors.
     *
     * @return A sampleProvider
     *     See {@link lejos.robotics.SampleProvider leJOS conventions for SampleProviders}
     */
    public SampleProvider getAngleMode() {
        switchMode(MODE_ANGLE, SWITCH_DELAY);
        return getMode(1);
    }

    /**
     * <b>EV3 Gyro sensor, Rate mode</b><br>
     * Measures both angle and angular velocity of the sensor.
     *
     * <p><b>Size and content of the sample</b><br>
     * The sample contains two elements. The first element contains angular velocity (in degrees / second).
     * The second element contain angle (in degrees).
     *
     * <p><b>Configuration</b><br>
     * The sensor can be recalibrated using the reset method of the sensor.
     *
     * @return A sampleProvider
     *     See {@link lejos.robotics.SampleProvider leJOS conventions for SampleProviders}
     */
    public SampleProvider getAngleAndRateMode() {
        switchMode(MODE_RATE_ANGLE, SWITCH_DELAY);
        return getMode(2);
    }

    /**
     * Hardware calibration of the Gyro sensors and reset off accumulated angle to zero. <br>
     * The sensors should be motionless during calibration.
     */
    public void reset() {
        // Reset mode (4) is not used here as it behaves erratically.
        // Instead the reset is done implicitly by going to mode 1.
        switchMode(MODE_RATE, SWITCH_DELAY);
        // And back to 3 to prevent another reset when fetching the next sample
        switchMode(MODE_RATE_ANGLE, SWITCH_DELAY);
    }

}
