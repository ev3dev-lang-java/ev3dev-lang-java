package ev3dev.hardware.motor;

/**
 * Abstraction for a Medium Lego EV3/NXT motor.
 * 
 */
public class EV3MediumRegulatedMotor extends BaseRegulatedMotor
{

	static final float MOVE_P = 8f;
    static final float MOVE_I = 0.04f;
    static final float MOVE_D = 8f;
    static final float HOLD_P = 8f;
    static final float HOLD_I = 0.02f;
    static final float HOLD_D = 0f;
    static final int OFFSET = 1000;
    
    private static final int MAX_SPEED = 260*360/60;

	public EV3MediumRegulatedMotor(final String motorPort) {
		super(motorPort, MOVE_P, MOVE_I, MOVE_D,
                HOLD_P, HOLD_I, HOLD_D, OFFSET, MAX_SPEED);
	}

}
