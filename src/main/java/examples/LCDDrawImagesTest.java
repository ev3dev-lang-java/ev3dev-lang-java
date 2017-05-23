package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;
import ev3dev.utils.JarResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LCDDrawImagesTest {

    public static EV3GraphicsLCD EV3GraphicsLCD = new EV3GraphicsLCD();

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

        EV3GraphicsLCD.setColor(Color.BLACK);
        //EV3GraphicsLCD.drawImage(img, new AffineTransform(1f, 0f, 0f, 1f, 0, 0), null);
        //EV3GraphicsLCD.drawImage(image, 40,40, null);
        EV3GraphicsLCD.drawImage(image,40,40,Color.WHITE,null);
        //EV3GraphicsLCD.drawImage(img, 0,0, Color.WHITE, null);
        EV3GraphicsLCD.drawImage(img, 0,0,null);

        EV3GraphicsLCD.flush();

        JarResource.delete("cross.gif");
        JarResource.delete("nought.gif");

    }

    public static void clear(){
        EV3GraphicsLCD.setColor(Color.WHITE);
        EV3GraphicsLCD.fillRect(0,0, EV3GraphicsLCD.SCREEN_WIDTH, EV3GraphicsLCD.SCREEN_HEIGHT);
    }

}
