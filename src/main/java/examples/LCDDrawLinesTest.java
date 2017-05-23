package examples;

import ev3dev.actuators.lcd.LCDGraphics;
import lejos.utility.Delay;

import java.awt.*;

public class LCDDrawLinesTest {

    public static LCDGraphics lcdGraphics = new LCDGraphics();

    public static void main(final String[] args){

        clear();
        lcdGraphics.setColor(Color.BLACK);
        lcdGraphics.drawLine(0, 0, 50, 50);
        lcdGraphics.drawLine(0, 0, 30, 60);
        lcdGraphics.flush();

        lcdGraphics.dispose();
    }

    public static void clear(){
        lcdGraphics.setColor(Color.WHITE);
        lcdGraphics.fillRect(0,0, LCDGraphics.SCREEN_WIDTH, LCDGraphics.SCREEN_HEIGHT);
    }

}
