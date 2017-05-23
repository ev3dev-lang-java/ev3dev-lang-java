package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;

import java.awt.*;

public class LCDFontTest {

    public static EV3GraphicsLCD EV3GraphicsLCD = new EV3GraphicsLCD();

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
        EV3GraphicsLCD.dispose();
    }

    public static void writeMessage(final String message){
        EV3GraphicsLCD.setColor(Color.BLACK);
        EV3GraphicsLCD.drawString(message, 50,50);
    }

    public static void clear(){
        EV3GraphicsLCD.setColor(Color.WHITE);
        EV3GraphicsLCD.fillRect(0,0, EV3GraphicsLCD.SCREEN_WIDTH, EV3GraphicsLCD.SCREEN_HEIGHT);
    }
}
