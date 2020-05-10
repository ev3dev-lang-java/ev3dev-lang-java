package ev3dev.actuators.lego.motors;

import lejos.hardware.port.Port;

/**
 * Abstraction for an Lego Mindstorms motors with no speed regulation.
 * http://www.ev3dev.org/docs/motors/
 */
public class UnregulatedMotor extends BasicMotor {

    /**
     * Constructor
     *
     * @param portName port
     */
    public UnregulatedMotor(final Port portName) {
        super(portName);
    }

}
