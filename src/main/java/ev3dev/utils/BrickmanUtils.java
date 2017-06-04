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

public class BrickmanUtils extends EV3DevPlatforms {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Shell.class);

    private static final String DISABLE_BRICKMAN_COMMAND = "systemctl stop brickman";
    private static final String ENABLE_BRICKMAN_COMMAND = "systemctl stop brickman";

    private static final String JAVA_LOGO = "java_icon100_100_color.png";

    public static void disable(){

        log.trace("Disabling Brickman to run a Java process");
        Shell.execute(DISABLE_BRICKMAN_COMMAND);

        showJavaLogo();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                log.trace("Enabling Brickman again");
                Shell.execute(ENABLE_BRICKMAN_COMMAND);
                JarResource.delete(JAVA_LOGO);
            }
        }));
    }

    private static void showJavaLogo() {

        final BrickmanUtils obj = new BrickmanUtils();
        if(obj.getPlatform().equals(EV3DevPlatform.EV3BRICK)){
            log.debug("Showing Java logo on EV3 Brick");
            final GraphicsLCD lcd = LCD.getInstance();
            try {
                JarResource.export(JAVA_LOGO);
                final Image image = ImageIO.read(new File(JAVA_LOGO));
                lcd.drawImage(image, 0, 0, 0);
                lcd.refresh();
            }catch (IOException e){
                log.error(e.getLocalizedMessage());
            }
        }
    }
}
