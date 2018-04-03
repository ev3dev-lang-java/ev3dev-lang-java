package ev3dev.hardware;

/**
 * Base class to interact with EV3Dev sysfs
 * 
 * @author Juan Antonio Breña Moral
 *
 */
public abstract class EV3DevMotorDevice extends EV3DevDevice {

    protected static final String TACHO_MOTOR = "tacho-motor";
    protected static final String DC_MOTOR = "dc-motor";
    protected static final String AUTO_MODE = "auto";

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

}
