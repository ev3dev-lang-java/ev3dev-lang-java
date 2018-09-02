package example;

import ev3dev.hardware.display.SystemDisplay;
import ev3dev.utils.JarResource;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;

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

        JarResource.export("cross.gif");
        JarResource.export("nought.gif");

        BufferedImage img = null;
        Image image = ImageIO.read(new File("nought.gif"));
        try {
            img = ImageIO.read(new File("cross.gif"));
        } catch (IOException e) {

        }

        lcd.setColor(Color.BLACK);
        //LCD.drawImage(img, new AffineTransform(1f, 0f, 0f, 1f, 0, 0), null);
        //LCD.drawImage(image, 40,40, null);
        lcd.drawImage(image, 40, 40, 0);
        //LCD.drawImage(img, 0,0, Color.WHITE, null);
        lcd.drawImage(img, 0, 0, 0);

        lcd.refresh();

        JarResource.delete("cross.gif");
        JarResource.delete("nought.gif");

    }

    public static void clear() {
        lcd.setColor(Color.WHITE);
        lcd.fillRect(0, 0, lcd.getWidth(), lcd.getHeight());
    }

}
