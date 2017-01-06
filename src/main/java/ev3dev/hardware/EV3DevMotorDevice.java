package ev3dev.hardware;

import lombok.extern.slf4j.Slf4j;

/**
 * Base class to interact with EV3Dev sysfs
 * 
 * @author Juan Antonio Breña Moral
 *
 */
public @Slf4j abstract class EV3DevMotorDevice extends EV3DevDevice {

    protected static final String TACHO_MOTOR = "tacho-motor";
    protected static final String DC_MOTOR = "dc-motor";

    protected static final String POSITION_SP = "position_sp";
    protected static final String POSITION = "position";
    protected static final String SPEED = "speed_sp";
    protected static final String DUTY_CYCLE = "duty_cycle_sp";

    protected static final String COMMAND = "command";
    protected static final String RUN_FOREVER = "run-forever";
    protected static final String RUN_DIRECT = "run-direct";
    protected static final String RUN_TO_REL_POS = "run-to-rel-pos";
    protected static final String RUN_TO_ABS_POS = "run-to-abs-pos";

    protected static final String STOP_COMMAND = "stop_action";
    protected static final String COAST = "coast";
    protected static final String BRAKE = "brake";
    protected static final String HOLD = "hold";
    protected static final String STOP = "stop";
    protected static final String RESET = "reset";

    protected static final String STATE = "state";
    protected static final String STATE_RUNNING = "running";
    protected static final String STATE_STALLED = "stalled";
    protected static final String POWER = "power";
    protected static final String POLARITY = "polarity";
    protected static final String POLARITY_NORMAL = "normal";
    protected static final String POLARITY_INVERSED = "inversed";

	/**
	 * Every device connected in a EV3 Brick with EV3Dev appears in /sys/class in a determinated category.
	 * It is necessary to indicate the type and ports.
	 * 
	 * @param type A valid type. Example: tacho-motors, lego-sensors, etc...
	 * @param portName The ports where is connected the sensors or the actuators.
	 * @throws DeviceException Exception
	 */
    public EV3DevMotorDevice(final String type, final String portName) throws DeviceException {

		//This method is oriented for EV3Brick, but for Pi Boards, it is necessary to detect in a previous action
        if(!this.getPlatform().equals(SupportedPlatform.EV3BRICK)) {
            this.connect(type, portName);
        }
    }
   
}
