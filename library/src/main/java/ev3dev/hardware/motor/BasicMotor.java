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
	private final String DUTY_CYCLE = "duty_cycle_sp";
	private final String POWER = "power";
	private final String COMMAND = "command";
	private final String RUN_FOREVER = "run-forever";
	private final String BRAKE = "brake";
	private final String STOP = "stop";
	private final String STATE = "state";
	private final String STATE_RUNNING = "running";
	
	public BasicMotor(String portName) {
		super(SYSTEM_PORT_CLASS_NAME, portName);
		this.setStringAttribute(MODE,SYSTEM_CLASS_NAME);
		this.connect(SYSTEM_CLASS_NAME, portName);
	}
	
    protected int local_power = 0;

    public void setPower(int power) {
    	this.local_power = power;
    	this.setIntegerAttribute(DUTY_CYCLE, local_power);
    }

    public int getPower() {
    	return this.getIntegerAttribute(POWER);
    }

	/**
	 * Causes motor to rotate forward.
	 */
	public void forward() {
    	if(this.local_power != 0) {
    		int value = Math.abs(this.local_power) * 1;
    		this.setIntegerAttribute(DUTY_CYCLE, value);  
    	}
    	this.setStringAttribute(COMMAND, RUN_FOREVER);
	}
	  

	/**
	 * Causes motor to rotate backwards.
	 */
	public void backward() {
    	if(this.local_power != 0) {
    		int value = Math.abs(this.local_power) * -1;
    		this.setIntegerAttribute(DUTY_CYCLE, value);  
    	}
		//TODO: Learn to use this attribute
		//final String attribute1 = "inversed";
		//final String value1 = "polarity";
    	this.setStringAttribute(COMMAND, RUN_FOREVER);
	}


	/**
	 * Returns true iff the motor is in motion.
	 * 
	 * @return true iff the motor is currently in motion.
	 */
    public boolean isMoving() {
		return (this.getStringAttribute(STATE).contains(STATE_RUNNING));
    }

	/**
	 * Causes motor to float. The motor will lose all power,
	 * but this is not the same as stopping. Use this
	 * method if you don't want your robot to trip in
	 * abrupt turns.
	 */   
	public void flt() {
		this.setStringAttribute(COMMAND, BRAKE);
	}

	/**
	 * Causes motor to stop, pretty much
	 * instantaneously. In other words, the
	 * motor doesn't just stop; it will resist
	 * any further motion.
	 * Cancels any rotate() orders in progress
	 */
	public void stop() {
		this.setStringAttribute(COMMAND, STOP);
	}
}

