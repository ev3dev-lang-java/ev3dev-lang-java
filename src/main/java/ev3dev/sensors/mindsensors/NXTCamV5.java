package ev3dev.sensors.mindsensors;

import ev3dev.sensors.BaseSensor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.geometry.Rectangle2D;
import lejos.robotics.geometry.RectangleInt32;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jabrena on 12/8/17.
 */
public class NXTCamV5 extends BaseSensor {

    private static final String MINDSENSORS_NXTCAMV5 = "ms-nxtcam5 0x01";

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

    private final Set<String> trackingAllowedModeList = new HashSet<>(
            Arrays.asList(OBJECT_TRACKING, FACE_TRACKING, EYE_TRACKING, LINE_TRACKING));

    //CAPABILITIES

    /**
     * Used to record a short video
     */
    public static final String TAKE_VIDEO = "MOVIE";

    /**
     * Used to take a snapshot
     */
    public static final String TAKE_PHOTO = "PICTURE";

    private void initModes() {
        this.setStringAttribute("mode", "TRACK");
        setModes(new SensorMode[]{});
    }

    public NXTCamV5(final Port portName) {
        super(portName, LEGO_I2C, MINDSENSORS_NXTCAMV5);
        this.initModes();
    }

    /**
     * Choose either object or line tracking mode.
     * @param mode Use either OBJECT_TRACKING or LINE_TRACKING
     */
    public void setTrackingMode(final String mode) {
        if (trackingAllowedModeList.contains(mode)) {
            sendCommand(mode);
        } else {
            throw new RuntimeException("Tracking mode not allowed: " + mode);
        }
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
    public void createVideo() {
        sendCommand(TAKE_VIDEO);
    }

    /**
     * Create a photo
     */
    public void createPhoto() {
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
    public Rectangle2D getRectangle(final int id) {

        //TODO, At the moment, the EV3Dev API only returns the first object.
        //It is necessary to research the I2C register to get more rectangles
        return new RectangleInt32(
                this.getIntegerAttribute("value2"),
                this.getIntegerAttribute("value3"),
                this.getIntegerAttribute("value4") - this.getIntegerAttribute("value2"),
                this.getIntegerAttribute("value5") - this.getIntegerAttribute("value3")
        );
    }

}
