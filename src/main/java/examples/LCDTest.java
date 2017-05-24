package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;

public class LCDTest {

    public static GraphicsLCD lcd = new EV3GraphicsLCD();

    public static void main(final String[] args){

        clear();

        lcd.setColor(Color.BLACK);
        lcd.drawRect(0,0, lcd.getWidth(), lcd.getHeight());
        lcd.fillRect(0,0, EV3GraphicsLCD.SCREEN_WIDTH, EV3GraphicsLCD.SCREEN_HEIGHT);

        lcd.setColor(Color.WHITE);
        lcd.drawString("Hello World", (lcd.getWidth()/ 2)-30, lcd.getHeight()/2, 0);
        lcd.refresh();
    }

    public static void clear(){
        lcd.setColor(lejos.robotics.Color.WHITE);
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());
    }
}