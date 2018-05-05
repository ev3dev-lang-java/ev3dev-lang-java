package ev3dev.sensors;

import ev3dev.utils.Sysfs;
import lejos.hardware.sensor.SensorMode;

import java.io.File;

public class GenericMode implements SensorMode {

    private final String VALUE = "value";
    private final String VALUE0 = "value0";

    private final File pathDevice;
    private final int sampleSize;
    private final String name;
    private final int type;
    private float minRange;
    private float maxRange;
    private float correctionFactor;

    public GenericMode (
            final File pathDevice,
            final int sampleSize,
            final String name,
            final int type) {
        this.pathDevice = pathDevice;
        this.sampleSize = sampleSize;
        this.name = name;
        this.type = type;
    }

    public GenericMode(
            final File pathDevice,
            final int sampleSize,
            final String name,
            final int type,
            final float minRange,
            final float maxRange,
            final float correctionFactor) {
        this(pathDevice, sampleSize, name, type);
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.correctionFactor = correctionFactor;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int sampleSize() {
        return sampleSize;
    }

    @Override
    public void fetchSample(float[] sample, int offset) {

        if(type == 1) {
            sample[offset] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0);

        } else if (type == 2) {
            float rawValue = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0) / correctionFactor;

            if (rawValue < minRange) {
                sample[offset] = 0;
            } else if (rawValue >= maxRange) {
                sample[offset] = Float.POSITIVE_INFINITY;
            } else {
                sample[offset] = rawValue;
            }
        } else if (type == 3) {

            for(int x=0; x < sampleSize; x++) {
                sample[offset++] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE + x);
            }
        }

    }
}
