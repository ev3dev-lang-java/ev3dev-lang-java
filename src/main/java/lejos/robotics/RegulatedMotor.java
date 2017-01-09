package lejos.robotics;

/**
 * Interface for encoded motors without limited range of movement (e.g. NXT motors).
 * TODO: Alternate names: EncodedMotor,EncoderMotor. 
 * 
 * @author dsturze
 */
public interface RegulatedMotor extends BaseMotor, Tachometer {

    /**
    * Adds a listener object that will be notified when rotation has started or stopped
    * @param listener
    */
    //TODO method name sounds like listener is added to some list.
    // javadoc and method name should be changed such that they indicate that only one listener is supported.
    // suggested method name: setListener(...)
    //public void addListener(RegulatedMotorListener listener);

	/**
	 * Removes the RegulatedMotorListener from this class.
	 * @return The RegulatedMotorListener that was removed, if any. Null if none existed.
	 */
	//public RegulatedMotorListener removeListener();

    /**
    * causes motors to rotate through angle; <br>
    * iff immediateReturn is true, method returns immediately and the motors stops by itself <br>
    * If any motors method is called before the limit is reached, the rotation is canceled.
    * When the angle is reached, the method isMoving() returns false;<br>
    *
    * @param  angle through which the motors will rotate
    * @param immediateReturn iff true, method returns immediately, thus allowing monitoring of sensors in the calling thread.
    *
    *  @see RegulatedMotor#rotate(int, boolean)
    */
    void rotate(int angle, boolean immediateReturn);

    /**
    * Causes motors to rotate by a specified angle. The resulting tachometer count should be within +- 2 degrees on the NXT.
    * This method does not return until the rotation is completed.
    *
    * @param angle by which the motors will rotate.
    *
    */
    void rotate(int angle);

  
    /**
    * Causes motors to rotate to limitAngle;  <br>
    * Then getTachoCount should be within +- 2 degrees of the limit angle when the method returns
    * @param  limitAngle to which the motors will rotate, and then stop (in degrees). Includes any positive or negative int, even values &gt; 360.
    */
    void rotateTo(int limitAngle);

    /**
    * causes motors to rotate to limitAngle; <br>
    * if immediateReturn is true, method returns immediately and the motors stops by itself <br>
    * and getTachoCount should be within +- 2 degrees if the limit angle
    * If any motors method is called before the limit is reached, the rotation is canceled.
    * When the angle is reached, the method isMoving() returns false;<br>
    * @param  limitAngle to which the motors will rotate, and then stop (in degrees). Includes any positive or negative int, even values &gt; 360.
    * @param immediateReturn iff true, method returns immediately, thus allowing monitoring of sensors in the calling thread.
    */
    void rotateTo(int limitAngle, boolean immediateReturn);

    /**
    * Return the limit angle (if any)
    * @return the current limit angle
    */
    //public int getLimitAngle();

    /**
    * Set motors speed. As a rule of thumb 100 degrees per second are possible for each volt on an NXT motors. Therefore,
    * disposable alkaline batteries can achieve a top speed of 900 deg/sec, while a rechargable lithium battery pack can achieve
    * 740 deg/sec.
    *
    * @param speed in degrees per second.
    */
    void setSpeed(int speed);

    /**
    * Returns the current motors speed.
    *
    * @return motors speed in degrees per second
    */
    int getSpeed();

    /**
    * Returns the maximum speed that can be maintained by the regulation system based upon the
    * current state of the battery.
    *
    * @return the maximum speed of the Motor in degrees per second.
    */
    //float getMaxSpeed();
  
    /**
    * returns true if motors is stalled
    * @return true if stalled
    */
    boolean isStalled();

    /**
    * Set the parameters for detecting a stalled motors. A motors will be recognized as
    * stalled if the movement error (the amount the motors lags the regulated position)
    * is greater than error for a period longer than time.
    *
    * @param error The error threshold
    * @param time The time that the error threshold needs to be exceeded for.
    */
    //void setStallThreshold(int error, int time);
   
    /**
    * Set the required rate of acceleration degrees/s/s
    * @param acceleration
    */
    //void setAcceleration(int acceleration);
    /**
    * Specify a set of motors that should be kept in synchronization with this one.
    * The synchronization mechanism simply ensures that operations between a startSynchronization
    * call and an endSynchronization call will all be executed at the same time (when the
    * endSynchronization method is called). This is all that is needed to ensure that motors
    * will operate in a synchronized fashion. The start/end methods can also be used to ensure
    * that reads of the motors state will also be consistent.
    * @param syncList an array of motors to synchronize with.
    */
    //public void synchronizeWith(RegulatedMotor[] syncList);

    /**
    * Begin a set of synchronized motors operations
    */
    //public void startSynchronization();

    /**
    * Complete a set of synchronized motors operations.
    */
    //public void endSynchronization();

    /**
    * Close the ports, the ports can not be used after this call.
    */
    //void close();
   
}