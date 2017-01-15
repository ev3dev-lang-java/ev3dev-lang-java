package ev3dev.hardware;

import ev3dev.utils.Sysfs;
import lombok.extern.slf4j.Slf4j;

public @Slf4j abstract class EV3DevPlatform implements SupportedPlatform {

    protected static final String DEVICE_ROOT_PATH = "/sys/class/";

    /**
     * This method returns the platform
     *
     * @return Platform used
     * @throws RuntimeException Exception
     */
    @Override
    public String getPlatform() {

        final String BATTERY =  "power_supply";
        final String BATTERY_PATH = DEVICE_ROOT_PATH + BATTERY;
        final String BATTERY_EV3 =  "legoev3-battery";
        final String BATTERY_PISTORMS =  "pistorms-battery";
        final String BATTERY_BRICKPI =  "brickpi-battery";
        final String EV3BRICK_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_EV3;
        final String PISTORMS_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_PISTORMS;
        final String BRICKPI_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_BRICKPI;

        if(Sysfs.existPath(EV3BRICK_DISCOVERY_PATTERN_PATH)){
            log.trace("Detected platform: " + SupportedPlatform.EV3BRICK);
            return EV3BRICK;
        } else if(Sysfs.existPath(PISTORMS_DISCOVERY_PATTERN_PATH)){
            log.trace("Detected platform: " + SupportedPlatform.PISTORMS);
            return PISTORMS;
        } else if(Sysfs.existPath(BRICKPI_DISCOVERY_PATTERN_PATH)){
            log.trace("Detected platform: " + SupportedPlatform.BRICKPI);
            return BRICKPI;
        } else {
            throw new RuntimeException("Platform not supported");
        }
    }

}
