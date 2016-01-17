package ev3dev.hardware.motor;

import lejos.robotics.RegulatedMotor;
import ev3dev.hardware.EV3DevDevice;

/**
 * Abstraction for a Regulated motor motor.
 * The basic control methods are:
 *  <code>forward, backward, reverseDirection, stop</code>
 * and <code>flt</code>. To set each motor's velocity, use {@link #setSpeed(int)
 * <code>setSpeed  </code> }.
 * The maximum velocity of the motor is limited by the battery voltage and load.
 * With no load, the maximum degrees per second is about 100 times the voltage
 * (for the large EV3 motor).  <br>
 * The velocity is regulated by comparing the tacho count with velocity times elapsed
 * time, and adjusting motor power to keep these closely matched. Changes in velocity
 * will be made at the rate specified via the
 * <code> setAcceleration(int acceleration)</code> method.
 * The methods <code>rotate(int angle) </code> and <code>rotateTo(int ange)</code>
 * use the tachometer to control the position at which the motor stops, usually within 1 degree
 * or 2.<br>
 *  <br> <b> Listeners.</b>  An object implementing the {@link lejos.robotics.RegulatedMotorListener
 * <code> RegulatedMotorListener </code> } interface  may register with this class.
 * It will be informed each time the motor starts or stops.
 * <br> <b>Stall detection</b> If a stall is detected or if for some other reason
 * the speed regulation fails, the motor will stop, and
 * <code>isStalled()</code >  returns <b>true</b>.
 * <br>Motors will hold their position when stopped. If this is not what you require use
 * the flt() method instead of stop().
 * 
 * TODO: Fix the name
 * @author Roger Glassey
 * @author Andy Shaw
 * @author Juan Antonio Breña Moral
 */
public abstract class BaseRegulatedMotor extends EV3DevDevice implements RegulatedMotor {

    protected float speed = 360;
    protected int acceleration = 6000;

    private final static String SYSTEM_CLASS_NAME = "tacho-motor";
    private final static String SYSTEM_PORT_CLASS_NAME = "lego-port";
	private final String MODE = "mode";
	private final String SPEED_REGULATION = "speed_regulation";
	private final String SPEED_REGULATION_ON = "on";
	private final String SPEED_REGULATION_OFF = "off";
	private final String POSITION = "position_sp";
	private final String SPEED = "speed_sp";
	private final String DUTY_CYCLE = "duty_cycle_sp";
	private final String COMMAND = "command";
	private final String RUN_FOREVER = "run-forever";
	private final String BRAKE = "brake";
	private final String STOP = "stop";
	private final String RESET = "reset";
	private final String RUN_TO_REL_POS = "run-to-rel-pos";
	private final String RUN_TO_ABS_POS = "run-to-abs-pos";
	private final String STATE = "state";
	private final String STATE_RUNNING = "running";
	private final String STATE_STALLED = "stalled";
	
    private int local_speed = 0;
    private boolean regulationFlag = false;
    
    public BaseRegulatedMotor(String motorPort, float moveP, float moveI, float moveD,
			float holdP, float holdI, float holdD, int offset, int maxSpeed) {
		super(SYSTEM_PORT_CLASS_NAME, motorPort);
		this.setStringAttribute(MODE,SYSTEM_CLASS_NAME);
		this.connect(SYSTEM_CLASS_NAME, motorPort);

		this.setStringAttribute(SPEED_REGULATION, SPEED_REGULATION_ON);
		this.regulationFlag = true;
	}


	/**
     * Close the motor regulator. Release the motor from regulation and free any
     * associated resources.
     */
    public void close() {
        super.close();
    }
    
    /**
     * Removes this motor from the motor regulation system. After this call
     * the motor will be in float mode and will have stopped. Note calling any
     * of the high level move operations (forward, rotate etc.), will
     * automatically enable regulation.
     * @return true iff regulation has been suspended.
     */
    public boolean suspendRegulation() {
		this.setStringAttribute(SPEED_REGULATION, SPEED_REGULATION_OFF);
		this.regulationFlag = false;
        return true;
    }


    /**
     * @return the current tachometer count.
     * @see lejos.robotics.RegulatedMotor#getTachoCount()
     */
    public int getTachoCount() {
    	return getIntegerAttribute(POSITION);
    }

    /**
     * Returns the current position that the motor regulator is trying to
     * maintain. Normally this will be the actual position of the motor and will
     * be the same as the value returned by getTachoCount(). However in some
     * circumstances (activeMotors that are in the process of stalling, or activeMotors
     * that have been forced out of position), the two values may differ. Note that
     * if regulation has been suspended calling this method will restart it.
     * @return the current position calculated by the regulator.
     */
    public float getPosition()
    {
        return 0.0f; //reg.getPosition();
    }

    /**
     * @see lejos.hardware.motor.BasicMotor#forward()
     */
    public void forward() {
    	if(this.local_speed != 0) {
    		String attribute = SPEED;
    		if(!this.regulationFlag) {
    			attribute = DUTY_CYCLE;
    		}
    		int value = Math.abs(this.local_speed) * 1;
    		this.setIntegerAttribute(attribute, value);  
    	}
		this.setStringAttribute(COMMAND, RUN_FOREVER);
    }

    /**
     * @see lejos.hardware.motor.BasicMotor#backward()
     */
    public void backward(){
    	if(this.local_speed != 0) {
    		String attribute = SPEED;
    		if(!this.regulationFlag) {
    			attribute = DUTY_CYCLE;
    		}
    		int value = Math.abs(this.local_speed) * -1;
    		this.setIntegerAttribute(attribute, value); 
    	}
		this.setStringAttribute(COMMAND, RUN_FOREVER);
    }

    /**
     * Set the motor into float mode. This will stop the motor without braking
     * and the position of the motor will not be maintained.
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

    /**
     * This method returns <b>true </b> if the motor is attempting to rotate.
     * The return value may not correspond to the actual motor movement.<br>
     * For example,  If the motor is stalled, isMoving()  will return <b> true. </b><br>
     * After flt() is called, this method will return  <b>false</b> even though the motor
     * axle may continue to rotate by inertia.
     * If the motor is stalled, isMoving()  will return <b> true. </b> . A stall can
     * be detected  by calling {@link #isStalled()};
     * @return true iff the motor is attempting to rotate.<br>
     */
    public boolean isMoving() {
		return (this.getStringAttribute(STATE).contains(STATE_RUNNING));
    }

    /**
     * Sets desired motor speed , in degrees per second;
     * The maximum reliably sustainable velocity is  100 x battery voltage under
     * moderate load, such as a direct drive robot on the level.
     * @param speed value in degrees/sec
     */
    public void setSpeed(int speed) {
    	this.local_speed = speed;
		String attribute = SPEED;
		if(!this.regulationFlag) {
			attribute = DUTY_CYCLE;
		}
		this.setIntegerAttribute(attribute, this.local_speed);
    }

    /**
     * Reset the tachometer associated with this motor. Note calling this method
     * will cause any current move operation to be halted.
     */
    public void resetTachoCount() {
		this.setStringAttribute(COMMAND, RESET);

		this.setStringAttribute(SPEED_REGULATION, SPEED_REGULATION_ON);
		this.regulationFlag = true;
    }

    private boolean isRunning() {
		return (this.getStringAttribute(STATE).contains(STATE_RUNNING));
    }
    
    /**
     * Rotate by the request number of degrees.
     * @param angle number of degrees to rotate relative to the current position
     * @param immediateReturn if true do not wait for the move to complete
     * Rotate by the requested number of degrees. Wait for the move to complete.
     * @param angle
     */
    public void rotate(int angle, boolean immediateReturn) {
		this.setIntegerAttribute(POSITION, angle);
		this.setStringAttribute(COMMAND, RUN_TO_REL_POS);
		
		if (!immediateReturn) {
			while(this.isRunning()){
			   // do stuff or do nothing
			   // possibly sleep for some short interval to not block
			}
		}
    }

    /**
     * Rotate by the requested number of degrees. Wait for the move to complete.
     * @param angle
     */
    public void rotate(int angle) {
        rotate(angle, false);
    }

    public void rotateTo(int limitAngle, boolean immediateReturn) {
    	this.setIntegerAttribute(POSITION, limitAngle);
    	this.setStringAttribute(COMMAND, RUN_TO_ABS_POS);
		
		if (!immediateReturn) {
			while(this.isRunning()){
			   // do stuff or do nothing
			   // possibly sleep for some short interval to not block
			}
		}
    }
    
    /**
     * Rotate to the target angle. Do not return until the move is complete.
     * @param limitAngle Angle to rotate to.
     */
    public void rotateTo(int limitAngle) {
    	rotateTo(limitAngle, false);
    }

    /**
     * Return the current target speed.
     * @return the current target speed.
     */
    public int getSpeed() {
		String attribute = SPEED;
		if(!this.regulationFlag) {
			attribute = DUTY_CYCLE;
		}
        return this.getIntegerAttribute(attribute);
    }

    /**
     * Return true if the motor is currently stalled.
     * @return true if the motor is stalled, else false
     */
    public boolean isStalled() {
		return (this.getStringAttribute(STATE).contains(STATE_STALLED));
    }
    
    /**
     * Return the current velocity.
     * @return current velocity in degrees/s
     */
    public int getRotationSpeed() {
        return 0;//Math.round(reg.getCurrentVelocity());
    }


}
