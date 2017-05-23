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

        clear();

        JarResource.export("/" + "cross.gif");
        JarResource.export("/" + "nought.gif");

        BufferedImage img = null;
        Image image = ImageIO.read(new File("nought.gif"));
        try {
            img = ImageIO.read(new File("cross.gif"));
        } catch (IOException e) {

        }

        lcdGraphics.setColor(Color.BLACK);
        //lcdGraphics.drawImage(img, new AffineTransform(1f, 0f, 0f, 1f, 0, 0), null);
        //lcdGraphics.drawImage(image, 40,40, null);
        lcdGraphics.drawImage(image,40,40,Color.WHITE,null);
        //lcdGraphics.drawImage(img, 0,0, Color.WHITE, null);
        lcdGraphics.drawImage(img, 0,0,null);

        lcdGraphics.flush();

        JarResource.delete("cross.gif");
        JarResource.delete("nought.gif");

    }

    public static void clear(){
        lcdGraphics.setColor(Color.WHITE);
        lcdGraphics.fillRect(0,0, LCDGraphics.SCREEN_WIDTH, LCDGraphics.SCREEN_HEIGHT);
    }

}
