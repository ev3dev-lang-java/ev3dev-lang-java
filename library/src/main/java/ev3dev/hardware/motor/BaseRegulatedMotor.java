package ev3dev.hardware.motor;

import ev3dev.hardware.Device;
import ev3dev.hardware.DeviceException;
//import lejos.hardware.ev3.LocalEV3;
//import lejos.hardware.port.Port;
//import lejos.hardware.port.TachoMotorPort;
//import lejos.internal.ev3.EV3MotorPort;
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
    protected final MotorRegulator reg;
    //protected TachoMotorPort tachoPort;
    protected float speed = 360;
    protected int acceleration = 6000;

	public BaseRegulatedMotor(String type, String portName) throws DeviceException {
		super(type, portName);
		// TODO Auto-generated constructor stub
		reg = null;
		MAX_SPEED_AT_9V = 9;
	}
    
    /**
     * Use this constructor to assign a variable of type motor connected to a particular port.
     * @param port  to which this motor is connected

    public BaseRegulatedMotor(TachoMotorPort port, MotorRegulator regulator, 
            int typ, float moveP, float moveI, float moveD, float holdP, float holdI, float holdD, int offset, int maxSpeed)
    {
        tachoPort = port;
        // Use default regulator if non specified
        if (regulator == null)
            reg = port.getRegulator();
        else
            reg = regulator;
        MAX_SPEED_AT_9V = maxSpeed;
        reg.setControlParamaters(typ, moveP, moveI, moveD, holdP, holdI, holdD, offset);   
    }
         */
    
    /**
     * Use this constructor to assign a variable of type motor connected to a particular port.
     * @param port  to which this motor is connected

    public BaseRegulatedMotor(Port port, MotorRegulator regulator, int typ, float moveP, float moveI,
            float moveD, float holdP, float holdI, float holdD, int offset, int maxSpeed)
    {
        this(port.open(TachoMotorPort.class), regulator, typ, moveP, moveI, moveD, holdP, holdI, holdD, offset, maxSpeed);
        releaseOnClose(tachoPort);
    }
         */

    /**
     * Close the motor regulator. Release the motor from regulation and free any
     * associated resources.

    public void close()
    {
        suspendRegulation();
        super.close();
    }
     */
    
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
        reg.newMove(0, acceleration, NO_LIMIT, false, true);
        return true;
    }


    /**
     * @return the current tachometer count.
     * @see lejos.robotics.RegulatedMotor#getTachoCount()
     */
    public int getTachoCount()
    {
        return reg.getTachoCount();
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
        return reg.getPosition();
    }

    /**
     * @see lejos.hardware.motor.BasicMotor#forward()
     */
    public void forward()
    {
        reg.newMove(speed, acceleration, +NO_LIMIT, true, false);
    }

    /**
     * @see lejos.hardware.motor.BasicMotor#backward()
     */
    public void backward()
    {
        reg.newMove(speed, acceleration, -NO_LIMIT, true, false);
    }

    /**
     * Set the motor into float mode. This will stop the motor without braking
     * and the position of the motor will not be maintained.
     */
    public void flt()
    {
        reg.newMove(0, acceleration, NO_LIMIT, false, true);
    }
    
    /**
     * Set the motor into float mode. This will stop the motor without braking
     * and the position of the motor will not be maintained.
     * @param immediateReturn If true do not wait for the motor to actually stop
     */
    public void flt(boolean immediateReturn)
    {
        reg.newMove(0, acceleration, NO_LIMIT, false, !immediateReturn);
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
        reg.newMove(0, acceleration, NO_LIMIT, true, true);
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
        reg.newMove(0, acceleration, NO_LIMIT, true, !immediateReturn);
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
        return reg.isMoving();
    }

    /**
     * Wait until the current movement operation is complete (this can include
     * the motor stalling).
     */
    public void waitComplete()
    {
        reg.waitComplete();
    }

    public void rotateTo(int limitAngle, boolean immediateReturn)
    {
        reg.newMove(speed, acceleration, limitAngle, true, !immediateReturn);
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
        reg.adjustSpeed(this.speed);
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
        reg.adjustSpeed(this.speed);
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
        reg.adjustAcceleration(this.acceleration);
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
        return reg.getLimitAngle();
    }

    /**
     * Reset the tachometer associated with this motor. Note calling this method
     * will cause any current move operation to be halted.
     */
    public void resetTachoCount()
    {
        reg.resetTachoCount();
    }

    /**
     * Add a motor listener. Move operations will be reported to this object.
     * @param listener
     */
    public void addListener(RegulatedMotorListener listener)
    {
        reg.addListener(this, listener);
    }
    
    public RegulatedMotorListener removeListener() 
    {
        return reg.removeListener();
    }

    /**
     * Rotate by the request number of degrees.
     * @param angle number of degrees to rotate relative to the current position
     * @param immediateReturn if true do not wait for the move to complete
     */
    public void rotate(int angle, boolean immediateReturn)
    {
        rotateTo(Math.round(reg.getPosition()) + angle, immediateReturn);
    }

    /**
     * Rotate by the requested number of degrees. Wait for the move to complete.
     * @param angle
     */
    public void rotate(int angle)
    {
        rotate(angle, false);
    }

    /**
     * Rotate to the target angle. Do not return until the move is complete.
     * @param limitAngle Angle to rotate to.
     */
    public void rotateTo(int limitAngle)
    {
        rotateTo(limitAngle, false);
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
     * @deprecated The regulator will always try to hold position unless the
     * motor is set into float mode using flt().
     * @param power - a value between 1 and 100;
     */
    @Deprecated
    public void lock(int power)
    {
        stop(false);
    }

    /**
     * Return true if the motor is currently stalled.
     * @return true if the motor is stalled, else false
     */
    public boolean isStalled()
    {
        return reg.isStalled();
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
        reg.setStallThreshold(error, time);
    }
    /**
     * Return the current velocity.
     * @return current velocity in degrees/s
     */
    public int getRotationSpeed()
    {
        return Math.round(reg.getCurrentVelocity());
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

    /**
     * Specify a set of motors that should be kept in synchronization with this one.
     * The synchronization mechanism simply ensures that operations between a startSynchronization
     * call and an endSynchronization call will all be executed at the same time (when the 
     * endSynchronization method is called). This is all that is needed to ensure that motors
     * will operate in a synchronized fashion. The start/end methods can also be used to ensure
     * that reads of the motor state will also be consistent.
     * @param syncList an array of motors to synchronize with.
     */
    public void synchronizeWith(RegulatedMotor[] syncList)
    {
        // Create list of regualtors and pass it on!
        MotorRegulator[] rl = new MotorRegulator[syncList.length];
        for(int i = 0; i < syncList.length; i++)
            rl[i] = ((BaseRegulatedMotor)syncList[i]).reg;
        reg.synchronizeWith(rl);
    }

    /**
     * Begin a set of synchronized motor operations
     */
    public void startSynchronization()
    {
        reg.startSynchronization();        
    }

    /**
     * Complete a set of synchronized motor operations.
     */
    public void endSynchronization()
    {
        reg.endSynchronization(true);        
    }
}
