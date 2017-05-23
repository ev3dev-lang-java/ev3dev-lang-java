package examples;

import ev3dev.actuators.lcd.LCDGraphics;
import ev3dev.utils.JarResource;
import lejos.utility.Delay;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LCDDrawImagesTest {

    public static LCDGraphics lcdGraphics = new LCDGraphics();

    public static void main(final String[] args) throws IOException {

        init();
        clear();

        JarResource.export("/" + "cross.gif");
        JarResource.export("/" + "nought.gif");

        lcdGraphics.setColor(Color.BLACK);

        BufferedImage img = null;
        Image image = ImageIO.read(new File("cross.gif"));
        try {
            img = ImageIO.read(new File("cross.gif"));
        } catch (IOException e) {

        }

        lcdGraphics.drawImage(img, new AffineTransform(1f, 0f, 0f, 1f, 0, 0), null);
        lcdGraphics.drawImage(image, 0,0, null);
        lcdGraphics.drawImage(image,0,0,Color.BLACK,null);
        lcdGraphics.drawImage(img, 0,0, Color.BLACK, null);
        lcdGraphics.drawImage(img, 0,0,null);

        lcdGraphics.dispose();

        Delay.msDelay(10000);
    }

    public static void init(){
        lcdGraphics.setBackground(Color.WHITE);
        //lcdGraphics.setPaint(Color.WHITE);
    }

    public static void clear(){
        //lcdGraphics.drawRect(0,0, LCDGraphics.SCREEN_WIDTH, LCDGraphics.SCREEN_HEIGHT);
        lcdGraphics.fillRect(0,0, LCDGraphics.SCREEN_WIDTH, LCDGraphics.SCREEN_HEIGHT);
        lcdGraphics.dispose();
    }

}
