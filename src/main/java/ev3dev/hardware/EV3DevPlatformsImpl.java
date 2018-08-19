package ev3dev.hardware;

import ev3dev.utils.Sysfs;
import ev3dev.hardware.EV3DevScreenInfo;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.EnumSet;
import java.util.Objects;

import java.nio.file.*;

import java.util.Properties;

class EV3DevPlatformsImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(EV3DevPlatformsImpl.class);

    private final EV3DevPlatform platform;
    private final String         propPrefix;
    private final Properties     props;

    private boolean batteryTest(final String batteryDir, final String propPrefix) {
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

    public EV3DevPlatformsImpl() {
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

    public EV3DevPlatformsImpl(EV3DevPlatform platform) {
        // load properties from jar
        final EV3DevPropertyLoader ev3DevPropertyLoader = new EV3DevPropertyLoader();
        props = ev3DevPropertyLoader.getEV3DevProperties();

        // get platform
        this.platform = platform;

        // handle success
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("Detected platform: " + platform);
        }
        propPrefix = platform.getPropertyNamespace();
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

    public EV3DevScreenInfo getFramebufferInfo() {
        // fetch from properties
        String path = Objects.requireNonNull(getProperty("framebuffer.path"));
        String mode = Objects.requireNonNull(getProperty("framebuffer.mode"));
        String wStr = Objects.requireNonNull(getProperty("framebuffer.width"));
        String hStr = Objects.requireNonNull(getProperty("framebuffer.height"));
        String sStr = getProperty("framebuffer.stride");

        // parse and pack
        int w = Integer.parseInt(wStr);
        int h = Integer.parseInt(hStr);
        EV3DevScreenInfo pack = null;
        if (mode.equals("bitplane")) {
            int s = Integer.parseInt(Objects.requireNonNull(sStr));
            pack = new EV3DevScreenInfo(path, EV3DevScreenInfo.Mode.BITPLANE, w, h, s);
        } else if (mode.equals("xrgb")) {
            pack = new EV3DevScreenInfo(path, EV3DevScreenInfo.Mode.XRGB, w, h, w * 4);
        } else {
            throw new RuntimeException("Invalid framebuffer mode");
        }
        return pack;
    }

    public String getProperty(String base) {
        String global = props.getProperty(base);
        String local  = props.getProperty(propPrefix + "." + base);
        return local == null ? global : local;
    }
}
