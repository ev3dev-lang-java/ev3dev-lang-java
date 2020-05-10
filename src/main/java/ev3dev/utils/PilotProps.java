package ev3dev.utils;

import ev3dev.actuators.lego.motors.Motor;
import lejos.robotics.RegulatedMotor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration class for Differential Pilot.
 *
 * @author Lawrie Griffiths, Juan Antonio Breña Moral
 */
public class PilotProps extends Properties {

    public static final String PERSISTENT_FILENAME = "pilot.props";

    public static final String KEY_WHEELDIAMETER = "wheelDiameter";
    public static final String KEY_TRACKWIDTH = "trackWidth";
    public static final String KEY_LEFTMOTOR = "leftMotor";
    public static final String KEY_RIGHTMOTOR = "rightMotor";
    public static final String KEY_REVERSE = "reverse";

    /**
     * Method to load values from file
     *
     * @throws IOException Not found
     */
    public void loadPersistentValues() throws IOException {

        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream resourceStream = loader.getResourceAsStream(PERSISTENT_FILENAME)) {
            this.load(resourceStream);
        }
    }

    /**
     * Store values in the file
     *
     * @throws IOException Not found
     */
    public void storePersistentValues() throws IOException {

        try (FileOutputStream fos = new FileOutputStream(new File(PERSISTENT_FILENAME))) {
            this.store(fos, null);
        }
    }

    /**
     * Utility method to get Motor instance from string (A, B or C)
     */
    public static RegulatedMotor getMotor(String motor) {

        if (motor.equals("A")) {
            return Motor.A;
        } else if (motor.equals("B")) {
            return Motor.B;
        } else if (motor.equals("C")) {
            return Motor.C;
        } else if (motor.equals("D")) {
            return Motor.D;
        } else {
            //TODO Review this case.
            return null;
        }
    }

}
