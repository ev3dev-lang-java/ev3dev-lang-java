package examples;

import ev3dev.actuators.lcd.LCDGraphics;
import lejos.utility.Delay;

import java.awt.Color;

public class LCDWriteTextTest {

    public static LCDGraphics lcdGraphics = new LCDGraphics();

    public static void main(final String[] args){

        clear();
        writeMessage("Juanito");
        lcdGraphics.flush();
        Delay.msDelay(1000);
        clear();
        writeMessage("Jorgito");
        lcdGraphics.flush();
        Delay.msDelay(1000);
        clear();
        writeMessage("Pablito");
        lcdGraphics.flush();

        lcdGraphics.dispose();
    }

    public static void writeMessage(final String message){
        lcdGraphics.setColor(Color.BLACK);
        lcdGraphics.drawString(message, 50,50);
    }

    public static void clear(){
        lcdGraphics.setColor(Color.WHITE);
        lcdGraphics.fillRect(0,0, LCDGraphics.SCREEN_WIDTH, LCDGraphics.SCREEN_HEIGHT);
    }
}
