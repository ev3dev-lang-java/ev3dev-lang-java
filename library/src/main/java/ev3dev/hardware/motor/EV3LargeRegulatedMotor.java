package ev3dev.hardware.motor;

import ev3dev.hardware.EV3DevDevice;

/**
 * Abstraction for a Large Lego EV3/NXT motor.
 * 
 */
public class EV3LargeRegulatedMotor extends BaseRegulatedMotor {

	static final float MOVE_P = 4f;
    static final float MOVE_I = 0.04f;
    static final float MOVE_D = 10f;
    static final float HOLD_P = 2f;
    static final float HOLD_I = 0.02f;
    static final float HOLD_D = 8f;
    static final int OFFSET = 0;
    
    private static final int MAX_SPEED = 175*360/60;

    public EV3LargeRegulatedMotor(final String motorPort){
        super(
        	motorPort,
        	MOVE_P, 
        	MOVE_I, 
        	MOVE_D,
            HOLD_P, 
            HOLD_I, 
            HOLD_D, 
            OFFSET, 
            MAX_SPEED);
    }

}
