package ev3dev.sensors.mindsensors;

import ev3dev.sensors.BaseSensor;
import lejos.hardware.port.Port;
import lejos.robotics.geometry.Rectangle2D;
import lejos.robotics.geometry.RectangleInt32;

/**
 * Created by jabrena on 12/8/17.
 */
public class NXTCamV5 extends BaseSensor {

    private static final String MINDSENSORS_NXTCAMV5 = "ms-nxtcam5";

    //MODES

    /**
     * Used by setTrackingMode() to choose object tracking.
     */
    public static final String OBJECT_TRACKING = "TRACK-OBJ";

    /**
     * Used by setTrackingMode() to choose face tracking.
     */
    public static final String FACE_TRACKING = "TRACK-FACE";

    /**
     * Used by setTrackingMode() to choose eye tracking.
     */
    public static final String EYE_TRACKING = "TRACK-EYE";

    /**
     * Used by setTrackingMode() to choose line tracking.
     */
    public static final String LINE_TRACKING = "TRACK-LINE";

    //CAPABILITIES

    /**
     * Used to record a short video
     */
    public static final String TAKE_VIDEO = "MOVIE";

    /**
     * Used to take a snapshot
     */
    public static final String TAKE_PHOTO = "PICTURE";

    public NXTCamV5(Port sensorPort, String mode) {
        super(sensorPort, mode, MINDSENSORS_NXTCAMV5);
    }

    /**
     * Choose either object or line tracking mode.
     * @param mode Use either OBJECT_TRACKING or LINE_TRACKING
     */
    public void setTrackingMode(final String mode) {
        sendCommand(mode);
    }

    /**
     * Send a single byte command represented by a letter
     * @param cmd the letter that identifies the command
     */
    public void sendCommand(final String cmd) {
        this.setStringAttribute("command", cmd);
    }

    /**
     * Create a new video
     */
    public void createVideo(){
        sendCommand(TAKE_VIDEO);
    }

    /**
     * Create a photo
     */
    public void createPhoto(){
        sendCommand(TAKE_PHOTO);
    }

    /**
     * Get the number of objects being tracked
     *
     * @return number of objects (0 - 8)
     */
    public int getNumberOfObjects() {
        return this.getIntegerAttribute("value0");
    }

    /**
     * Get the color number for a tracked object
     *
     * @param id the object number (starting at zero)
     * @return the color of the object (starting at zero)
     */
    public int getObjectColor(int id) {
        return this.getIntegerAttribute("value1");
    }

    /**
     * Get the rectangle containing a tracked object
     *
     * @param id the object number (starting at zero)
     * @return the rectangle
     */
    public Rectangle2D getRectangle(int id) {

        /*
        		getData(0x44 + (id * 5), buf, 4);
		return new RectangleInt32(
		buf[0] & 0xFF, buf[1] & 0xFF,
				(buf[2] & 0xFF) - (buf[0] & 0xFF),
				(buf[3] & 0xFF) - (buf[1] & 0xFF));
         */

        return new RectangleInt32(
                this.getIntegerAttribute("value2"),
                this.getIntegerAttribute("value3"),
                this.getIntegerAttribute("value4") - this.getIntegerAttribute("value2"),
                this.getIntegerAttribute("value5") - this.getIntegerAttribute("value3")
        );
    }

}
