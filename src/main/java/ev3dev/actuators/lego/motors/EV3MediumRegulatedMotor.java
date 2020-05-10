package ev3dev.actuators.lego.motors;

import lejos.hardware.port.Port;

/**
 * Abstraction for a Medium Lego EV3/NXT motors.
 */
public class EV3MediumRegulatedMotor extends BaseRegulatedMotor {

    private static final float MOVE_P = 8f;
    private static final float MOVE_I = 0.04f;
    private static final float MOVE_D = 8f;
    private static final float HOLD_P = 8f;
    private static final float HOLD_I = 0.02f;
    private static final float HOLD_D = 0f;
    private static final int OFFSET = 1000;

    private static final int MAX_SPEED = 260 * 360 / 60;

    /**
     * Constructor
     *
     * @param motorPort motor port
     */
    public EV3MediumRegulatedMotor(final Port motorPort) {
        super(motorPort, MOVE_P, MOVE_I, MOVE_D, HOLD_P, HOLD_I, HOLD_D, OFFSET, MAX_SPEED);
    }

}
