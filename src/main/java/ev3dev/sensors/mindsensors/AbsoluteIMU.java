package ev3dev.sensors.mindsensors;

import ev3dev.sensors.BaseSensor;
import ev3dev.sensors.EV3DevSensorMode;
import ev3dev.sensors.SensorMode;
import ev3dev.utils.Sysfs;
import lejos.hardware.port.Port;

import java.io.File;

/**
 * Created by jabrena on 12/8/17.
 */
public class AbsoluteIMU extends BaseSensor {

    private static final String MINDSENSORS_ABSOLUTEIMU = "ms-absolute-imu 0x11";

    //MODES

    public static final String MODE_TILT = "TILT";
    public static final String MODE_ACCELEROMETER = "ACCEL";
    public static final String MODE_COMPASS = "COMPASS";
    public static final String MODE_MAGNETIC = "MAG";
    public static final String MODE_GYRO = "GYRO";

    //COMMANDS

    public static final String START_CALIBRATION = "BEGIN-COMP-CAL";
    public static final String END_CALIBRATION = "END-COMP-CAL";
    public static final int LOW = 1;
    public static final int MEDIUM = 2;
    public static final int HIGH = 3;
    public static final int VERY_HIGH = 4;
    public static final String SET_ACCELERATION_2G = "ACCEL-2G";
    public static final String SET_ACCELERATION_4G = "ACCEL-4G";
    public static final String SET_ACCELERATION_8G = "ACCEL-8G";
    public static final String SET_ACCELERATION_16G = "ACCEL-16G";

    private void initModes() {
        setModes(
                new SensorMode[]{
                        new AbsoluteIMU.AccelerometerMode(this.PATH_DEVICE),
                        new AbsoluteIMU.MagnetometerMode(this.PATH_DEVICE),
                        new AbsoluteIMU.GyroMode(this.PATH_DEVICE),
                        new AbsoluteIMU.CompassMode(this.PATH_DEVICE),
                        new AbsoluteIMU.TiltMode(this.PATH_DEVICE),
                });
        setRange(LOW);
    }

    public AbsoluteIMU(final Port portName) {
        super(portName, LEGO_I2C, MINDSENSORS_ABSOLUTEIMU);
        this.initModes();
    }

    /**
     * Send a single byte command represented by a letter
     * @param cmd the letter that identifies the command
     */
    public void sendCommand(final String cmd) {
        this.setStringAttribute("command", cmd);
    }

    /**
     * Return a SensorMode object that will provide tilt compensated compass data
     * . The sample contains one element containing the bearing of the sensor
     * relative to north expressed in degrees. East being at 90 degrees.
     *
     * @return a SensorMode object
     */
    public SensorMode getCompassMode() {
        return getMode(3);
    }

    /**
     * Return a SensorMode object that will acceleration data for the X, Y and Z
     * axis. The data is returned in units of m/s/s.
     *
     * @return a SensorMode object
     */
    public SensorMode getAccelerationMode() {
        return getMode(0);
    }

    /**
     * Return a SensorMode object that will return Magnetic data for the X, Y and
     * Z axis The data is returned in Guass
     *
     * @return a SensorMode object
     */
    public SensorMode getMagneticMode() {
        return getMode(1);
    }

    public SensorMode getGyroMode() {
        return getMode(2);
    }

    public SensorMode getTiltMode() {
        return getMode(4);
    }


    /**
     * Set the sensitivity used by the sensor. This setting impacts the maximum
     * range of the returned value and the resolution of the reading.<br>
     * LOW Acceleration 2G Gyro 250 degrees/second<br>
     * MEDIUM Acceleration 4G Gyro 500 degrees/second<br>
     * HIGH Acceleration 8G Gyro 2000 degrees/second<br>
     * VERY_HIGH Acceleration 16G Gyro 2000 degrees/second<br>
     * The default setting is LOW.
     *
     * @param range
     *          the selected range (LOW/MEDIUM/HIGH/VERY_HIGH)
     */
    public void setRange(int range) {
        String cmd = "";
        switch (range) {
            case LOW:
                cmd = SET_ACCELERATION_2G;
                break;
            case MEDIUM:
                cmd = SET_ACCELERATION_4G;
                break;
            case HIGH:
                cmd = SET_ACCELERATION_8G;
                break;
            case VERY_HIGH:
                cmd = SET_ACCELERATION_16G;
                break;
            default:
                throw new IllegalArgumentException("Range setting invalid");
        }
        sendCommand(cmd);
    }

    private class AccelerometerMode extends EV3DevSensorMode {

        private File pathDevice = null;

        public AccelerometerMode(final File pathDevice) {
            this.pathDevice = pathDevice;
        }

        @Override
        public int sampleSize() {
            return 3;
        }

        @Override
        public void fetchSample(float[] sample, int offset) {
            switchMode(MODE_ACCELEROMETER, SWITCH_DELAY);
            sample[0] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0);
            sample[1] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE1);
            sample[2] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE2);
        }

        @Override
        public String getName() {
            return "Acceleration";
        }

    }

    private class MagnetometerMode extends EV3DevSensorMode {

        private File pathDevice = null;

        public MagnetometerMode(final File pathDevice) {
            this.pathDevice = pathDevice;
        }

        @Override
        public int sampleSize() {
            return 3;
        }

        @Override
        public void fetchSample(float[] sample, int offset) {
            switchMode(MODE_MAGNETIC, SWITCH_DELAY);
            sample[0] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0);
            sample[1] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE1);
            sample[2] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE2);
        }

        @Override
        public String getName() {
            return "Magnetic";
        }

    }

    private class GyroMode extends EV3DevSensorMode {

        private File pathDevice = null;

        public GyroMode(final File pathDevice) {
            this.pathDevice = pathDevice;
        }

        @Override
        public int sampleSize() {
            return 3;
        }

        @Override
        public void fetchSample(float[] sample, int offset) {
            switchMode(MODE_GYRO, SWITCH_DELAY);
            sample[0] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0);
            sample[1] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE1);
            sample[2] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE2);
        }

        @Override
        public String getName() {
            return "Gyro";
        }

    }

    private class CompassMode extends EV3DevSensorMode {

        private File pathDevice = null;

        public CompassMode(final File pathDevice) {
            this.pathDevice = pathDevice;
        }

        @Override
        public int sampleSize() {
            return 1;
        }

        @Override
        public void fetchSample(float[] sample, int offset) {
            switchMode(MODE_COMPASS, SWITCH_DELAY);
            sample[0] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0);
        }

        @Override
        public String getName() {
            return "Compass";
        }

    }

    private class TiltMode extends EV3DevSensorMode {

        private File pathDevice = null;

        public TiltMode(final File pathDevice) {
            this.pathDevice = pathDevice;
        }

        @Override
        public int sampleSize() {
            return 3;
        }

        @Override
        public void fetchSample(float[] sample, int offset) {
            switchMode(MODE_TILT, SWITCH_DELAY);
            sample[0] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0);
            sample[1] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE1);
            sample[2] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE2);
        }

        @Override
        public String getName() {
            return "Tilt";
        }

    }


    /**
     * To calibrate Compass, mount it on your robot where it will be used and
     * issue startCalibration method and then rotate AbsoluteIMU slowly along all
     * three axes. (The Compass in AbsoluteIMU is a 3 axis compass, and hence
     * needs to be turned along all three axes, and if it's mounted on your robot,
     * the whole robot needs to rotate). Rotate one axis at a time, turn once in
     * clock-wise direction completing at-least 360 degrees, and then turn it in
     * anti-clock-wise direction, then go to next axis. Upon finishing turning
     * along all axes, issue stopCalibration method.
     */
    public void startCalibration() {
        sendCommand(START_CALIBRATION);
    }

    /**
     * Ends calibration sequence.
     *
     */
    public void stopCalibration() {
        sendCommand(END_CALIBRATION);
    }

}
