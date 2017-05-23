package examples;

import ev3dev.actuators.lcd.LCDGraphics;
import lejos.utility.Delay;

import java.awt.*;

public class LCDTest {

    public static void main(final String[] args){

        LCDGraphics lcdGraphics = new LCDGraphics();
        //lcdGraphics.setBackground(Color.WHITE);
        //lcdGraphics.setPaint(Color.WHITE);

        lcdGraphics.setColor(Color.BLACK);
        lcdGraphics.drawRect(0,0,LCDGraphics.SCREEN_WIDTH,LCDGraphics.SCREEN_HEIGHT);
        lcdGraphics.fillRect(0,0,LCDGraphics.SCREEN_WIDTH,LCDGraphics.SCREEN_HEIGHT);

        lcdGraphics.setColor(Color.WHITE);
        lcdGraphics.drawString("Hello World", (LCDGraphics.SCREEN_WIDTH/2)-30,LCDGraphics.SCREEN_HEIGHT/2);

        lcdGraphics.dispose();
        //Delay.msDelay(10000);
    }
}