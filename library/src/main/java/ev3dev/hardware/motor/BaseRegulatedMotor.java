package ev3dev.hardware.motor;

import ev3dev.hardware.Device;
import ev3dev.hardware.EV3DevDevice;
//import lejos.hardware.ev3.LocalEV3;
//import lejos.robotics.RegulatedMotor;
//import lejos.robotics.RegulatedMotorListener;
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
 * <br>
 * <p>
 * Example:<p>
 * <code><pre>
 *   Motor.A.setSpeed(720);// 2 RPM
 *   Motor.C.setSpeed(720);
 *   Motor.A.forward();
 *   Motor.C.forward();
 *   Delay.msDelay(1000);
 *   Motor.A.stop();
 *   Motor.C.stop();
 *   Motor.A.rotateTo( 360);
 *   Motor.A.rotate(-720,true);
 *   while(Motor.A.isMoving()Thread.yield();
 *   int angle = Motor.A.getTachoCount(); // should be -360
 *   LCD.drawInt(angle,0,0);
 * </pre></code>
 * TODO: Fix the name
 * @author Roger Glassey/Andy Shaw
 */
public abstract class BaseRegulatedMotor extends Device implements RegulatedMotor {



	// Following should be set to the max SPEED (in deg/sec) of the motor when free running and powered by 9V
    protected final int MAX_SPEED_AT_9V;
    protected static final int NO_LIMIT = 0x7fffffff;
    protected final EV3DevDevice reg;
    protected float speed = 360;
    protected int acceleration = 6000;

    private final String SYSTEM_CLASS_NAME = "tacho-motor";

    
    public BaseRegulatedMotor(String motorPort, float moveP, float moveI, float moveD,
			float holdP, float holdI, float holdD, int offset, int maxSpeed) {
    	MAX_SPEED_AT_9V = maxSpeed;
    	reg = new EV3DevDevice(SYSTEM_CLASS_NAME, motorPort);
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
    public boolean suspendRegulation()
    {
        // Putting the motor into float mode disables regulation. note
        // that we wait for the operation to complete.
		final String attribute = "off";
		final String value = "speed_regulation";
		reg.setAttribute(attribute, value);
        return true;
    }


    /**
     * @return the current tachometer count.
     * @see lejos.robotics.RegulatedMotor#getTachoCount()
     */
    public int getTachoCount()
    {
        return 0;//reg.getTachoCount();
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
		final String attribute = "command";
		final String value = "run-forever";
		reg.setAttribute(attribute, value);
    }

    /**
     * @see lejos.hardware.motor.BasicMotor#backward()
     */
    public void backward(){
		final String attribute1 = "inversed";
		final String value1 = "polarity";
		reg.setAttribute(attribute1, value1);
    	final String attribute2 = "command";
		final String value2 = "run-forever";
		reg.setAttribute(attribute2, value2);
    }

    /**
     * Set the motor into float mode. This will stop the motor without braking
     * and the position of the motor will not be maintained.
     */
    public void flt()
    {
        //reg.newMove(0, acceleration, NO_LIMIT, false, true);
    }
    
    /**
     * Set the motor into float mode. This will stop the motor without braking
     * and the position of the motor will not be maintained.
     * @param immediateReturn If true do not wait for the motor to actually stop
     */
    public void flt(boolean immediateReturn)
    {
        //reg.newMove(0, acceleration, NO_LIMIT, false, !immediateReturn);
    }

    /**
     * Causes motor to stop, pretty much
     * instantaneously. In other words, the
     * motor doesn't just stop; it will resist
     * any further motion.
     * Cancels any rotate() orders in progress
     */
    public void stop()
    {
		final String attribute = "command";
		final String value = "stop";
		reg.setAttribute(attribute, value);
    }

    /**
     * Causes motor to stop, pretty much
     * instantaneously. In other words, the
     * motor doesn't just stop; it will resist
     * any further motion.
     * Cancels any rotate() orders in progress
     * @param immediateReturn if true do not wait for the motor to actually stop
     */
    public void stop(boolean immediateReturn)
    {
        //reg.newMove(0, acceleration, NO_LIMIT, true, !immediateReturn);
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
    public boolean isMoving()
    {
        return true; //reg.isMoving();
    }

    /**
     * Wait until the current movement operation is complete (this can include
     * the motor stalling).
     */
    public void waitComplete()
    {
        //reg.waitComplete();
    }

    public void rotateTo(int limitAngle, boolean immediateReturn)
    {
        //reg.newMove(speed, acceleration, limitAngle, true, !immediateReturn);
    }

    /**
     * Sets desired motor speed , in degrees per second;
     * The maximum reliably sustainable velocity is  100 x battery voltage under
     * moderate load, such as a direct drive robot on the level.
     * @param speed value in degrees/sec
     */
    public void setSpeed(int speed)
    {
        this.speed = Math.abs(speed);
		final String attribute = "/duty_cycle_sp";
		reg.setAttribute(attribute, "" + speed);
    }

    /**
     * Sets desired motor speed , in degrees per second;
     * The maximum reliably sustainable velocity is  100 x battery voltage under
     * moderate load, such as a direct drive robot on the level.
     * @param speed value in degrees/sec
     */
    public void setSpeed(float speed)
    {
        this.speed = Math.abs(speed);
		final String attribute = "/duty_cycle_sp";
		reg.setAttribute(attribute, "" + speed);
    }

    /**
     * sets the acceleration rate of this motor in degrees/sec/sec <br>
     * The default value is 6000; Smaller values will make speeding up. or stopping
     * at the end of a rotate() task, smoother;
     * @param acceleration
     */
    public void setAcceleration(int acceleration)
    {
        this.acceleration = Math.abs(acceleration);
        //reg.adjustAcceleration(this.acceleration);
    }

    /**
     * returns acceleration in degrees/second/second
     * @return the value of acceleration
     */
    public int getAcceleration()
    {
        return acceleration;
    }

    /**
     * Return the angle that this Motor is rotating to.
     * @return angle in degrees
     */
    public int getLimitAngle()
    {
        return 0;//reg.getLimitAngle();
    }

    /**
     * Reset the tachometer associated with this motor. Note calling this method
     * will cause any current move operation to be halted.
     */
    public void resetTachoCount() {
		final String attribute = "command";
		final String value = "reset";
		reg.setAttribute(attribute, value);
    }

    /**
     * Add a motor listener. Move operations will be reported to this object.
     * @param listener
     */
    public void addListener(RegulatedMotorListener listener)
    {
        //reg.addListener(this, listener);
    }
    
    public RegulatedMotorListener removeListener() 
    {
        return null;//reg.removeListener();
    }

    /**
     * Rotate by the request number of degrees.
     * @param angle number of degrees to rotate relative to the current position
     * @param immediateReturn if true do not wait for the move to complete
     */
    public void rotate(int angle, boolean immediateReturn)
    {
        //rotateTo(Math.round(reg.getPosition()) + angle, immediateReturn);
    }

    /**
     * Rotate by the requested number of degrees. Wait for the move to complete.
     * @param angle
     */
    public void rotate(int angle)
    {
        //rotate(angle, false);
    	
    }

    /**
     * Rotate to the target angle. Do not return until the move is complete.
     * @param limitAngle Angle to rotate to.
     */
    public void rotateTo(int limitAngle) {
    	final String attribute1 = "position_sp";
    	reg.setAttribute(attribute1, "" + limitAngle);
    	final String attribute2 = "command";
		final String value2 = "run-to-abs-pos";
		reg.setAttribute(attribute2, value2);
    	
    }

    /**
     * Return the current target speed.
     * @return the current target speed.
     */
    public int getSpeed()
    {
        return Math.round(speed);
    }

    /**
     * Return true if the motor is currently stalled.
     * @return true if the motor is stalled, else false
     */
    public boolean isStalled()
    {
        return true;//reg.isStalled();
    }

    /**
     * Set the parameters for detecting a stalled motor. A motor will be recognised
     * as stalled if the movement error (the amount the motor lags the regulated
     * position) is greater than error for a period longer than time.
     * @param error The error threshold
     * @param time The time that the error threshold needs to be exceeded for.
     */
    public void setStallThreshold(int error, int time)
    {
        //reg.setStallThreshold(error, time);
    }

    /**
     * Return the current velocity.
     * @return current velocity in degrees/s
     */
    public int getRotationSpeed()
    {
        return 0;//Math.round(reg.getCurrentVelocity());
    }


    public float getMaxSpeed() {
        // It is generally assumed, that the maximum accurate speed of an EV3 Motor is
        // 100 degree/second * Voltage. We generalise this to other LEGO motors by returning a value
        // that is based on 90% of the maximum free running speed of the motor.
        // TODO: Should this be using the Brick interface?
        // TODO: If we ever allow the regulator class be remote, then we will need to ensure we 
        // get the voltage of the remote brick not the local one.
        //return LocalEV3.ev3.getPower().getVoltage() * MAX_SPEED_AT_9V/9.0f * 0.9f;
    	return 0.0f;
    }

}
