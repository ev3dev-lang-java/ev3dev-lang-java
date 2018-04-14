package ev3dev.hardware;

import ev3dev.utils.Sysfs;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class EV3DevPlatforms {

    private static final Logger LOGGER = LoggerFactory.getLogger(EV3DevPlatforms.class);

    private static final Properties ev3DevProperties = EV3DevPropertyLoader.getEV3DevProperties();
    private static final EV3DevPlatform CURRENT_PLATFORM = retrievePlatform();

    /**
     * This method returns the platform
     *
     * @return Platform used
     * @throws RuntimeException Exception
     */
    private static EV3DevPlatform retrievePlatform() {

        //TODO Duplicated code
        final String BATTERY = "power_supply";
        final String BATTERY_PATH = EV3DevFileSystem.getRootPath() + "/" + BATTERY;
        final String BATTERY_EV3 =  ev3DevProperties.getProperty("BATTERY_EV3");
        final String BATTERY_PISTORMS =  "pistorms-battery";
        final String BATTERY_BRICKPI =  "brickpi-battery";
        final String BATTERY_BRICKPI3 =  "brickpi3-battery";
        final String EV3BRICK_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_EV3;
        final String PISTORMS_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_PISTORMS;
        final String BRICKPI_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_BRICKPI;
        final String BRICKPI3_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_BRICKPI3;

        if(Sysfs.existPath(EV3BRICK_DISCOVERY_PATTERN_PATH)){
            if(LOGGER.isTraceEnabled())
                LOGGER.trace(EV3BRICK_DISCOVERY_PATTERN_PATH);
                LOGGER.trace("Detected platform: " + EV3DevPlatform.EV3BRICK);
            return EV3DevPlatform.EV3BRICK;
        } else if(Sysfs.existPath(PISTORMS_DISCOVERY_PATTERN_PATH)){
            if(LOGGER.isTraceEnabled())
                LOGGER.trace(PISTORMS_DISCOVERY_PATTERN_PATH);
                LOGGER.trace("Detected platform: " + EV3DevPlatform.PISTORMS);
            return EV3DevPlatform.PISTORMS;
        } else if(Sysfs.existPath(BRICKPI_DISCOVERY_PATTERN_PATH)){
            if(LOGGER.isTraceEnabled())
                LOGGER.trace(BRICKPI_DISCOVERY_PATTERN_PATH);
                LOGGER.trace("Detected platform: " + EV3DevPlatform.BRICKPI);
            return EV3DevPlatform.BRICKPI;
        } else if(Sysfs.existPath(BRICKPI3_DISCOVERY_PATTERN_PATH)){
            if(LOGGER.isTraceEnabled())
                LOGGER.trace(BRICKPI3_DISCOVERY_PATTERN_PATH);
                LOGGER.trace("Detected platform: " + EV3DevPlatform.BRICKPI3);
            return EV3DevPlatform.BRICKPI3;
        } else {
            final String OS_NAME = System.getProperty("os.name");
            final String OS_VERSION = System.getProperty("os.version");
            final String message = "Platform not supported: " + OS_NAME + " " + OS_VERSION;
            LOGGER.error(message);
            throw new RuntimeException(message);
        }
    }

    public static EV3DevPlatform getPlatform() {
        return CURRENT_PLATFORM;
    }

    public static String getMotorPort(final Port port) {

        if(getPlatform().equals(EV3DevPlatform.EV3BRICK)){

            if(port.equals(MotorPort.A)){
                return ev3DevProperties.getProperty("ev3.motor.port.a");
            }else if(port.equals(MotorPort.B)){
                return ev3DevProperties.getProperty("ev3.motor.port.b");
            }else if(port.equals(MotorPort.C)){
                return ev3DevProperties.getProperty("ev3.motor.port.c");
            }else if(port.equals(MotorPort.D)){
                return ev3DevProperties.getProperty("ev3.motor.port.d");
            }

        } else if(getPlatform().equals(EV3DevPlatform.BRICKPI)) {

            if (port.equals(MotorPort.A)) {
                return "ttyAMA0:MA";
            } else if (port.equals(MotorPort.B)) {
                return "ttyAMA0:MB";
            } else if (port.equals(MotorPort.C)) {
                return "ttyAMA0:MC";
            } else if (port.equals(MotorPort.D)) {
                return "ttyAMA0:MD";
            }

        } else if(getPlatform().equals(EV3DevPlatform.BRICKPI3)) {

            if (port.equals(MotorPort.A)) {
                return "spi0.1:MA";
            } else if (port.equals(MotorPort.B)) {
                return "spi0.1:MB";
            } else if (port.equals(MotorPort.C)) {
                return "spi0.1:MC";
            } else if (port.equals(MotorPort.D)) {
                return "spi0.1:MD";
            }

        } else {

            if (port.equals(MotorPort.A)) {
                return "pistorms:BBM1";
            } else if (port.equals(MotorPort.B)) {
                return "pistorms:BBM2";
            } else if (port.equals(MotorPort.C)) {
                return "pistorms:BAM2";
            } else if (port.equals(MotorPort.D)) {
                return "pistorms:BAM1";
            }

        }

        throw new RuntimeException("Bad port used");
    }

    public static String getSensorPort(final Port port) {

        if(getPlatform().equals(EV3DevPlatform.EV3BRICK)){

            if(port.equals(SensorPort.S1)){
                return ev3DevProperties.getProperty("ev3.sensor.port.1");
            }else if(port.equals(SensorPort.S2)){
                return ev3DevProperties.getProperty("ev3.sensor.port.2");
            }else if(port.equals(SensorPort.S3)){
                return ev3DevProperties.getProperty("ev3.sensor.port.3");
            }else if(port.equals(SensorPort.S4)){
                return ev3DevProperties.getProperty("ev3.sensor.port.4");
            }

        } else if(getPlatform().equals(EV3DevPlatform.BRICKPI)) {

            if (port.equals(SensorPort.S1)) {
                return "ttyAMA0:S1";
            } else if (port.equals(SensorPort.S2)) {
                return "ttyAMA0:S2";
            } else if (port.equals(SensorPort.S3)) {
                return "ttyAMA0:S3";
            } else if (port.equals(SensorPort.S4)) {
                return "ttyAMA0:S4";
            }

        } else if(getPlatform().equals(EV3DevPlatform.BRICKPI3)) {

            if (port.equals(SensorPort.S1)) {
                return "spi0.1:S1";
            } else if (port.equals(SensorPort.S2)) {
                return "spi0.1:S2";
            } else if (port.equals(SensorPort.S3)) {
                return "spi0.1:S3";
            } else if (port.equals(SensorPort.S4)) {
                return "spi0.1:S4";
            }

        } else {

            if (port.equals(SensorPort.S1)) {
                return "pistorms:BBS2";
            } else if (port.equals(SensorPort.S2)) {
                return "pistorms:BBS1";
            } else if (port.equals(SensorPort.S3)) {
                return "pistorms:BAS1";
            } else if (port.equals(SensorPort.S4)) {
                return "pistorms:BAS2";
            }

        }

        throw new RuntimeException("Bad port used");
    }

}
