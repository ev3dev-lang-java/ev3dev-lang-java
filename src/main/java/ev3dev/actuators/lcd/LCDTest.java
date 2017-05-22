package ev3dev.actuators.lcd;

import lejos.utility.Delay;

import java.awt.*;

public class LCDTest {

    public static void main(final String[] args){

        final LCD2 lcd = new LCD2();
        LCDGraphics lcdGraphics = new LCDGraphics(lcd);
        lcdGraphics.setBackground(Color.WHITE);
        lcdGraphics.setPaint(Color.WHITE);

        //lcdGraphics.drawLine(0,0,50,50);
        lcdGraphics.drawRect(0,0,LCD2.SCREEN_WIDTH,LCD2.SCREEN_HEIGHT);
        lcdGraphics.fillRect(0,0,LCD2.SCREEN_WIDTH,LCD2.SCREEN_HEIGHT);

        lcdGraphics.setColor(Color.BLACK);
        lcdGraphics.drawString("Hello World", 50,50);
        lcdGraphics.drawLine(0,0,50,50);
        lcdGraphics.rotate(45);
        lcdGraphics.drawLine(10,10,50,50);
        lcdGraphics.create();
        lcdGraphics.finalize();
        lcdGraphics.dispose();
        Delay.msDelay(10000);
    }
}
