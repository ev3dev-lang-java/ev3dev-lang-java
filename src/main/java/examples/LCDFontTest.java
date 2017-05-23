package examples;

import ev3dev.actuators.lcd.LCDGraphics;
import lejos.utility.Delay;

import java.awt.*;

public class LCDFontTest {

    public static LCDGraphics lcdGraphics = new LCDGraphics();

    public static void main(final String[] args){

        clear();

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

        // Get all font family name in a String[]
        String[] fontNames = env.getAvailableFontFamilyNames();
        for (String fontName : fontNames) {
            System.out.println(fontName);
        }

        // Construct all Font instance (with font size of 1)
        Font[] fonts = env.getAllFonts();
        for (Font font : fonts) {
            System.out.println(font);
        }

        writeMessage("Hello World");
        lcdGraphics.dispose();
    }

    public static void writeMessage(final String message){
        lcdGraphics.setColor(Color.BLACK);
        lcdGraphics.drawString(message, 50,50);
    }

    public static void clear(){
        lcdGraphics.setColor(Color.WHITE);
        lcdGraphics.fillRect(0,0, LCDGraphics.SCREEN_WIDTH, LCDGraphics.SCREEN_HEIGHT);
    }
}
