package ev3dev.sensors.mindsensors;

import ev3dev.sensors.BaseSensor;
import ev3dev.sensors.GenericMode;
import ev3dev.utils.Shell;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.SensorMode;

/**
 * <b>Mindsensors AbsoluteIMU</b><br>
 * Sensor interface for the Mindsensors AbsoluteIMU family of sensors. The
 * AbsoluteIMU sensors combine gyro, accelerometer and compass sensors in
 * various combinations in a single housing. This interface works with all
 * AbsoluteIMU models, but not all modes will work with any particular model.
 *
 * <table border=1>
 * <tr>
 * <th colspan=4>Supported modes</th>
 * </tr>
 * <tr>
 * <th>Mode name</th>
 * <th>Description</th>
 * <th>unit(s)</th>
 * <th>Getter</th>
 * </tr>
 * <tr>
 * <td>Magnetic</td>
 * <td>Measures the strength of the magnetic field over three axes</td>
 * <td></td>
 * <td> {getMagneticMode() }</td>
 * </tr>
 * <tr>
 * <td>Compass</td>
 * <td>Measures the orientation of the sensor</td>
 * <td>Degrees, corresponding to the compass rose</td>
 * <td> {getCompassMode() }</td>
 * </tr>
 * <tr>
 * <td>Angle</td>
 * <td>Measures the orientation of the sensor</td>
 * <td>Degrees, corresponding to the right hand coordinate system</td>
 * <td> {getAngleMode() }</td>
 * </tr>
 * <tr>
 * <td>Acceleration</td>
 * <td>The Acceleration mode measures the linear acceleration of the sensor over
 * three axes</td>
 * <td>Metres/second^2</td>
 * <td> {getAccelerationMode() }</td>
 * </tr>
 * <tr>
 * <td>Rate</td>
 * <td>The Rate mode measures the angular speed of the sensor over three axes</td>
 * <td>Degrees/second</td>
 * <td> {getRateMode() }</td>
 * </tr>
 * </table>
 *
 * <p><b>Sensor configuration</b><br>
 * The gyro sensor of the AbsoluteIMU uses a filter to remove noise from
 * the samples. The filter can be configured using the {setGyroFilter }
 * method. <br>
 * The compass sensor of the AbsoluteIMU can be calibrated to compensate
 * for magnetical disturbances on the robot (soft iron calibration)
 * using the {#startCalibration} and {stopCalibration} methods.
 *
 * <p>To calibrate Compass, mount it on your robot where it will be used and
 * issue startCalibration method and then rotate AbsoluteIMU slowly along all
 * three axes. (The Compass in AbsoluteIMU is a 3 axis compass, and hence
 * needs to be turned along all three axes, and if it's mounted on your robot,
 * the whole robot needs to rotate). Rotate one axis at a time, turn once in
 * clock-wise direction completing at-least 360 degrees, and then turn it in
 * anti-clock-wise direction, then go to next axis. Upon finishing turning
 * along all axes, issue stopCalibration method.
 *
 * <p>See <a href=
 *      "http://mindsensors.com/index.php?module=documents&JAS_DocumentManager_op=downloadFile&JAS_File_id=1369"
 *      >Mindsensors IMU user guide"> Sensor Product page </a>
 * See <a href="http://sourceforge.net/p/lejos/wiki/Sensor%20Framework/"> The
 *      leJOS sensor framework</a>
 * See {@link lejos.robotics.SampleProvider leJOS conventions for
 *      SampleProviders}
 *
 * @author Andy, Juan Antonio Breña Moral
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

    //I2C Register
    public static final int GYRO_FILTER = 0x5a;

    /**
     * Constructor
     *
     * @param portName portName
     */
    public AbsoluteIMU(final Port portName) {
        super(portName, LEGO_I2C, MINDSENSORS_ABSOLUTEIMU);

        setModes(new SensorMode[]{
            new GenericMode(this.PATH_DEVICE, 3, "Acceleration"),
            new GenericMode(this.PATH_DEVICE, 3, "Magnetic"),
            new GenericMode(this.PATH_DEVICE, 3, "Gyro"),
            new GenericMode(this.PATH_DEVICE, 1, "Compass"),
            new GenericMode(this.PATH_DEVICE, 3, "Tilt")});

        setRange(LOW);
    }

    /**
     * Send a single byte command represented by a letter
     *
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
        switchMode(MODE_COMPASS, SWITCH_DELAY);
        return getMode(3);
    }

    /**
     * Return a SensorMode object that will acceleration data for the X, Y and Z
     * axis. The data is returned in units of m/s/s.
     *
     * @return a SensorMode object
     */
    public SensorMode getAccelerationMode() {
        switchMode(MODE_ACCELEROMETER, SWITCH_DELAY);
        return getMode(0);
    }

    /**
     * Return a SensorMode object that will return Magnetic data for the X, Y and
     * Z axis The data is returned in Guass
     *
     * @return a SensorMode object
     */
    public SensorMode getMagneticMode() {
        switchMode(MODE_MAGNETIC, SWITCH_DELAY);
        return getMode(1);
    }

    /**
     * @return SensorMode for reading the mode's data.
     */
    public SensorMode getGyroMode() {
        switchMode(MODE_GYRO, SWITCH_DELAY);
        return getMode(2);
    }

    /**
     * @return SensorMode for reading the mode's data.
     */
    public SensorMode getTiltMode() {
        switchMode(MODE_TILT, SWITCH_DELAY);
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
     * @param range the selected range (LOW/MEDIUM/HIGH/VERY_HIGH)
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

    /**
     * Set the smoothing filter for the gyro. <br>
     * The Gyro readings are filtered with nth order finite impulse response
     * filter, (where n ranges from 0 to 7) value 0 will apply no filter,
     * resulting in faster reading, but noisier values.value 7 will apply stronger
     * filter resulting in slower read (about 10 milli-seconds slower) but less
     * noise.<br>
     * The default value for the filter is 4.
     *
     * @param value (range 0-7)
     */
    public void setGyroFilter(int value) {
        if ((value < 0) || (value > 7)) {
            throw new IllegalArgumentException("Bad argument");
        }
        final String i2c_command2 =
            "echo \"" + value + "\" | dd bs=1 of=" + this.PATH_DEVICE + "/direct seek=$(( 0x5a ))";
        final String[] cmd = {
            "/bin/sh",
            "-c",
            i2c_command2
        };
        Shell.execute(cmd);
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
     */
    public void stopCalibration() {
        sendCommand(END_CALIBRATION);
    }

}
