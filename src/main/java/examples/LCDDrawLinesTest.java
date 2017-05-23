package examples;

import ev3dev.actuators.lcd.LCDGraphics;
import lejos.utility.Delay;

import java.awt.*;

public class LCDDrawLinesTest {

    public static LCDGraphics lcdGraphics = new LCDGraphics();

    public static void main(final String[] args){

        init();
        clear();
        lcdGraphics.setColor(Color.BLACK);
        lcdGraphics.drawLine(0, 0, 50, 50);
        lcdGraphics.drawLine(20, 20, 70, 70);
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
