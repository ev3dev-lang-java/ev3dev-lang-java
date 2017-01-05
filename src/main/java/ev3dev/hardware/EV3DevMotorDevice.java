package ev3dev.hardware;

import lombok.extern.slf4j.Slf4j;

/**
 * Base class to interact with EV3Dev sysfs
 * 
 * @author Juan Antonio Breña Moral
 *
 */
public @Slf4j class EV3DevMotorDevice extends EV3DevDevice {

	/**
	 * Every device connected in a EV3 Brick with EV3Dev appears in /sys/class in a determinated category.
	 * It is necessary to indicate the type and ports.
	 * 
	 * @param type A valid type. Example: tacho-motors, lego-sensors, etc...
	 * @param portName The ports where is connected the sensors or the actuators.
	 * @throws DeviceException
	 */
    public EV3DevMotorDevice(final String type, final String portName) throws DeviceException {

		//This method is oriented for EV3Brick, but for Pi Boards, it is necessary to detect in a previous action
        if(!this.getPlatform().equals(SupportedPlatform.EV3BRICK)) {
            this.connect(type, portName);
        }
    }
   
}
