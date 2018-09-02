package example;

import ev3dev.hardware.display.SystemDisplay;
import lejos.hardware.lcd.GraphicsLCD;

import java.awt.*;

public class LCDFontTest {

    public static GraphicsLCD lcd = SystemDisplay.initializeRealLCD();

    public static void main(final String[] args) {

        System.out.println("EV3 LCD Example");

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

        // Get all font family name in a String[]
        String[] fontNames = env.getAvailableFontFamilyNames();
        for (String fontName : fontNames) {
            System.out.println("Font: {}" + fontName);
        }

        // Construct all Font instance (with font size of 1)
        Font[] fonts = env.getAllFonts();
        for (Font font : fonts) {
            System.out.println("Font: {}" + font);
        }

        writeMessage("Hello World");
    }

    public static void writeMessage(final String message) {
        lcd.setColor(lejos.robotics.Color.BLACK);
        lcd.drawString(message, 50, 50, 0);
        lcd.refresh();
    }

    public static void clear() {
        lcd.setColor(lejos.robotics.Color.WHITE);
        lcd.fillRect(0, 0, lcd.getWidth(), lcd.getHeight());
    }
}
