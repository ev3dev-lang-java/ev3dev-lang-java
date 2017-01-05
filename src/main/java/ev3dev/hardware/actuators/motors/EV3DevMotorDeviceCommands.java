package ev3dev.hardware.actuators.motors;

public interface EV3DevMotorDeviceCommands {

    String SYSTEM_CLASS_NAME = "tacho-motor";
    String SYSTEM_PORT_CLASS_NAME = "lego-port";
    String POSITION_SP = "position_sp";
    String POSITION = "position";
    String SPEED = "speed_sp";
    String DUTY_CYCLE = "duty_cycle_sp";
    String COMMAND = "command";
    String RUN_FOREVER = "run-forever";
    String RUN_DIRECT = "run-direct";
    String STOP_COMMAND = "stop_action";
    String COAST = "coast";
    String BRAKE = "brake";
    String HOLD = "hold";
    String STOP = "stop";
    String RESET = "reset";
    String RUN_TO_REL_POS = "run-to-rel-pos";
    String RUN_TO_ABS_POS = "run-to-abs-pos";
    String STATE = "state";
    String POWER = "power";

}
