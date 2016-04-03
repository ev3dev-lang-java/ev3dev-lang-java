package ev3dev.hardware;

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
public class Platform implements SupportedPlatform{

	public final String DEVICE_ROOT_PATH = "/sys/class/";

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
		
		final String BATTERY_PATH = DEVICE_ROOT_PATH + "power_supply/";
		final String EV3BRICK_BATTERY_PATH = BATTERY_PATH + "legoev3-battery";
		final String PISTORMS_BATTERY_PATH = BATTERY_PATH + "pistorm-battery";
		final String BRICKPI_BATTERY_PATH = BATTERY_PATH + "brickpi-battery";
		
		if(existPath(EV3BRICK_BATTERY_PATH)){
			return EV3BRICK;
		} else if(existPath(PISTORMS_BATTERY_PATH)){
			return PISTORMS;
		} else if(existPath(BRICKPI_BATTERY_PATH)){
			return BRICKPI;
		} else {
			throw new RuntimeException("Platform not supported");
		}
	}
	
}
