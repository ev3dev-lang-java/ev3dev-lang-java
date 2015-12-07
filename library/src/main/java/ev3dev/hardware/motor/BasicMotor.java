package ev3dev.hardware.motor;

import lejos.robotics.DCMotor;
import ev3dev.hardware.EV3DevDevice;

/** 
 * Abstraction for basic motor operations.
 * 
 * @author Lawrie Griffiths.
 * @author Juan Antonio Breña Moral
 *
 */
public abstract class BasicMotor extends EV3DevDevice implements DCMotor {

    private final static String SYSTEM_CLASS_NAME = "dc-motor";
    private final static String SYSTEM_PORT_CLASS_NAME = "lego-port";
	private final String MODE = "mode";
	private final String POWER = "power";
	
	public BasicMotor(String portName) {
		super(SYSTEM_PORT_CLASS_NAME, portName);
		this.setAttribute(MODE,SYSTEM_CLASS_NAME);
		this.connect(SYSTEM_CLASS_NAME, portName);
	}
	
    protected int power = 0;

    public void setPower(int power) {
    	this.setAttribute(POWER,"" + power);
    }

    public int getPower() {
    	return Integer.parseInt(this.getAttribute(POWER));
    }

	/**
	 * Causes motor to rotate forward.
	 */
	public void forward() { 
		final String attribute = "command";
		final String value = "run-forever";
		this.setAttribute(attribute, value);
	}
	  

	/**
	 * Causes motor to rotate backwards.
	 */
	public void backward() {
		final String attribute1 = "inversed";
		final String value1 = "polarity";
		this.setAttribute(attribute1, value1);
    	final String attribute2 = "command";
		final String value2 = "run-forever";
		this.setAttribute(attribute2, value2);
	}


	/**
	 * Returns true iff the motor is in motion.
	 * 
	 * @return true iff the motor is currently in motion.
	 */
	public boolean isMoving() {
		final String attribute = "state";
		final String STATE_RUNNING = "running";
		return (this.getAttribute(attribute) == STATE_RUNNING);
	}

	/**
	 * Causes motor to float. The motor will lose all power,
	 * but this is not the same as stopping. Use this
	 * method if you don't want your robot to trip in
	 * abrupt turns.
	 */   
	public void flt() {
		final String attribute = "command";
		final String value = "brake";
		this.setAttribute(attribute, value);
	}

	/**
	 * Causes motor to stop, pretty much
	 * instantaneously. In other words, the
	 * motor doesn't just stop; it will resist
	 * any further motion.
	 * Cancels any rotate() orders in progress
	 */
	public void stop() {
		final String attribute = "command";
		final String value = "stop";
		this.setAttribute(attribute, value);
	}
}

