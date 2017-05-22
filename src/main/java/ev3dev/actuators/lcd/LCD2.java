package ev3dev.actuators.lcd;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Provides an interface to draw to the EV3's LCD
 * @author Anthony
 *
 */
public class LCD2 {

    public static final String FB_PATH = "/dev/fb0";

    //This should not be hard-coded, however just for testing
    public static final int SCREEN_WIDTH = 178;

    public static final int SCREEN_HEIGHT = 128;

    public LCD2() {

    }

    /**
     * Draws a byte array into the EV3 framebuffer
     * @param data Byte array to be drawn (128 (height) * 178 / 8 (length) = 3072 bytes)
     */
    public void draw(byte[] data) {
        File file = new File(FB_PATH);
        if (!file.exists()){
            throw new IllegalArgumentException("The framebuffer device does not exist! Are you using a EV3?");
        }
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            out.write(data);
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to draw the LCD", e);
        }
    }

}
