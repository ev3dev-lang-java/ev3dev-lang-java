package examples;

import ev3dev.actuators.lcd.LCDGraphics;
import lejos.utility.Delay;

import java.awt.Color;

public class LCDWriteTextTest {

    public static LCDGraphics lcdGraphics = new LCDGraphics();

    public static void main(final String[] args){

        init();
        clear();
        writeMessage("Hello World");
        lcdGraphics.dispose();

        Delay.msDelay(10000);
    }

    public static void writeMessage(final String message){
        lcdGraphics.setColor(Color.BLACK);
        lcdGraphics.drawString(message, 0,LCDGraphics.SCREEN_HEIGHT/2);
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
