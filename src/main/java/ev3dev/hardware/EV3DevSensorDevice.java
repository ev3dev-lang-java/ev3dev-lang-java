package ev3dev.hardware;

import lejos.hardware.port.Port;
import lejos.utility.Delay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class to interact with EV3Dev Sensors
 *
 * @author Juan Antonio Breña Moral
 */
public abstract class EV3DevSensorDevice extends EV3DevDevice {

    private static final Logger log = LoggerFactory.getLogger(EV3DevSensorDevice.class);

    protected static final String LEGO_UART_SENSOR = "ev3-uart";
    protected static final String LEGO_ANALOG_SENSOR = "ev3-analog";
    protected static final String LEGO_I2C = "nxt-i2c";
    protected static final String SENSOR_MODES = "modes";
    protected static final String SENSOR_MODE = "mode";

    /**
     * Every device connected in a EV3 Brick with EV3Dev appears in /sys/class in a determinated category.
     * It is necessary to indicate the type and ports.
     *
     * @param portName The ports where is connected the sensors or the actuators.
     * @param mode     mode
     * @param device   device
     */
    protected EV3DevSensorDevice(final Port portName, final String mode, final String device) {

        final EV3DevPlatforms ev3DevPlatforms = EV3DevPlatforms.getInstance();
        final String port = ev3DevPlatforms.getSensorPort(portName);

        //EV3 Brick detect in a automatic way the sensors
        if (CURRENT_PLATFORM.equals(EV3DevPlatform.EV3BRICK)) {

            this.detect(LEGO_SENSOR, port);
        } else {

            //With Pi Boards, it is necessary to detect in 2 paths the sensors
            this.detect(LEGO_PORT, port);
            log.info("detected lego port: {}", this.PATH_DEVICE);
            this.setStringAttribute(MODE, mode);
            this.setStringAttribute(DEVICE, device);
            Delay.msDelay(1000);
            this.detect(LEGO_SENSOR, port);
            log.info("detected lego sensor: {}", this.PATH_DEVICE);
        }

    }

    /**
     * Constructor used for some Analog Sensors like EV3 Touch Sensors
     *
     * @param portName Port
     * @param mode Mode
     */
    protected EV3DevSensorDevice(final Port portName, final String mode) {

        final EV3DevPlatforms ev3DevPlatforms = EV3DevPlatforms.getInstance();
        final String port = ev3DevPlatforms.getSensorPort(portName);

        //EV3 Brick detect in a automatic way the sensors
        if (CURRENT_PLATFORM.equals(EV3DevPlatform.EV3BRICK)) {
            this.detect(LEGO_SENSOR, port);
        } else {

            //With Pi Boards, it is necessary to detect in 2 paths the sensors
            this.detect(LEGO_PORT, port);
            log.debug("detected lego port: {}", this.PATH_DEVICE);
            this.setStringAttribute(MODE, mode);
            Delay.msDelay(1000);
            this.detect(LEGO_SENSOR, port);
            log.debug("detected lego sensor: {}", this.PATH_DEVICE);
        }

    }

}
