package ev3dev.sensors.ev3;

import ev3dev.sensors.EV3DevSensorMode;
import ev3dev.utils.Sysfs;

import java.io.File;

public class GenericMode extends EV3DevSensorMode {

    private final File pathDevice;
    private final int sampleSize;
    private final String name;
    private final int type;
    private float minRange;
    private float maxRange;

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
            final float maxRange) {
        this(pathDevice, sampleSize, name, type);
        this.minRange = minRange;
        this.maxRange = maxRange;
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
            float rawValue = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0) / 10f;

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
