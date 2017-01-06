package lejos.robotics;

/**
 * Interface for a regular DC motors.
 * @author BB
 *
 */
public interface DCMotor extends BaseMotor {

    /**
     * Set the power level 0%-100% to be applied to the motors
     * @param power new motors power 0-100
     */
    void setPower(int power);

    /**
     * Returns the current motors power setting.
     * @return current power 0-100
     */
    int getPower();
}
