package example;

import ev3dev.actuators.LCD;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;

public class LCDDrawLinesTest {

    public static GraphicsLCD lcd = LCD.getInstance();

    public static void main(final String[] args){

        System.out.println("EV3 LCD Example");

        clear();
        lcd.setColor(Color.BLACK);
        lcd.drawLine(0, 0, 50, 50);
        lcd.drawLine(0, 0, 30, 60);
        lcd.refresh();
    }

    public static void clear(){
        lcd.setColor(Color.WHITE);
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());
    }

}
