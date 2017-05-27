package ev3dev.actuators.ev3.motors;


public interface EV3DevBaseMotor {

    /**
     * Motor loses all power, causing the rotor to float freely to a stop.
     * This is not the same as stopping, which locks the rotor.
     */
    void coast();

    /**
     * Removes power from the motor and creates a passive electrical load.
     * This is usually done by shorting the motor terminals together.
     * This load will absorb the energy from the rotation of the motors and
     * cause the motor to stop more quickly than coasting.
     */
    void brake();

    /**
     * Causes the motor to actively try to hold the current position.
     * If an external force tries to turn the motor, the motor will “push back”
     * to maintain its position.
     */
    void hold();

}
