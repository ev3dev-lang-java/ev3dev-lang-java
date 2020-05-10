package ev3dev.actuators.lego.motors;

import ev3dev.hardware.EV3DevMotorDevice;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.hardware.EV3DevPlatforms;
import lejos.hardware.port.Port;
import lejos.robotics.DCMotor;
import lejos.utility.Delay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Abstraction for basic motors operations.
 *
 * <p>Unregulated motors only is enabled for EV3Brick.
 *
 * @author Lawrie Griffiths.
 * @author Juan Antonio Breña Moral
 */
public abstract class BasicMotor extends EV3DevMotorDevice implements DCMotor {

    private static final Logger log = LoggerFactory.getLogger(BasicMotor.class);

    private int power = 50;

    /**
     * Constructor
     *
     * @param motorPort port
     */
    public BasicMotor(final Port motorPort) {

        Set<EV3DevPlatform> supportedUnregulatedMotors = new HashSet<>();
        supportedUnregulatedMotors.add(EV3DevPlatform.EV3BRICK);
        supportedUnregulatedMotors.add(EV3DevPlatform.BRICKPI3);

        if (!supportedUnregulatedMotors.contains(CURRENT_PLATFORM)) {
            throw new RuntimeException("This device is not supported in the platform: " + CURRENT_PLATFORM);
        }

        final EV3DevPlatforms ev3DevPlatforms = EV3DevPlatforms.getInstance();
        final String port = ev3DevPlatforms.getMotorPort(motorPort);

        log.debug("Detecting motor on port: {}", port);
        this.detect(LEGO_PORT, port);
        log.debug("Setting port in mode: {}", DC_MOTOR);
        this.setStringAttribute(MODE, DC_MOTOR);
        Delay.msDelay(500);
        this.detect(DC_MOTOR, port);
    }

    /**
     * Set power
     *
     * @param power new motors power 0-100
     */
    @Override
    public void setPower(final int power) {
        this.power = power;
        this.setIntegerAttribute(DUTY_CYCLE, power);
    }

    /**
     * Get power
     *
     * @return power
     */
    @Override
    public int getPower() {
        return this.getIntegerAttribute(DUTY_CYCLE);
    }

    /**
     * Update the internal state tracking the motor direction
     *
     * @param newMode mode
     */
    protected void updateState(final String newMode) {
        this.setStringAttribute(POLARITY, newMode);
    }

    /**
     * Causes motors to rotate forward.
     */
    @Override
    public void forward() {
        this.updateState(POLARITY_NORMAL);
        this.setPower(this.power);
        this.setStringAttribute(COMMAND, RUN_FOREVER);
    }

    /**
     * Causes motors to rotate backwards.
     */
    @Override
    public void backward() {
        this.updateState(POLARITY_INVERSED);
        this.setPower(this.power);
        this.setStringAttribute(COMMAND, RUN_FOREVER);
    }

    /**
     * Returns true iff the motors is in motion.
     *
     * @return true iff the motors is currently in motion.
     */
    @Override
    public boolean isMoving() {
        return this.getStringAttribute(STATE).contains(STATE_RUNNING);
    }

    /**
     * Causes motors to float. The motors will lose all power,
     * but this is not the same as stopping. Use this
     * method if you don't want your robot to trip in
     * abrupt turns.
     */
    @Override
    public void flt() {
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

