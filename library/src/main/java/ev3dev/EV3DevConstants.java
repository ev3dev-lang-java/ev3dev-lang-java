package ev3dev;

public class EV3DevConstants {

	//~autogen generic-get-set classes.motor>currentClass

    // Command: write-only
    // Sends a command to the motor controller. See `commands` for a list of
    // possible values.


    // Commands: read-only
    // Returns a list of commands that are supported by the motor
    // controller. Possible values are `run-forever`, `run-to-abs-pos`, `run-to-rel-pos`,
    // `run-timed`, `run-direct`, `stop` and `reset`. Not all commands may be supported.
    // 
    // - `run-forever` will cause the motor to run until another command is sent.
    // - `run-to-abs-pos` will run to an absolute position specified by `position_sp`
    //   and then stop using the command specified in `stop_command`.
    // - `run-to-rel-pos` will run to a position relative to the current `position` value.
    //   The new position will be current `position` + `position_sp`. When the new
    //   position is reached, the motor will stop using the command specified by `stop_command`.
    // - `run-timed` will run the motor for the amount of time specified in `time_sp`
    //   and then stop the motor using the command specified by `stop_command`.
    // - `run-direct` will run the motor at the duty cycle specified by `duty_cycle_sp`.
    //   Unlike other run commands, changing `duty_cycle_sp` while running *will*
    //   take effect immediately.
    // - `stop` will stop any of the run commands before they are complete using the
    //   command specified by `stop_command`.
    // - `reset` will reset all of the motor parameter attributes to their default value.
    //   This will also have the effect of stopping the motor.
    public static String commands = "commands"; 


    // Count Per Rot: read-only
    // Returns the number of tacho counts in one rotation of the motor. Tacho counts
    // are used by the position and speed attributes, so you can use this value
    // to convert rotations or degrees to tacho counts. In the case of linear
    // actuators, the units here will be counts per centimeter.
    public static String count_per_rot = "count_per_rot"; 


    // Driver Name: read-only
    // Returns the name of the driver that provides this tacho motor device.
    public static String driver_name = "driver_name"; 


    // Duty Cycle: read-only
    // Returns the current duty cycle of the motor. Units are percent. Values
    // are -100 to 100.
    public static String duty_cycle = "duty_cycle"; 


    // Duty Cycle SP: read/write
    // Writing sets the duty cycle setpoint. Reading returns the current value.
    // Units are in percent. Valid values are -100 to 100. A negative value causes
    // the motor to rotate in reverse. This value is only used when `speed_regulation`
    // is off.
    public static String duty_cycle_sp = "duty_cycle_sp"; 


    // Encoder Polarity: read/write
    // Sets the polarity of the rotary encoder. This is an advanced feature to all
    // use of motors that send inversed encoder signals to the EV3. This should
    // be set correctly by the driver of a device. It You only need to change this
    // value if you are using a unsupported device. Valid values are `normal` and
    // `inversed`.
    public static String encoder_polarity = "encoder_polarity"; 


    // Polarity: read/write
    // Sets the polarity of the motor. With `normal` polarity, a positive duty
    // cycle will cause the motor to rotate clockwise. With `inversed` polarity,
    // a positive duty cycle will cause the motor to rotate counter-clockwise.
    // Valid values are `normal` and `inversed`.
    public static String polarity = "polarity"; 


    // Port Name: read-only
    // Returns the name of the port that the motor is connected to.
    public static String port_name = "port_name"; 


    // Position: read/write
    // Returns the current position of the motor in pulses of the rotary
    // encoder. When the motor rotates clockwise, the position will increase.
    // Likewise, rotating counter-clockwise causes the position to decrease.
    // Writing will set the position to that value.
    public static String position = "position"; 


    // Position P: read/write
    // The proportional constant for the position PID.
    public static String position_p = "hold_pid/Kp"; 


    // Position I: read/write
    // The integral constant for the position PID.
    public static String position_i = "hold_pid/Ki"; 


    // Position D: read/write
    // The derivative constant for the position PID.
    public static String position_d = "hold_pid/Kd"; 


    // Position SP: read/write
    // Writing specifies the target position for the `run-to-abs-pos` and `run-to-rel-pos`
    // commands. Reading returns the current value. Units are in tacho counts. You
    // can use the value returned by `counts_per_rot` to convert tacho counts to/from
    // rotations or degrees.
    public static String position_sp = "position_sp"; 


    // Speed: read-only
    // Returns the current motor speed in tacho counts per second. Not, this is
    // not necessarily degrees (although it is for LEGO motors). Use the `count_per_rot`
    // attribute to convert this value to RPM or deg/sec.
    public static String speed = "speed"; 


    // Speed SP: read/write
    // Writing sets the target speed in tacho counts per second used when `speed_regulation`
    // is on. Reading returns the current value.  Use the `count_per_rot` attribute
    // to convert RPM or deg/sec to tacho counts per second.
    public static String speed_sp = "speed_sp"; 


    // Ramp Up SP: read/write
    // Writing sets the ramp up setpoint. Reading returns the current value. Units
    // are in milliseconds. When set to a value > 0, the motor will ramp the power
    // sent to the motor from 0 to 100% duty cycle over the span of this setpoint
    // when starting the motor. If the maximum duty cycle is limited by `duty_cycle_sp`
    // or speed regulation, the actual ramp time duration will be less than the setpoint.
    public static String ramp_up_sp = "ramp_up_sp"; 


    // Ramp Down SP: read/write
    // Writing sets the ramp down setpoint. Reading returns the current value. Units
    // are in milliseconds. When set to a value > 0, the motor will ramp the power
    // sent to the motor from 100% duty cycle down to 0 over the span of this setpoint
    // when stopping the motor. If the starting duty cycle is less than 100%, the
    // ramp time duration will be less than the full span of the setpoint.
    public static String ramp_down_sp = "ramp_down_sp"; 


    // Speed Regulation Enabled: read/write
    // Turns speed regulation on or off. If speed regulation is on, the motor
    // controller will vary the power supplied to the motor to try to maintain the
    // speed specified in `speed_sp`. If speed regulation is off, the controller
    // will use the power specified in `duty_cycle_sp`. Valid values are `on` and
    // `off`.
    public static String speed_regulation_enabled = "speed_regulation"; 


    // Speed Regulation P: read/write
    // The proportional constant for the speed regulation PID.
    public static String speed_regulation_p = "speed_pid/Kp"; 


    // Speed Regulation I: read/write
    // The integral constant for the speed regulation PID.
    public static String speed_regulation_i = "speed_pid/Ki"; 


    // Speed Regulation D: read/write
    // The derivative constant for the speed regulation PID.
    public static String speed_regulation_d = "speed_pid/Kd"; 


    // State: read-only
    // Reading returns a list of state flags. Possible flags are
    // `running`, `ramping` `holding` and `stalled`.
    public static String state = "state"; 


    // Stop Command: read/write
    // Reading returns the current stop command. Writing sets the stop command.
    // The value determines the motors behavior when `command` is set to `stop`.
    // Also, it determines the motors behavior when a run command completes. See
    // `stop_commands` for a list of possible values.
    public static String stop_command = "stop_command"; 


    // Stop Commands: read-only
    // Returns a list of stop modes supported by the motor controller.
    // Possible values are `coast`, `brake` and `hold`. `coast` means that power will
    // be removed from the motor and it will freely coast to a stop. `brake` means
    // that power will be removed from the motor and a passive electrical load will
    // be placed on the motor. This is usually done by shorting the motor terminals
    // together. This load will absorb the energy from the rotation of the motors and
    // cause the motor to stop more quickly than coasting. `hold` does not remove
    // power from the motor. Instead it actively try to hold the motor at the current
    // position. If an external force tries to turn the motor, the motor will 'push
    // back' to maintain its position.
    public static String stop_commands = "stop_commands"; 


    // Time SP: read/write
    // Writing specifies the amount of time the motor will run when using the
    // `run-timed` command. Reading returns the current value. Units are in
    // milliseconds.
    public static String time_sp = "time_sp"; 


//~autogen
	
}
