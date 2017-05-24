package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class LCDWriteTextTest {

    public static GraphicsLCD lcd = new EV3GraphicsLCD();

    public static void main(final String[] args){

        clear();
        writeMessage("Juanito");
        Delay.msDelay(1000);
        clear();
        writeMessage("Jorgito");
        Delay.msDelay(1000);
        clear();
        writeMessage("Pablito");
    }

    public static void writeMessage(final String message){
        lcd.setColor(Color.BLACK);
        lcd.drawString(message, 50,50, 0);
        lcd.refresh();
    }

    public static void clear(){
        lcd.setColor(Color.WHITE);
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());
    }
}
