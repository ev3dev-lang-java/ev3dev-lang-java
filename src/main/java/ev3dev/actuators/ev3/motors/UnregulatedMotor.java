package ev3dev.actuators.ev3.motors;

/**
 * Abstraction for an Lego Mindstorms motors with no speed regulation.
 * http://www.ev3dev.org/docs/motors/
 *
 */
public class UnregulatedMotor extends BasicMotor {

    /**
     * Constructor
     * @param portName port
     */
	public UnregulatedMotor(final String portName) {
		super(portName);
	}

}
