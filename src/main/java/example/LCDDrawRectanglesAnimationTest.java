package example;

import ev3dev.actuators.LCD;
import ev3dev.utils.JarResource;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
public class LCDDrawRectanglesAnimationTest {

    public static GraphicsLCD lcd = LCD.getInstance();

    public static void main(final String[] args) {

        LOGGER.info("EV3 LCD Example");

        showJavaLogo();
        Delay.msDelay(5000);

        clear();

        sin();

        Delay.msDelay(5000);
    }

    public static final String JAVA_DUKE_IMAGE_NAME = "java_logo.png";

    private static void showJavaLogo() {

        if(LOGGER.isDebugEnabled())
            LOGGER.debug("Showing Java logo on EV3 Brick");

        try {
            BufferedImage image  = JarResource.loadImage(JAVA_DUKE_IMAGE_NAME);
            lcd.drawImage(image, 35, 10, 0);
            lcd.refresh();
        }catch (IOException e){
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    public static void sin() {

        for ( int i = 0; i < lcd.getWidth(); i++ ) {
            int x = i;
            int y = 50 + (int) (Math.sin(i) * 5);

            clear();
            //lcd.setColor(Color.BLACK);
            lcd.setColor(0,0,0);
            lcd.fillRect(x, y, 20, 20);
            lcd.refresh();
            Delay.msDelay(10);
        }

    }

    public static void clear(){
        //lcd.setColor(Color.WHITE);
        lcd.setColor(255,255,255);
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());
    }

}
