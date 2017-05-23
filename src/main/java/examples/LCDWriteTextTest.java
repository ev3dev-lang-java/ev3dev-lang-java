package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;
import lejos.utility.Delay;

import java.awt.Color;

public class LCDWriteTextTest {

    public static EV3GraphicsLCD EV3GraphicsLCD = new EV3GraphicsLCD();

    public static void main(final String[] args){

        clear();
        writeMessage("Juanito");
        EV3GraphicsLCD.flush();
        Delay.msDelay(1000);
        clear();
        writeMessage("Jorgito");
        EV3GraphicsLCD.flush();
        Delay.msDelay(1000);
        clear();
        writeMessage("Pablito");
        EV3GraphicsLCD.flush();

        EV3GraphicsLCD.dispose();
    }

    public static void writeMessage(final String message){
        EV3GraphicsLCD.setColor(Color.BLACK);
        EV3GraphicsLCD.drawString(message, 50,50);
    }

    public static void clear(){
        EV3GraphicsLCD.setColor(Color.WHITE);
        EV3GraphicsLCD.fillRect(0,0, EV3GraphicsLCD.SCREEN_WIDTH, EV3GraphicsLCD.SCREEN_HEIGHT);
    }
}
