package example;

import ev3dev.actuators.LCD;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class LCDFontTest {

    public static GraphicsLCD lcd = LCD.getInstance();

    public static void main(final String[] args) {

        LOGGER.info("EV3 LCD Example");

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

        // Get all font family name in a String[]
        String[] fontNames = env.getAvailableFontFamilyNames();
        for (String fontName : fontNames) {
            LOGGER.info("Font: {}", fontName);
        }

        // Construct all Font instance (with font size of 1)
        Font[] fonts = env.getAllFonts();
        for (Font font : fonts) {
            LOGGER.info("Font: {}", font);
        }

        writeMessage("Hello World");

        Delay.msDelay(5000);
    }


    public static void writeMessage(final String message){
        //lcd.setColor(lejos.robotics.Color.BLACK);
        lcd.setColor(0,0,0);
        lcd.drawString(message, 50,50, 0);
        lcd.refresh();
    }

    public static void clear(){
        //lcd.setColor(lejos.robotics.Color.WHITE);
        lcd.setColor(255,255,255);
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());

    }
}
