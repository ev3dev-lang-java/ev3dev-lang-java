package ev3dev.sensors.nxt;

import ev3dev.sensors.BaseSensor;
import ev3dev.sensors.GenericMode;
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

    public NXTTemperatureSensor(final Port portName) {
        super(portName, LEGO_I2C, LEGO_NXT_TEMP);

        setModes(new SensorMode[]{
            new GenericMode(this.PATH_DEVICE, 1, "C", CELSIUS_MIN_RANGE, CELSIUS_MAX_RANGE, 0.1f),
            new GenericMode(this.PATH_DEVICE, 1, "F", FAHRENHEIT_MIN_RANGE, FAHRENHEIT_MAX_RANGE, 0.1f)
        });
    }

    public SampleProvider getCelsiusMode() {
        switchMode(MODE_CELSIUS, SWITCH_DELAY);
        return getMode(1);
    }

    public SampleProvider getFahrenheitMode() {
        switchMode(MODE_FAHRENHEIT, SWITCH_DELAY);
        return getMode(1);
    }
}
