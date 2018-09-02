package ev3dev.hardware.display;

import ev3dev.utils.JarResource;
import ev3dev.utils.Shell;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// intentionally package-local to prevent API misuse
@Slf4j
class Brickman {

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

    public static void drawJavaLogo(Graphics2D gfx) {
        LOGGER.debug("Showing Java logo on EV3 Brick");

        try {
            Rectangle bounds = gfx.getClipBounds();
            final BufferedImage image = ImageIO.read(JarResource.stream(JarResource.JAVA_DUKE_IMAGE_NAME));
            int x = (bounds.width - image.getWidth()) / 2;
            int y = (bounds.height - image.getHeight()) / 2;
            gfx.drawImage(image, x, y, null);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }
}
