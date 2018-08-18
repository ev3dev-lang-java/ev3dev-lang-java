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

    private final EV3DevPlatform CURRENT_PLATFORM;

    public EV3DevPlatforms() {

        final EV3DevPropertyLoader ev3DevPropertyLoader = new EV3DevPropertyLoader();
        final Properties ev3DevProperties = ev3DevPropertyLoader.getEV3DevProperties();

        final String BATTERY = ev3DevProperties.getProperty("battery");
        final String BATTERY_PATH = EV3DevFileSystem.getRootPath() + "/" + BATTERY;
        final String BATTERY_EV3 =  ev3DevProperties.getProperty("ev3.battery");
        final String BATTERY_PISTORMS =  ev3DevProperties.getProperty("pistorms.battery");
        final String BATTERY_BRICKPI = ev3DevProperties.getProperty("brickpi.battery");;
        final String BATTERY_BRICKPI3 =  ev3DevProperties.getProperty("brickpi3.battery");
        final String EV3BRICK_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_EV3;
        final String PISTORMS_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_PISTORMS;
        final String BRICKPI_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_BRICKPI;
        final String BRICKPI3_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_BRICKPI3;

        if(Sysfs.existPath(EV3BRICK_DISCOVERY_PATTERN_PATH)){
            if(LOGGER.isTraceEnabled())
                LOGGER.trace(EV3BRICK_DISCOVERY_PATTERN_PATH);
                LOGGER.trace("Detected platform: " + EV3DevPlatform.EV3BRICK);
            CURRENT_PLATFORM = EV3DevPlatform.EV3BRICK;
        } else if(Sysfs.existPath(PISTORMS_DISCOVERY_PATTERN_PATH)){
            if(LOGGER.isTraceEnabled())
                LOGGER.trace(PISTORMS_DISCOVERY_PATTERN_PATH);
                LOGGER.trace("Detected platform: " + EV3DevPlatform.PISTORMS);
            CURRENT_PLATFORM =  EV3DevPlatform.PISTORMS;
        } else if(Sysfs.existPath(BRICKPI_DISCOVERY_PATTERN_PATH)){
            if(LOGGER.isTraceEnabled())
                LOGGER.trace(BRICKPI_DISCOVERY_PATTERN_PATH);
                LOGGER.trace("Detected platform: " + EV3DevPlatform.BRICKPI);
            CURRENT_PLATFORM =  EV3DevPlatform.BRICKPI;
        } else if(Sysfs.existPath(BRICKPI3_DISCOVERY_PATTERN_PATH)){
            if(LOGGER.isTraceEnabled())
                LOGGER.trace(BRICKPI3_DISCOVERY_PATTERN_PATH);
                LOGGER.trace("Detected platform: " + EV3DevPlatform.BRICKPI3);
            CURRENT_PLATFORM =  EV3DevPlatform.BRICKPI3;
        } else {
            final String OS_NAME = System.getProperty("os.name");
            final String OS_VERSION = System.getProperty("os.version");
            final String message = "Platform not supported: " + OS_NAME + " " + OS_VERSION;
            LOGGER.error(message);
            throw new RuntimeException(message);
        }
    }

    public EV3DevPlatform getPlatform() {
        return CURRENT_PLATFORM;
    }

    public String getMotorPort(final Port port) {

        final EV3DevPropertyLoader ev3DevPropertyLoader = new EV3DevPropertyLoader();
        final Properties ev3DevProperties = ev3DevPropertyLoader.getEV3DevProperties();

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
                return ev3DevProperties.getProperty("brickpi.motor.port.a");
            } else if (port.equals(MotorPort.B)) {
                return ev3DevProperties.getProperty("brickpi.motor.port.b");
            } else if (port.equals(MotorPort.C)) {
                return ev3DevProperties.getProperty("brickpi.motor.port.c");
            } else if (port.equals(MotorPort.D)) {
                return ev3DevProperties.getProperty("brickpi.motor.port.d");
            }

        } else if(getPlatform().equals(EV3DevPlatform.BRICKPI3)) {

            if (port.equals(MotorPort.A)) {
                return ev3DevProperties.getProperty("brickpi3.motor.port.a");
            } else if (port.equals(MotorPort.B)) {
                return ev3DevProperties.getProperty("brickpi3.motor.port.b");
            } else if (port.equals(MotorPort.C)) {
                return ev3DevProperties.getProperty("brickpi3.motor.port.c");
            } else if (port.equals(MotorPort.D)) {
                return ev3DevProperties.getProperty("brickpi3.motor.port.d");
            }

        } else {

            if (port.equals(MotorPort.A)) {
                return ev3DevProperties.getProperty("pistorms.motor.port.a");
            } else if (port.equals(MotorPort.B)) {
                return ev3DevProperties.getProperty("pistorms.motor.port.b");
            } else if (port.equals(MotorPort.C)) {
                return ev3DevProperties.getProperty("pistorms.motor.port.c");
            } else if (port.equals(MotorPort.D)) {
                return ev3DevProperties.getProperty("pistorms.motor.port.d");
            }

        }

        throw new RuntimeException("Bad port used");
    }

    public String getSensorPort(final Port port) {

        final EV3DevPropertyLoader ev3DevPropertyLoader = new EV3DevPropertyLoader();
        final Properties ev3DevProperties = ev3DevPropertyLoader.getEV3DevProperties();

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

            if(port.equals(SensorPort.S1)){
                return ev3DevProperties.getProperty("brickpi.sensor.port.1");
            }else if(port.equals(SensorPort.S2)){
                return ev3DevProperties.getProperty("brickpi.sensor.port.2");
            }else if(port.equals(SensorPort.S3)){
                return ev3DevProperties.getProperty("brickpi.sensor.port.3");
            }else if(port.equals(SensorPort.S4)){
                return ev3DevProperties.getProperty("brickpi.sensor.port.4");
            }

        } else if(getPlatform().equals(EV3DevPlatform.BRICKPI3)) {

            if(port.equals(SensorPort.S1)){
                return ev3DevProperties.getProperty("brickpi3.sensor.port.1");
            }else if(port.equals(SensorPort.S2)){
                return ev3DevProperties.getProperty("brickpi3.sensor.port.2");
            }else if(port.equals(SensorPort.S3)){
                return ev3DevProperties.getProperty("brickpi3.sensor.port.3");
            }else if(port.equals(SensorPort.S4)){
                return ev3DevProperties.getProperty("brickpi3.sensor.port.4");
            }

        } else {

            if(port.equals(SensorPort.S1)){
                return ev3DevProperties.getProperty("pistorms.sensor.port.1");
            }else if(port.equals(SensorPort.S2)){
                return ev3DevProperties.getProperty("pistorms.sensor.port.2");
            }else if(port.equals(SensorPort.S3)){
                return ev3DevProperties.getProperty("pistorms.sensor.port.3");
            }else if(port.equals(SensorPort.S4)){
                return ev3DevProperties.getProperty("pistorms.sensor.port.4");
            }

        }

        throw new RuntimeException("Bad port used");
    }

}
