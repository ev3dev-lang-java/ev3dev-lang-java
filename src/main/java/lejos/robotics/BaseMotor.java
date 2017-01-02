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
    public void coast();

    public void brake();

	public void hold();

    /**
    * Return <code>true</code> if the motors is moving.
    *
    * @return <code>true</code> if the motors is currently in motion, <code>false</code> if stopped.
    */
    // TODO: Possibly part of Encoder interface? Depends if encoder used to determine this.
    public boolean isMoving();

    public void reverseDirection();
}
