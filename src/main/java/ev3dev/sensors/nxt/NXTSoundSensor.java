package ev3dev.sensors.nxt;

import ev3dev.sensors.BaseSensor;
import ev3dev.utils.Sysfs;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3SensorConstants;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NXTSoundSensor extends BaseSensor {
    protected static final long SWITCH_DELAY = 10;
    protected static float MIN_RANGE = 0F;
    protected static float MAX_RANGE = 1000F;

    protected int currentType = -1;


    public NXTSoundSensor(Port sensorPort) {
        super(sensorPort, "nxt-analog", "lego-nxt-sound");
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public SampleProvider getDBMode() {
        this.switchMode("Sound-DB", 400L);
        return getMode(1);
    }

    public SampleProvider getDBAMode() {
        this.switchMode("Sound-DBA", 400L);
        return getMode(0);
    }

    /**
     * Helper method. Take a voltage and return it as a normalized value in the
     * range 0.0-1.0
     *
     * @param val input value
     * @return normalized value
     */
    protected float normalize(float val) {
        return val / EV3SensorConstants.ADC_REF;
    }

    /**
     * Switch to the selected type (if not already in that type) and delay for the
     * specified period to allow the sensor to settle in the new state. <br>
     * NOTE: This method is intended for use with NXT sensor drivers that use a
     * sensor type to specify the operating mode.
     *
     * @param newType     The type to switch to.
     * @param switchDelay Time in mS to delay after the switch.
     */
    protected void switchType(int newType, long switchDelay) {
        if (currentType != newType) {
            currentType = newType;
            Delay.msDelay(switchDelay);
        }
    }

    private class DBMode implements SensorMode {

        @Override
        public int sampleSize() {
            return 1;
        }

        @Override
        public void fetchSample(float[] sample, int offset) {
            switchType(7, SWITCH_DELAY);
            float reading = Sysfs.readFloat(PATH_DEVICE + "/value" + 0);
            sample[offset] = round(1.0F - normalize(reading) / MAX_RANGE, 2);
        }

        @Override
        public String getName() {
            return "Sound-DB";
        }
    }

    private class DBAMode implements SensorMode {
        @Override
        public int sampleSize() {
            return 1;
        }

        @Override
        public void fetchSample(float[] sample, int offset) {
            switchType(8, SWITCH_DELAY);
            float reading = Sysfs.readFloat(PATH_DEVICE + "/value" + 0);
            sample[offset] = round(1.0F - normalize(reading) / MAX_RANGE, 2);
        }


        @Override
        public String getName() {
            return "Sound-DBA";
        }
    }
}
