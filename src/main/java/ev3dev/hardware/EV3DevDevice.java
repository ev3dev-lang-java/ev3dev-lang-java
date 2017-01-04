package ev3dev.hardware;

import ev3dev.utils.Sysfs;
import lombok.extern.slf4j.Slf4j;

/**
 * This class been designed to discover if the library is used in:
 *
 * - EV3 Brick
 * - Raspberry Pi 1 + PiStorms
 * - Raspberry Pi 1 + BrickPi
 * 
 * @author Juan Antonio Breña Moral
 *
 */

@Slf4j
public class EV3DevDevice extends Device implements SupportedPlatform {

	/**
	 * This method returns the platform
	 * 
	 * @throws PlatformNotSupportedException
	 * @return
	 */
    @Override
	public String getPlatform() throws PlatformNotSupportedException{
		
		final String DEVICE_ROOT_PATH = "/sys/class/";

		final String BATTERY_PATH = DEVICE_ROOT_PATH + "power_supply/";
		final String EV3BRICK_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "legoev3-battery";
		final String PISTORMS_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "pistorm-battery";
		final String BRICKPI_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "brickpi-battery";

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
			throw new PlatformNotSupportedException("Platform not supported");
		}
	}
	
}
