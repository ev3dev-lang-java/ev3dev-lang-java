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

    //dc-motor > /sys/class/lego-port/port6/mode
    
	public BasicMotor(String type, String portName) {
		super(type, portName);
	}

	public BasicMotor(String type, String portName, boolean force) {
		super(type, portName);
	}
	
    protected int power = 0;

    public void setPower(int power) {
        this.power = power;
        //port.controlMotor(power, mode);
    }

    public int getPower() {
        return power;
    }

	/**
	 * Causes motor to rotate forward.
	 */
	public void forward() { 
		//updateState( BasicMotorPort.FORWARD);
	}
	  

	/**
	 * Causes motor to rotate backwards.
	 */
	public void backward()
	{
		//updateState( BasicMotorPort.BACKWARD);
	}


	/**
	 * Returns true iff the motor is in motion.
	 * 
	 * @return true iff the motor is currently in motion.
	 */
	public boolean isMoving() {
		//return (mode == BasicMotorPort.FORWARD || mode == BasicMotorPort.BACKWARD);
		return true;
	}

	/**
	 * Causes motor to float. The motor will lose all power,
	 * but this is not the same as stopping. Use this
	 * method if you don't want your robot to trip in
	 * abrupt turns.
	 */   
	public void flt() {
		//updateState( BasicMotorPort.FLOAT);
	}

	  
	/**
	 * Causes motor to stop, pretty much
	 * instantaneously. In other words, the
	 * motor doesn't just stop; it will resist
	 * any further motion.
	 * Cancels any rotate() orders in progress
	 */
	public void stop() {
		//updateState( BasicMotorPort.STOP);
	}
}

