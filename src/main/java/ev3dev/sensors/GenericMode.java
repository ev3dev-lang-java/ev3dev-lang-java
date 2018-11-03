package ev3dev.sensors;

import ev3dev.utils.Sysfs;
import lejos.hardware.sensor.SensorMode;

import java.io.File;

/**
 * Generic ev3dev sensor handler.
 */
public class GenericMode implements SensorMode {

    private final File pathDevice;
    private final int sampleSize;
    private final String modeName;

    private final float correctMin;
    private final float correctMax;
    private final float correctFactor;

    /**
     * Create new generic sensor handler.
     * @param pathDevice Reference to the object responsible for mode setting and value reading.
     * @param sampleSize Number of returned samples.
     * @param modeName Human-readable sensor mode name.
     */
    public GenericMode (
            final File pathDevice,
            final int sampleSize,
            final String modeName) {
        this(pathDevice, sampleSize, modeName,
                Float.MIN_VALUE, Float.MAX_VALUE, 1.0f);
    }

    /**
     *
     * @param pathDevice Reference to the object responsible for mode setting and value reading.
     * @param sampleSize Number of returned samples.
     * @param modeName Human-readable sensor mode name.
     * @param correctMin Minimum value measured by the sensor. If the reading is lower, zero is returned.
     * @param correctMax Maximum value measured by the sensor. If the reading is higher, positive infinity is returned.
     * @param correctFactor Scaling factor applied to the sensor reading.
     */
    public GenericMode(
            final File pathDevice,
            final int sampleSize,
            final String modeName,
            final float correctMin,
            final float correctMax,
            final float correctFactor) {
        this.pathDevice = pathDevice;
        this.sampleSize = sampleSize;
        this.modeName = modeName;
        this.correctMin = correctMin;
        this.correctMax = correctMax;
        this.correctFactor = correctFactor;
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

        // for all values
        for (int n = 0; n < sampleSize; n++) {
            // read
            float reading = Sysfs.readFloat(this.pathDevice + "/" + "value" + n);

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
