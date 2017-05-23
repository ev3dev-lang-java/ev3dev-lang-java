package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;

import java.awt.*;

public class LCDDrawRectanglesTest {

    public static EV3GraphicsLCD EV3GraphicsLCD = new EV3GraphicsLCD();

    public static void main(final String[] args){

        clear();
        EV3GraphicsLCD.setColor(Color.BLACK);
        EV3GraphicsLCD.drawRect(0, 0, 20, 20);
        EV3GraphicsLCD.drawRect(40, 40, 80, 80);
        EV3GraphicsLCD.flush();

    }


    public static void clear(){
        EV3GraphicsLCD.setColor(Color.WHITE);
        EV3GraphicsLCD.fillRect(0,0, EV3GraphicsLCD.SCREEN_WIDTH, EV3GraphicsLCD.SCREEN_HEIGHT);
    }

}
