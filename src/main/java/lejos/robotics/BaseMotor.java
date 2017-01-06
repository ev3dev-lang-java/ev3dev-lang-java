package lejos.robotics;

/**
 * Base motors interface. Contains basic movement commands.
 *
 */
public interface BaseMotor {

    /**
    * Causes motors to rotate forward until <code>stop()</code> or <code>flt()</code> is called.
    */
    void forward();

    /**
    * Causes motors to rotate backwards until <code>stop()</code> or <code>flt()</code> is called.
    */
    void backward();

    /**
    * Causes motors to stop immediately. It will resist any further motion. Cancels any rotate() orders in progress.
    */
    void stop();

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

    /**
    * Return <code>true</code> if the motors is moving.
    *
    * @return <code>true</code> if the motors is currently in motion, <code>false</code> if stopped.
    */
    boolean isMoving();
}
