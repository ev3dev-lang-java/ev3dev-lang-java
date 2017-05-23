package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;

import java.awt.*;

public class LCDTest {

    public static void main(final String[] args){

        EV3GraphicsLCD EV3GraphicsLCD = new EV3GraphicsLCD();
        //EV3GraphicsLCD.setBackground(Color.WHITE);
        //EV3GraphicsLCD.setPaint(Color.WHITE);

        EV3GraphicsLCD.setColor(Color.BLACK);
        EV3GraphicsLCD.drawRect(0,0, EV3GraphicsLCD.SCREEN_WIDTH, EV3GraphicsLCD.SCREEN_HEIGHT);
        EV3GraphicsLCD.fillRect(0,0, EV3GraphicsLCD.SCREEN_WIDTH, EV3GraphicsLCD.SCREEN_HEIGHT);

        EV3GraphicsLCD.setColor(Color.WHITE);
        EV3GraphicsLCD.drawString("Hello World", (EV3GraphicsLCD.SCREEN_WIDTH/2)-30, EV3GraphicsLCD.SCREEN_HEIGHT/2);

        EV3GraphicsLCD.dispose();
        //Delay.msDelay(10000);
    }
}