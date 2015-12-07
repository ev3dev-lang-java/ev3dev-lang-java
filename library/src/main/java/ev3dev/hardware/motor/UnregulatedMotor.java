package ev3dev.hardware.motor;

/**
 * Abstraction for an Lego Mindstorms motors with no speed regulation.
 * http://www.ev3dev.org/docs/motors/
 */
public class UnregulatedMotor extends BasicMotor {

	public UnregulatedMotor(String portName) {
		super(portName);
	}

}
