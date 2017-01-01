package ev3dev.hardware;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

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
public @Slf4j class Platform implements SupportedPlatform{

	/**
	 * This method is used to detect folders in /sys/class/
	 * 
	 * @param path
	 * @return
	 */
	private boolean existPath(final String path){
		final File f = new File(path);
		if (f.exists() && f.isDirectory()) {
		   return true;
		}
		return false;
	}
	
	/**
	 * This method returns the platform
	 * 
	 * @throws RuntimeException
	 * @return
	 */
	public String getPlatform(){
		
		final String DEVICE_ROOT_PATH = "/sys/class/";

		final String BATTERY_PATH = DEVICE_ROOT_PATH + "power_supply/";
		final String EV3BRICK_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "legoev3-battery";
		final String PISTORMS_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "pistorm-battery";
		final String BRICKPI_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "brickpi-battery";

		if(existPath(EV3BRICK_DISCOVERY_PATTERN_PATH)){
			log.trace("Detected platform: " + EV3BRICK);
			return EV3BRICK;
		} else if(existPath(PISTORMS_DISCOVERY_PATTERN_PATH)){
			log.trace("Detected platform: " + PISTORMS);
			return PISTORMS;
		} else if(existPath(BRICKPI_DISCOVERY_PATTERN_PATH)){
			log.trace("Detected platform: " + BRICKPI);
			return BRICKPI;
		} else {
			throw new PlatformNotSupportedException("Platform not supported");
		}
	}
	
}
