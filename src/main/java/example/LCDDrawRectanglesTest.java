package example;

import ev3dev.hardware.display.SystemDisplay;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;

public class LCDDrawRectanglesTest {

    public static GraphicsLCD lcd = SystemDisplay.initializeRealLCD();

    public static void main(final String[] args) {

        System.out.println("EV3 LCD Example");

        clear();
        lcd.setColor(Color.BLACK);
        lcd.drawRect(0, 0, 20, 20);
        lcd.drawRect(40, 40, 80, 80);
        lcd.refresh();
    }

    public static void clear() {
        lcd.setColor(Color.WHITE);
        lcd.fillRect(0, 0, lcd.getWidth(), lcd.getHeight());
    }

}
