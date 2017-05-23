package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;

import java.awt.*;

public class LCDDrawLinesTest {

    public static EV3GraphicsLCD EV3GraphicsLCD = new EV3GraphicsLCD();

    public static void main(final String[] args){

        clear();
        EV3GraphicsLCD.setColor(Color.BLACK);
        EV3GraphicsLCD.drawLine(0, 0, 50, 50);
        EV3GraphicsLCD.drawLine(0, 0, 30, 60);
        EV3GraphicsLCD.flush();

        EV3GraphicsLCD.dispose();
    }

    public static void clear(){
        EV3GraphicsLCD.setColor(Color.WHITE);
        EV3GraphicsLCD.fillRect(0,0, EV3GraphicsLCD.SCREEN_WIDTH, EV3GraphicsLCD.SCREEN_HEIGHT);
    }

}
