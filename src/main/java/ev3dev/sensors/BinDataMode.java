package ev3dev.sensors;

import ev3dev.hardware.EV3DevSensorDevice;
import lejos.hardware.sensor.SensorMode;

import java.nio.*;

import static ev3dev.hardware.EV3DevSensorDevice.ATTR_BIN_DATA;

/**
 * Generic ev3dev sensor handler.
 *
 * <p>Note: data returned by {@link BinDataMode#fetchSample(float[], int)}
 * are valid only when the sensor itself is in the correct mode.
 * Otherwise, wrong data will be returned.</p>
 */
public class BinDataMode implements SensorMode {
    public static final int WIDTH_INT8 = 1;
    public static final int WIDTH_INT16 = 2;
    public static final int WIDTH_INT32 = 4;
    public static final int WIDTH_FLOAT32 = 5;

    private final EV3DevSensorDevice device;
    private final int sampleSize;
    private final String modeName;
    private final int intWidth;
    private final boolean littleEndian;
    private final boolean signed;

    private final float correctMin;
    private final float correctMax;
    private final float correctFactor;

    /**
     * Create new generic sensor handler.
     *
     * @param device     Reference to the object responsible for mode setting and value reading.
     * @param sampleSize Number of returned samples.
     * @param modeName   Human-readable sensor mode name.
     */
    public BinDataMode(
        final EV3DevSensorDevice device,
        final int sampleSize,
        final String modeName,
        final int intWidth,
        final boolean littleEndian,
        final boolean signed) {
        this(device, sampleSize, modeName,
            intWidth, littleEndian, signed,
            Float.MIN_VALUE, Float.MAX_VALUE, 1.0f);
    }

    /**
     * Create new generic sensor handler.
     *
     * @param device        Reference to the object responsible for mode setting and value reading.
     * @param sampleSize    Number of returned samples.
     * @param modeName      Human-readable sensor mode name.
     * @param correctMin    Minimum value measured by the sensor. If the reading is lower, zero is returned.
     * @param correctMax    Maximum value measured by the sensor. If the reading is higher, positive infinity is returned.
     * @param correctFactor Scaling factor applied to the sensor reading.
     */
    public BinDataMode(
        final EV3DevSensorDevice device,
        final int sampleSize,
        final String modeName,
        final int intWidth,
        final boolean littleEndian,
        final boolean signed,
        final float correctMin,
        final float correctMax,
        final float correctFactor) {
        this.device = device;
        this.sampleSize = sampleSize;
        this.modeName = modeName;
        this.intWidth = intWidth;
        this.littleEndian = littleEndian;
        this.signed = signed;
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


    /**
     * Fetches a sample from the sensor.
     *
     * <p>Note: this function works properly only when
     * the sensor is already in the appropriate mode. Otherwise,
     * returned data will be invalid.</p>
     *
     * @param sample The array to store the sample in.
     * @param offset The elements of the sample are stored in the array starting at the offset position.
     */
    @Override
    public void fetchSample(float[] sample, int offset) {
        float[] unscaled = new float[this.sampleSize];

        ByteBuffer data = this.device.readRawAttr(ATTR_BIN_DATA);
        if (littleEndian)
            data.order(ByteOrder.LITTLE_ENDIAN);
        else
            data.order(ByteOrder.BIG_ENDIAN);

        if (intWidth == WIDTH_INT8 && signed) {
            for (int i = 0; i < sampleSize; i++) {
                unscaled[i] = data.get(i);
            }
        } else if (intWidth == WIDTH_INT8) {
            for (int i = 0; i < sampleSize; i++) {
                unscaled[i] = Byte.toUnsignedInt(data.get(i));
            }
        } else if (intWidth == WIDTH_INT16 && signed) {
            ShortBuffer typed = data.asShortBuffer();
            for (int i = 0; i < sampleSize; i++) {
                unscaled[i] = typed.get(i);
            }
        } else if (intWidth == WIDTH_INT16) {
            ShortBuffer typed = data.asShortBuffer();
            for (int i = 0; i < sampleSize; i++) {
                unscaled[i] = Short.toUnsignedInt(typed.get(i));
            }

        } else if (intWidth == WIDTH_INT32) { /* no unsigned option */
            IntBuffer typed = data.asIntBuffer();
            for (int i = 0; i < sampleSize; i++) {
                unscaled[i] = typed.get(i);
            }

        } else if (intWidth == WIDTH_FLOAT32) { /* unsigned does not make sense */
            FloatBuffer typed = data.asFloatBuffer();
            for (int i = 0; i < sampleSize; i++) {
                unscaled[i] = typed.get(i);
            }
        }

        // for all values
        for (int n = 0; n < sampleSize; n++) {
            // read (kernel only has integers)
            float reading = unscaled[n];

            // apply correction
            if (correctFactor != 1.0f) {
                reading *= correctFactor;
            }
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
