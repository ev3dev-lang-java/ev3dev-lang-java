package ev3dev.hardware.actuators.motors;

/**
 * Abstraction for an Lego Mindstorms motors with no speed regulation.
 * http://www.ev3dev.org/docs/motors/
 *
 */
public class UnregulatedMotor extends BasicMotor {

    /**
     * Constructor
     * @param portName
     */
	public UnregulatedMotor(final String portName) {
		super(portName);
	}

}
