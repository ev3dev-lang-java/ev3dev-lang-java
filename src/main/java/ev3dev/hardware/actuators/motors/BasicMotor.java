package ev3dev.hardware.actuators.motors;

import ev3dev.hardware.DeviceNotSupportedException;
import ev3dev.hardware.EV3DevMotorDevice;
import ev3dev.hardware.SupportedPlatform;
import lejos.robotics.DCMotor;

/** 
 * Abstraction for basic motors operations.
 *
 * Unregulated motors only is enabled for EV3Brick.
 *
 * @author Lawrie Griffiths.
 * @author Juan Antonio Breña Moral
 *
 */
public abstract class BasicMotor extends EV3DevMotorDevice implements EV3DevMotorDeviceCommands, DCMotor {

	//TODO Improve the way to connect with a Motor.
	/**
	 * Constructor
	 *
	 * @param portName
     */
	public BasicMotor(final String portName) {
		super(LEGO_PORT, portName);
		if(!this.getPlatform().equals(SupportedPlatform.EV3BRICK)){
			throw new DeviceNotSupportedException("This device is not supported in this platform");
		}
		this.setStringAttribute(MODE, DC_MOTOR);
		this.connect(DC_MOTOR, portName);
	}

	/**
	 * Set power
	 * @param power new motors power 0-100
     */
    public void setPower(final int power) {
    	this.setIntegerAttribute(DUTY_CYCLE, power);
    }

	/**
	 * Get power
	 * @return
     */
    public int getPower() {
    	return this.getIntegerAttribute(POWER);
    }

	/**
	 * Update the internal state tracking the motor direction
	 * @param newMode
	 */
	protected void updateState(final String newMode) {
		this.setStringAttribute(POLARITY, newMode);
	}

	/**
	 * Causes motors to rotate forward.
	 */
	public void forward() {
		this.updateState(POLARITY_NORMAL);
    	this.setStringAttribute(COMMAND, RUN_FOREVER);
	}

	/**
	 * Causes motors to rotate backwards.
	 */
	public void backward() {
		this.updateState(POLARITY_INVERSED);
    	this.setStringAttribute(COMMAND, RUN_FOREVER);
	}

	/**
	 * Returns true iff the motors is in motion.
	 * 
	 * @return true iff the motors is currently in motion.
	 */
    public boolean isMoving() {
		return this.getStringAttribute(STATE).contains(STATE_RUNNING);
    }

	/**
	 * Causes motors to float. The motors will lose all power,
	 * but this is not the same as stopping. Use this
	 * method if you don't want your robot to trip in
	 * abrupt turns.
	 */   
	public void brake() {
		this.setStringAttribute(STOP_COMMAND, BRAKE);
	}

	/**
	 * Causes the motor to actively try to hold the current position.
	 * If an external force tries to turn the motor, the motor will “push back” to maintain its position.
	 */
	public void hold() {
		this.setStringAttribute(STOP_COMMAND, HOLD);
	}

	/**
	 * Removes power from the motor.
	 * The motor will freely coast to a stop.
	 */
	public void coast() {
		this.setStringAttribute(STOP_COMMAND, COAST);
	}

	/**
	 * Causes motors to stop, pretty much
	 * instantaneously. In other words, the
	 * motors doesn't just stop; it will resist
	 * any further motion.
	 * Cancels any rotate() orders in progress
	 */
	public void stop() {
		this.setStringAttribute(COMMAND, STOP);
	}

}

