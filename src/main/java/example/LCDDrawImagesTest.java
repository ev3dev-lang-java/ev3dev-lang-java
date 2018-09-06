package example;

import ev3dev.hardware.display.SystemDisplay;
import ev3dev.utils.JarResource;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;
import lejos.utility.Delay;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LCDDrawImagesTest {

    public static GraphicsLCD lcd = SystemDisplay.initializeRealLCD();

    public static void main(final String[] args) throws IOException {

        System.out.println("EV3 LCD Example");

        clear();

        BufferedImage cross  = ImageIO.read(JarResource.stream("cross.gif"));
        BufferedImage nought = ImageIO.read(JarResource.stream("nought.gif"));

        //lcd.setColor(Color.BLACK);
        lcd.setColor(0,0,0);
        //LCD.drawImage(img, new AffineTransform(1f, 0f, 0f, 1f, 0, 0), null);
        //LCD.drawImage(image, 40,40, null);
        lcd.drawImage(cross,40,40,0);
        //LCD.drawImage(img, 0,0, Color.WHITE, null);
        lcd.drawImage(nought, 0,0,0);

        lcd.refresh();

        Delay.msDelay(5000);

    }


    public static void clear() {
        //lcd.setColor(Color.WHITE);
        lcd.setColor(255, 255, 255);
        lcd.fillRect(0, 0, lcd.getWidth(), lcd.getHeight());
    }

}
