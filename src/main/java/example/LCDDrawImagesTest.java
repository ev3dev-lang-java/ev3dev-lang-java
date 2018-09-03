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

        JarResource.export("cross.gif");
        JarResource.export("nought.gif");

        BufferedImage img = null;
        Image image = ImageIO.read(new File("nought.gif"));
        try {
            img = ImageIO.read(new File("cross.gif"));
        } catch (IOException e) {

        }

    }


    public static void clear() {
        //lcd.setColor(Color.WHITE);
        lcd.setColor(255, 255, 255);
        lcd.fillRect(0, 0, lcd.getWidth(), lcd.getHeight());


    }

}