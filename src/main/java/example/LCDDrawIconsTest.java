package example;

import ev3dev.actuators.LCD;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;

import java.awt.*;

public class LCDDrawIconsTest {

    public static GraphicsLCD lcd = LCD.getInstance();

    public static void main(final String[] args){

        System.out.println("EV3 LCD Example");

        clear();
        lcd.setColor(Color.BLACK);
        lcd.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 70));
        lcd.drawString("\u2665", 50, 50, 0);
        lcd.drawString("\u2661", 70, 70, 0);
        lcd.refresh();
    }

    public static void clear(){
        lcd.setColor(Color.WHITE);
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());
    }

}
