package ev3dev.sensors;

import lejos.hardware.sensor.SensorMode;

/**
 * Generic ev3dev sensor handler.
 */
public class GenericMode implements SensorMode {

    private final BaseSensor sensor;
    private final String sensorMode;
    private final long sensorSwitchDelay;

    private final int sampleSize;
    private final String modeName;

    private float correctMin;
    private float correctMax;
    private float correctFactor;

    /**
     * Create new generic sensor handler.
     * @param sensor Reference to the object responsible for mode setting and value reading.
     * @param sensorMode Identifier of the sensor mode this handler represents.
     * @param sampleSize Number of returned samples.
     * @param modeName Human-readable sensor mode name.
     */
    public GenericMode (
            final BaseSensor sensor,
            final String sensorMode,
            final int sampleSize,
            final String modeName) {
        this(sensor, sensorMode,
                sampleSize, modeName,
                Float.MIN_VALUE, Float.MAX_VALUE, 1.0f,
                BaseSensor.SWITCH_DELAY);
    }

    /**
     *
     * @param sensor Reference to the object responsible for mode setting and value reading.
     * @param sensorMode Identifier of the sensor mode this handler represents.
     * @param sampleSize Number of returned samples.
     * @param modeName Human-readable sensor mode name.
     * @param correctMin Minimum value measured by the sensor. If the reading is lower, zero is returned.
     * @param correctMax Maximum value measured by the sensor. If the reading is higher, positive infinity is returned.
     * @param correctFactor Scaling factor applied to the sensor reading.
     */
    public GenericMode(
            final BaseSensor sensor,
            final String sensorMode,
            final int sampleSize,
            final String modeName,
            final float correctMin,
            final float correctMax,
            final float correctFactor) {
        this(sensor, sensorMode,
                sampleSize, modeName,
                correctMin, correctMax, correctFactor,
                BaseSensor.SWITCH_DELAY);
    }

    /**
     *
     * @param sensor Reference to the object responsible for mode setting and value reading.
     * @param sensorMode Identifier of the sensor mode this handler represents.
     * @param sampleSize Number of returned samples.
     * @param modeName Human-readable sensor mode name.
     * @param correctMin Minimum value measured by the sensor. If the reading is lower, zero is returned.
     * @param correctMax Maximum value measured by the sensor. If the reading is higher, positive infinity is returned.
     * @param correctFactor Scaling factor applied to the sensor reading.
     * @param sensorSwitchDelay Delay when the sensor after mode switch still returns data from old sensor mode.
     */
    private GenericMode(
            final BaseSensor sensor,
            final String sensorMode,
            final int sampleSize,
            final String modeName,
            final float correctMin,
            final float correctMax,
            final float correctFactor,
            final long sensorSwitchDelay) {
        this.sensor = sensor;
        this.sensorMode = sensorMode;
        this.sampleSize = sampleSize;
        this.modeName = modeName;
        this.correctMin = correctMin;
        this.correctMax = correctMax;
        this.correctFactor = correctFactor;
        this.sensorSwitchDelay = sensorSwitchDelay;
    }

    @Override
    public String getName() {
        return modeName;
    }

    @Override
    public int sampleSize() {
        return sampleSize;
    }

    @Override
    public void fetchSample(float[] sample, int offset) {
        // for analog sensors
        if (sensorMode != null) {
            sensor.switchMode(sensorMode, sensorSwitchDelay);
        }

        // for all values
        for (int n = 0; n < sampleSize; n++) {
            // read
            float reading = sensor.readValue(n);

            // apply correction
            reading *= correctFactor;
            if (reading < correctMin) {
                reading = 0;
            } else if (reading >= correctMax) {
                reading = Float.POSITIVE_INFINITY;
            }

            // store
            sample[offset + n] = reading;
        }
    }
}
