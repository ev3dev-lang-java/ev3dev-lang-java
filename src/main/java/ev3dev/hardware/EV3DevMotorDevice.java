package ev3dev.hardware;

import ev3dev.utils.io.NativeLibc;

import static ev3dev.hardware.EV3DevAttributeSpec.*;
import static ev3dev.hardware.EV3DevAttributeSpec.ACCESS_RO;

/**
 * Base class to interact with EV3Dev sysfs
 *
 * @author Juan Antonio Breña Moral
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


    public static final int ATTR_ADDRESS = 0;
    public static final int ATTR_COMMAND = 1;
    public static final int ATTR_COMMANDS = 2;
    public static final int ATTR_COUNT_PER_ROT = 3;
    public static final int ATTR_COUNT_PER_M = 4;
    public static final int ATTR_FULL_TRAVEL_COUNT = 5;
    public static final int ATTR_DRIVER_NAME = 6;
    public static final int ATTR_DUTY_CYCLE = 7;
    public static final int ATTR_DUTY_CYCLE_SP = 8;
    public static final int ATTR_POLARITY = 9;
    public static final int ATTR_POSITION = 10;
    public static final int ATTR_HOLD_PID_KP = 11;
    public static final int ATTR_HOLD_PID_KI = 12;
    public static final int ATTR_HOLD_PID_KD = 13;
    public static final int ATTR_SPEED_PID_KP = 14;
    public static final int ATTR_SPEED_PID_KI = 15;
    public static final int ATTR_SPEED_PID_KD = 16;
    public static final int ATTR_MAX_SPEED = 17;
    public static final int ATTR_POSITION_SP = 18;
    public static final int ATTR_SPEED = 19;
    public static final int ATTR_SPEED_SP = 20;
    public static final int ATTR_RAMP_UP_SP = 21;
    public static final int ATTR_RAMP_DOWN_SP = 22;
    public static final int ATTR_STATE = 23;
    public static final int ATTR_STOP_ACTION = 24;
    public static final int ATTR_STOP_ACTIONS = 25;
    public static final int ATTR_TIME_SP = 26;

    protected static final EV3DevAttributeSpec[] ATTR_INFO = new EV3DevAttributeSpec[]{
        new EV3DevAttributeSpec("address", ACCESS_RO),
        new EV3DevAttributeSpec("command", ACCESS_WO),
        new EV3DevAttributeSpec("commands", ACCESS_RO),
        new EV3DevAttributeSpec("count_per_rot", ACCESS_RO),
        new EV3DevAttributeSpec("count_per_m", ACCESS_RO),
        new EV3DevAttributeSpec("full_travel_count", ACCESS_RO),
        new EV3DevAttributeSpec("driver_name", ACCESS_RO),
        new EV3DevAttributeSpec("duty_cycle", ACCESS_RO),
        new EV3DevAttributeSpec("duty_cycle_sp", ACCESS_RW),
        new EV3DevAttributeSpec("polarity", ACCESS_RW),
        new EV3DevAttributeSpec("position", ACCESS_RW),
        new EV3DevAttributeSpec("hold_pid_kp", ACCESS_RW),
        new EV3DevAttributeSpec("hold_pid_ki", ACCESS_RW),
        new EV3DevAttributeSpec("hold_pid_kd", ACCESS_RW),
        new EV3DevAttributeSpec("speed_pid_kp", ACCESS_RW),
        new EV3DevAttributeSpec("speed_pid_ki", ACCESS_RW),
        new EV3DevAttributeSpec("speed_pid_kd", ACCESS_RW),
        new EV3DevAttributeSpec("max_speed", ACCESS_RO),
        new EV3DevAttributeSpec("position_sp", ACCESS_RW),
        new EV3DevAttributeSpec("speed", ACCESS_RO),
        new EV3DevAttributeSpec("speed_sp", ACCESS_RW),
        new EV3DevAttributeSpec("ramp_up_sp", ACCESS_RW),
        new EV3DevAttributeSpec("ramp_down_sp", ACCESS_RW),
        new EV3DevAttributeSpec("state", ACCESS_RO),
        new EV3DevAttributeSpec("stop_action", ACCESS_RW),
        new EV3DevAttributeSpec("stop_actions", ACCESS_RO),
        new EV3DevAttributeSpec("time_sp", ACCESS_RW)
    };

    protected EV3DevMotorDevice() {
        super(ATTR_INFO, new NativeLibc());
    }
}
