package ev3dev.hardware.motor;

import ev3dev.hardware.DeviceException;

//import lejos.hardware.port.Port;
//import lejos.hardware.port.TachoMotorPort;
//import lejos.hardware.sensor.EV3SensorConstants;

/**
 * Abstraction for a Large Lego EV3/NXT motor.
 * 
 */
public class EV3LargeRegulatedMotor extends BaseRegulatedMotor
{
    public EV3LargeRegulatedMotor(String type, String portName)
			throws DeviceException {
		super(type, portName);
		// TODO Auto-generated constructor stub
	}

	static final float MOVE_P = 4f;
    static final float MOVE_I = 0.04f;
    static final float MOVE_D = 10f;
    static final float HOLD_P = 2f;
    static final float HOLD_I = 0.02f;
    static final float HOLD_D = 8f;
    static final int OFFSET = 0;
    
    private static final int MAX_SPEED = 175*360/60;

    /**
     * Use this constructor to assign a variable of type motor connected to a particular port.
     * @param port  to which this motor is connected

    public EV3LargeRegulatedMotor(TachoMotorPort port)
    {
        super(port, null, EV3SensorConstants.TYPE_NEWTACHO, MOVE_P, MOVE_I, MOVE_D,
                HOLD_P, HOLD_I, HOLD_D, OFFSET, MAX_SPEED);
    }
         */
    
    /**
     * Use this constructor to assign a variable of type motor connected to a particular port.
     * @param port  to which this motor is connected

    public EV3LargeRegulatedMotor(Port port)
    {
        super(port, null, EV3SensorConstants.TYPE_NEWTACHO, MOVE_P, MOVE_I, MOVE_D,
                HOLD_P, HOLD_I, HOLD_D, OFFSET, MAX_SPEED);
    }
     */

}
