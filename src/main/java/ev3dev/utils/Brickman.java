package ev3dev.utils;

import lombok.extern.slf4j.Slf4j;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
public class Brickman {

    private static final String DISABLE_BRICKMAN_COMMAND = "sudo systemctl stop brickman";
    private static final String ENABLE_BRICKMAN_COMMAND = "sudo systemctl start brickman";

    /**
     * Disable Brickman.
     */
    public static void disable() {
        LOGGER.trace("Disabling Brickman service");

        Shell.execute(DISABLE_BRICKMAN_COMMAND);

        Runtime.getRuntime().addShutdownHook(new Thread(Brickman::restoreBrickman, "restore brickman"));
    }

    /**
     * Enable Brickman.
     */
    private static void restoreBrickman() {
        LOGGER.trace("Enabling Brickman service");

        Shell.execute(ENABLE_BRICKMAN_COMMAND);
    }

    /**
     * Draw Duke.
     * @param gfx Required context to draw an image.
     */
    public static void drawJavaLogo(Graphics2D gfx) {
        LOGGER.debug("Showing Java logo on EV3 Brick");

        try {
            Rectangle bounds = gfx.getClipBounds();
            final BufferedImage image = JarResource.loadImage(JarResource.JAVA_DUKE_IMAGE_NAME);
            int x = (bounds.width - image.getWidth()) / 2;
            int y = (bounds.height - image.getHeight()) / 2;
            gfx.drawImage(image, x, y, null);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }
}
