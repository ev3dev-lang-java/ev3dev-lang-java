package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;

public class LCDDrawRectanglesTest {

    public static GraphicsLCD lcd = new EV3GraphicsLCD();

    public static void main(final String[] args){

        clear();
        lcd.setColor(Color.BLACK);
        lcd.drawRect(0, 0, 20, 20);
        lcd.drawRect(40, 40, 80, 80);
        lcd.refresh();

    }

    public static void clear(){
        lcd.setColor(lejos.robotics.Color.WHITE);
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());
    }

}
