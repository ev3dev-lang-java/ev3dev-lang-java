package ev3dev.hardware;

import ev3dev.hardware.EV3DevAttributeSpec;
import ev3dev.utils.Sysfs;
import ev3dev.utils.io.NativeLibc;
import lejos.hardware.port.Port;
import lejos.utility.Delay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ev3dev.hardware.EV3DevAttributeSpec.*;

/**
 * Base class to interact with EV3Dev Sensors
 *
 * @author Juan Antonio Breña Moral
 */
public abstract class EV3DevSensorDevice extends EV3DevDevice implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(EV3DevSensorDevice.class);

    protected static final String LEGO_UART_SENSOR = "ev3-uart";
    protected static final String LEGO_ANALOG_SENSOR = "ev3-analog";
    protected static final String LEGO_I2C = "nxt-i2c";
    protected static final String SENSOR_MODES = "modes";
    protected static final String SENSOR_MODE = "mode";

    public static final int ATTR_VALUE0 = 0;
    // -- 7 values --
    public static final int ATTR_ADDRESS = 8;
    public static final int ATTR_BIN_DATA = 9;
    public static final int ATTR_BIN_DATA_FORMAT = 10;
    public static final int ATTR_COMMAND = 11;
    public static final int ATTR_COMMANDS = 12;
    public static final int ATTR_DIRECT = 13;
    public static final int ATTR_DECIMALS = 14;
    public static final int ATTR_DRIVER_NAME = 15;
    public static final int ATTR_FW_VERSION = 16;
    public static final int ATTR_MODE = 17;
    public static final int ATTR_MODES = 18;
    public static final int ATTR_NUM_VALUES = 19;
    public static final int ATTR_POLL_MS = 20;
    public static final int ATTR_UNITS = 21;
    public static final int ATTR_TEXT_VALUE = 22;

    protected static final EV3DevAttributeSpec[] ATTR_INFO = new EV3DevAttributeSpec[]{
        new EV3DevAttributeSpec("value0", ACCESS_RO),
        new EV3DevAttributeSpec("value1", ACCESS_RO),
        new EV3DevAttributeSpec("value2", ACCESS_RO),
        new EV3DevAttributeSpec("value3", ACCESS_RO),
        new EV3DevAttributeSpec("value4", ACCESS_RO),
        new EV3DevAttributeSpec("value5", ACCESS_RO),
        new EV3DevAttributeSpec("value6", ACCESS_RO),
        new EV3DevAttributeSpec("value7", ACCESS_RO),
        new EV3DevAttributeSpec("address", ACCESS_RO),
        new EV3DevAttributeSpec("bin_data", ACCESS_RO),
        new EV3DevAttributeSpec("bin_data_format", ACCESS_RO),
        new EV3DevAttributeSpec("command", ACCESS_WO),
        new EV3DevAttributeSpec("commands", ACCESS_RO),
        new EV3DevAttributeSpec("direct", ACCESS_RW),
        new EV3DevAttributeSpec("decimals", ACCESS_RO),
        new EV3DevAttributeSpec("driver_name", ACCESS_RO),
        new EV3DevAttributeSpec("fw_version", ACCESS_RO),
        new EV3DevAttributeSpec("mode", ACCESS_RW),
        new EV3DevAttributeSpec("modes", ACCESS_RO),
        new EV3DevAttributeSpec("num_values", ACCESS_RO),
        new EV3DevAttributeSpec("poll_ms", ACCESS_RW),
        new EV3DevAttributeSpec("units", ACCESS_RO),
        new EV3DevAttributeSpec("text_value", ACCESS_RO),
    };

    /**
     * Every device connected in a EV3 Brick with EV3Dev appears in /sys/class in a determinated category.
     * It is necessary to indicate the type and ports.
     *
     * @param portName The ports where is connected the sensors or the actuators.
     * @param mode     mode
     * @param device   device
     */
    protected EV3DevSensorDevice(final Port portName, final String mode, final String device) {
        super(ATTR_INFO, new NativeLibc());

        final EV3DevPlatforms ev3DevPlatforms = EV3DevPlatforms.getInstance();
        final String port = ev3DevPlatforms.getSensorPort(portName);

        //EV3 Brick detect in a automatic way the sensors
        if (CURRENT_PLATFORM.equals(EV3DevPlatform.EV3BRICK)) {

            this.detect(LEGO_SENSOR, port);
        } else {

            //With Pi Boards, it is necessary to detect in 2 paths the sensors
            this.detect(LEGO_PORT, port);
            log.info("detected lego port: {}", this.PATH_DEVICE);
            Sysfs.writeString(this.PATH_DEVICE + "/" + MODE, mode);
            Sysfs.writeString(this.PATH_DEVICE + "/" + DEVICE, device);
            Delay.msDelay(1000);
            this.detect(LEGO_SENSOR, port);
            log.info("detected lego sensor: {}", this.PATH_DEVICE);
        }

    }

    /**
     * Constructor used for some Analog Sensors like EV3 Touch Sensors
     *
     * @param portName Port
     * @param mode     Mode
     */
    protected EV3DevSensorDevice(final Port portName, final String mode) {
        super(ATTR_INFO, new NativeLibc());

        final EV3DevPlatforms ev3DevPlatforms = EV3DevPlatforms.getInstance();
        final String port = ev3DevPlatforms.getSensorPort(portName);

        //EV3 Brick detect in a automatic way the sensors
        if (CURRENT_PLATFORM.equals(EV3DevPlatform.EV3BRICK)) {
            this.detect(LEGO_SENSOR, port);
        } else {

            //With Pi Boards, it is necessary to detect in 2 paths the sensors
            this.detect(LEGO_PORT, port);
            log.debug("detected lego port: {}", this.PATH_DEVICE);
            Sysfs.writeString(this.PATH_DEVICE + "/" + MODE, mode);
            Delay.msDelay(1000);
            this.detect(LEGO_SENSOR, port);
            log.debug("detected lego sensor: {}", this.PATH_DEVICE);
        }

    }


    public int value(int num) {
        return readIntAttr(ATTR_VALUE0 + num);
    }
}
