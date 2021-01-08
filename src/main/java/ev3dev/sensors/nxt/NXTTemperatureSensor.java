package ev3dev.sensors.nxt;

import ev3dev.sensors.BaseSensor;
import ev3dev.sensors.GenericMode;
import ev3dev.utils.Sysfs;
import java.io.File;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class NXTTemperatureSensor extends BaseSensor {

    private static final String LEGO_NXT_TEMP = "lego-nxt-temp";

    public static float CELSIUS_MIN_RANGE = -550f; //celsius degrees
    public static float CELSIUS_MAX_RANGE = 1280f; //celsius degrees

    public static float FAHRENHEIT_MIN_RANGE = -670f; //fahrenheit degrees
    public static float FAHRENHEIT_MAX_RANGE = 2624f; //fahrenheit degrees

    private static final String MODE_CELSIUS = "NXT-TEMP-C";
    private static final String MODE_FAHRENHEIT = "NXT-TEMP-F";

    /**
     * Constructor
     *
     * @param portName Port name
     */
    public NXTTemperatureSensor(final Port portName) {
        super(portName, LEGO_I2C, LEGO_NXT_TEMP);

        setModes(new SensorMode[]{
            new InternalMode(this.PATH_DEVICE, 1, "C", CELSIUS_MIN_RANGE, CELSIUS_MAX_RANGE, 1.0f),
            new InternalMode(this.PATH_DEVICE, 1, "F", FAHRENHEIT_MIN_RANGE, FAHRENHEIT_MAX_RANGE, 1.0f)
        });
    }

    public SampleProvider getCelsiusMode() {
        switchMode(MODE_CELSIUS, SWITCH_DELAY);
        return getMode(0);
    }

    public SampleProvider getFahrenheitMode() {
        switchMode(MODE_FAHRENHEIT, SWITCH_DELAY);
        return getMode(1);
    }

    //TODO Review GenericMode to not duplicate (reading = Float.NEGATIVE_INFINITY;)
    private class InternalMode extends GenericMode {

        private final float correctMin;
        private final float correctMax;
        private final float correctFactor;

        public InternalMode(File pathDevice, int sampleSize, String modeName,
                            float correctMin, float correctMax, float correctFactor) {
            super(pathDevice, sampleSize, modeName, correctMin, correctMax, correctFactor);

            this.correctMin = correctMin;
            this.correctMax = correctMax;
            this.correctFactor = correctFactor;
        }

        @Override
        public void fetchSample(float[] sample, int offset) {

            float reading = Sysfs.readFloat(this.pathDevice + "/" + "value0");

            //Processing
            reading = reading / 10f;
            reading *= correctFactor;
            if (reading < correctMin) {
                reading = Float.NEGATIVE_INFINITY;
            } else if (reading >= correctMax) {
                reading = Float.POSITIVE_INFINITY;
            }

            // store
            sample[0] = reading;
        }
    }
}
