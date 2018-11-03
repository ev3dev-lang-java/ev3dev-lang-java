package ev3dev.hardware;

import ev3dev.utils.Sysfs;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class EV3DevPlatforms {

    private static EV3DevPlatforms instance;

    private final EV3DevPlatform platform;
    private final String         propPrefix;
    private final Properties props;

    /**
     * Return a Instance of EV3DevPlatforms.
     *
     * @return A EV3DevPlatforms instance
     */
    public static EV3DevPlatforms getInstance() {

        if (Objects.isNull(instance)) {
            instance = new EV3DevPlatforms();
        }
        return instance;
    }

    private EV3DevPlatforms() {

        LOGGER.info("Providing a EV3DevPlatforms instance");

        // load properties from jar
        final EV3DevPropertyLoader ev3DevPropertyLoader = new EV3DevPropertyLoader();
        props = ev3DevPropertyLoader.getEV3DevProperties();

        // get battery directory prefix
        final String batteryDirectory = props.getProperty("battery");

        // iterate over all platforms and check if they're the correct one
        platform = EnumSet.allOf(EV3DevPlatform.class)
                .stream()
                .filter(e -> batteryTest(batteryDirectory, e.getPropertyNamespace()))
                .findFirst()
                .orElseThrow(this::throwNoPlatform);

        // handle success
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("Detected platform: " + platform);
        }
        propPrefix = platform.getPropertyNamespace();

    }

    private boolean batteryTest(final String batteryDir, final String propPrefix) {

        LOGGER.debug("Detecting platform with the battery approach");
        Path path = Paths.get(EV3DevFileSystem.getRootPath(),
                batteryDir,
                props.getProperty(propPrefix + ".battery"));
        return Sysfs.existPath(path.toString());
    }

    private RuntimeException throwNoPlatform() {
        final String OS_NAME = System.getProperty("os.name");
        final String OS_VERSION = System.getProperty("os.version");
        final String message = "Platform not supported: " + OS_NAME + " " + OS_VERSION;
        LOGGER.error(message);
        return new RuntimeException(message);
    }

    public EV3DevPlatform getPlatform() {
        return platform;
    }

    public String getMotorPort(final Port port) {
        String portLetter = "";
        if (port.equals(MotorPort.A)) {
            portLetter = "a";
        } else if (port.equals(MotorPort.B)) {
            portLetter = "b";
        } else if (port.equals(MotorPort.C)) {
            portLetter = "c";
        } else if (port.equals(MotorPort.D)) {
            portLetter = "d";
        } else {
            throw new RuntimeException("Bad port used");
        }
        return props.getProperty(propPrefix + ".motor.port." + portLetter);
    }

    public String getSensorPort(final Port port) {
        String portNumber;
        if (port.equals(SensorPort.S1)) {
            portNumber = "1";
        } else if (port.equals(SensorPort.S2)) {
            portNumber = "2";
        } else if (port.equals(SensorPort.S3)) {
            portNumber = "3";
        } else if (port.equals(SensorPort.S4)) {
            portNumber = "4";
        } else {
            throw new RuntimeException("Bad port used");
        }

        return props.getProperty(propPrefix + ".sensor.port." + portNumber);
    }

}
