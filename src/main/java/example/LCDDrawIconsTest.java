package example;

import ev3dev.hardware.display.SystemDisplay;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;
import lejos.utility.Delay;

import java.awt.*;

public class LCDDrawIconsTest {

    public static GraphicsLCD lcd = SystemDisplay.initializeRealLCD();

    public static void main(final String[] args) {

        System.out.println("EV3 LCD Example");

        //clear();
        lcd.clear();
        //lcd.setColor(Color.BLACK);
        lcd.setColor(0, 0, 0);
        lcd.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 70));
        lcd.drawString("\u2665", 50, 50, 0);
        lcd.drawString("\u2661", 70, 70, 0);
        lcd.refresh();

        Delay.msDelay(5000);
    }

    public static void clear() {
        lcd.setColor(Color.WHITE);
        lcd.fillRect(0, 0, lcd.getWidth(), lcd.getHeight());
    }

}
