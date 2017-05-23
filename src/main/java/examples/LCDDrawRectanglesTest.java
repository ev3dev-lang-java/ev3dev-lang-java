package examples;

import ev3dev.actuators.lcd.LCDGraphics;
import lejos.utility.Delay;

import java.awt.*;

public class LCDDrawRectanglesTest {

    public static LCDGraphics lcdGraphics = new LCDGraphics();

    public static void main(final String[] args){

        clear();
        lcdGraphics.setColor(Color.BLACK);
        lcdGraphics.drawRect(0, 0, 20, 20);
        lcdGraphics.drawRect(40, 40, 80, 80);
        lcdGraphics.flush();

    }


    public static void clear(){
        lcdGraphics.setColor(Color.WHITE);
        lcdGraphics.fillRect(0,0, LCDGraphics.SCREEN_WIDTH, LCDGraphics.SCREEN_HEIGHT);
    }

}
