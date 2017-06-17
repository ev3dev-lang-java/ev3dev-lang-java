package ev3dev.utils;

import ev3dev.actuators.LCD;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.hardware.EV3DevPlatforms;
import lejos.hardware.lcd.GraphicsLCD;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BrickmanUtils extends EV3DevPlatforms {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Shell.class);

    private static final String DISABLE_BRICKMAN_COMMAND = "sudo systemctl stop brickman";
    private static final String ENABLE_BRICKMAN_COMMAND = "sudo systemctl start brickman";

    private static final String JAVA_LOGO = "java_logo.png";

    public static void disable() {

        if(log.isTraceEnabled())
            log.trace("Disabling Brickman to run a Java process");
        Shell.execute(DISABLE_BRICKMAN_COMMAND);

        showJavaLogo();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                if(log.isTraceEnabled())
                    log.trace("Enabling Brickman again");
                Shell.execute(ENABLE_BRICKMAN_COMMAND);
                JarResource.delete(JAVA_LOGO);
            }
        }));
    }

    private static void showJavaLogo() {

        final Set<EV3DevPlatform> platforms = new HashSet<>();
        platforms.add(EV3DevPlatform.EV3BRICK);
        platforms.add(EV3DevPlatform.PISTORMS);

        final BrickmanUtils obj = new BrickmanUtils();
        if(platforms.contains(obj.getPlatform())) {
            if(log.isDebugEnabled())
                log.debug("Showing Java logo on EV3 Brick");
            final GraphicsLCD lcd = LCD.getInstance();
            try {
                JarResource.export(JAVA_LOGO);
                final Image image = ImageIO.read(new File(JAVA_LOGO));
                lcd.drawImage(image, 40, 10, 0);
                lcd.refresh();
            }catch (IOException e){
                log.error(e.getLocalizedMessage());
            }
        }
    }
}
