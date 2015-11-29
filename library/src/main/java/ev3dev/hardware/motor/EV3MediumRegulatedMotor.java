package ev3dev.hardware.motor;

import ev3dev.hardware.port.TachoMotorPortNew;


//import lejos.hardware.port.Port;
//import lejos.hardware.port.TachoMotorPort;
//import lejos.hardware.sensor.EV3SensorConstants;

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
    
    /**
     * Use this constructor to assign a variable of type motor connected to a particular port.
     * @param port  to which this motor is connected

    public EV3MediumRegulatedMotor(TachoMotorPort port)
    {
        super(port, null, 9, MOVE_P, MOVE_I, MOVE_D,
                HOLD_P, HOLD_I, HOLD_D, OFFSET, MAX_SPEED);
    }
         */
    
    /**
     * Use this constructor to assign a variable of type motor connected to a particular port.
     * @param port  to which this motor is connected

    public EV3MediumRegulatedMotor(Port port)
    {
        super(port, null, EV3SensorConstants.TYPE_NEWTACHO, MOVE_P, MOVE_I, MOVE_D,
                HOLD_P, HOLD_I, HOLD_D, OFFSET, MAX_SPEED);
    }
         */

	public EV3MediumRegulatedMotor(TachoMotorPortNew port, Object regulator,
			int typeNewtacho, float moveP, float moveI, float moveD,
			float holdP, float holdI, float holdD, int offset, int maxSpeed) {
		super(port, regulator, typeNewtacho, moveP, moveI, moveD, holdP, holdI, holdD,
				offset, maxSpeed);
		// TODO Auto-generated constructor stub
	}

}
