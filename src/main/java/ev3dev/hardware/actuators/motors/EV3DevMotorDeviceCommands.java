package ev3dev.hardware.actuators.motors;

public interface EV3DevMotorDeviceCommands {

    String TACHO_MOTOR = "tacho-motor";
    String DC_MOTOR = "dc-motor";
    String PORT = "lego-port";

    String POSITION_SP = "position_sp";
    String POSITION = "position";
    String SPEED = "speed_sp";
    String DUTY_CYCLE = "duty_cycle_sp";

    String COMMAND = "command";
    String RUN_FOREVER = "run-forever";
    String RUN_DIRECT = "run-direct";
    String RUN_TO_REL_POS = "run-to-rel-pos";
    String RUN_TO_ABS_POS = "run-to-abs-pos";

    String STOP_COMMAND = "stop_action";
    String COAST = "coast";
    String BRAKE = "brake";
    String HOLD = "hold";
    String STOP = "stop";
    String RESET = "reset";

    String STATE = "state";
    String STATE_RUNNING = "running";
    String POWER = "power";
    String POLARITY = "polarity";
    String POLARITY_NORMAL = "normal";
    String POLARITY_INVERSED = "inversed";

}
