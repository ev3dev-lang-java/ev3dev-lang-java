package ev3dev.hardware.actuators.motors;

import ev3dev.hardware.EV3DevMotorDevice;
import ev3dev.hardware.SupportedPlatform;
import lejos.robotics.DCMotor;

/** 
 * Abstraction for basic motors operations.
 * 
 * @author Lawrie Griffiths.
 * @author Juan Antonio Breña Moral
 *
 */
public abstract class BasicMotor extends EV3DevMotorDevice implements DCMotor {

    private final static String SYSTEM_CLASS_NAME = "dc-motor";
    private final static String SYSTEM_PORT_CLASS_NAME = "lego-port";
	private final String MODE = "mode";
	private final String DUTY_CYCLE = "duty_cycle_sp";
	private final String POWER = "power";
	private final String COMMAND = "command";
	private final String STOP_COMMAND = "stop_action";
	private final String RUN_FOREVER = "run-forever";
	private final String COAST = "coast";
	private final String BRAKE = "brake";
	private final String HOLD = "hold";
	private final String STOP = "stop";
	private final String STATE = "state";
	private final String STATE_RUNNING = "running";

	//This feature is only allowed with EV3Brick
	private final static String[] SUPPORTED_PLATFORMS = {SupportedPlatform.EV3BRICK.toString()};
	
	public BasicMotor(String portName) {
		super(SYSTEM_PORT_CLASS_NAME, portName, SUPPORTED_PLATFORMS);
		this.setStringAttribute(MODE, SYSTEM_CLASS_NAME);
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
	 * Causes motors to rotate forward.
	 */
	public void forward() {
    	if(this.local_power != 0) {
    		int value = Math.abs(this.local_power) * 1;
    		this.setIntegerAttribute(DUTY_CYCLE, value);  
    	}
    	this.setStringAttribute(COMMAND, RUN_FOREVER);
	}
	  

	/**
	 * Causes motors to rotate backwards.
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
	 * Returns true iff the motors is in motion.
	 * 
	 * @return true iff the motors is currently in motion.
	 */
    public boolean isMoving() {
		return (this.getStringAttribute(STATE).contains(STATE_RUNNING));
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

	public void hold() {
		this.setStringAttribute(STOP_COMMAND, HOLD);
	}

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

