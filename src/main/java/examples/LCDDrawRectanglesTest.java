package examples;

import ev3dev.actuators.lcd.LCDGraphics;
import lejos.utility.Delay;

import java.awt.*;

public class LCDDrawRectanglesTest {

    public static LCDGraphics lcdGraphics = new LCDGraphics();

    public static void main(final String[] args){

        init();
        clear();
        lcdGraphics.setColor(Color.BLACK);
        lcdGraphics.drawRect(0, 0, 20, 20);
        lcdGraphics.drawRect(40, 40, 80, 80);
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
