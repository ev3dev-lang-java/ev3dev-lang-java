package ev3dev.hardware.display;

import ev3dev.actuators.LCD;
import ev3dev.utils.JarResource;
import ev3dev.utils.Shell;
import lejos.hardware.lcd.GraphicsLCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

// intentionally package-local to prevent API misuse
class Brickman {
    private static final Logger LOGGER = LoggerFactory.getLogger(Shell.class);

    private static final String DISABLE_BRICKMAN_COMMAND = "sudo systemctl stop brickman";
    private static final String ENABLE_BRICKMAN_COMMAND = "sudo systemctl start brickman";

    static void disable() {
        LOGGER.trace("Disabling Brickman service");

        Shell.execute(DISABLE_BRICKMAN_COMMAND);

        Runtime.getRuntime().addShutdownHook(new Thread(Brickman::restoreBrickman, "restore brickman"));
    }

    private static void restoreBrickman() {
        LOGGER.trace("Enabling Brickman service");
        Shell.execute(ENABLE_BRICKMAN_COMMAND);
    }

    public static void showJavaLogo() {
        LOGGER.debug("Showing Java logo on EV3 Brick");

        final GraphicsLCD lcd = LCD.getInstance();
        try {
            final Image image = ImageIO.read(JarResource.stream(JarResource.JAVA_DUKE_IMAGE_NAME));
            lcd.drawImage(image, 40, 10, 0);
            lcd.refresh();
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }
}
