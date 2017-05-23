package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;
import lejos.utility.Delay;

import java.awt.*;

public class LCDDrawOvalsTest2 {

    public static EV3GraphicsLCD EV3GraphicsLCD = new EV3GraphicsLCD();

    public static void main(final String[] args){

        clear();

        EV3GraphicsLCD.setColor(Color.BLACK);
        //EV3GraphicsLCD.fillOval(25, 25, 50, 50);

        EV3GraphicsLCD.setColor(Color.BLACK);
        EV3GraphicsLCD.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 70));
        EV3GraphicsLCD.drawString("\u2665", 50, 50);
        EV3GraphicsLCD.drawString("\u2661", 70, 70);

        EV3GraphicsLCD.flush();

    }

    public static void clear(){
        EV3GraphicsLCD.setColor(Color.WHITE);
        EV3GraphicsLCD.fillRect(0,0, EV3GraphicsLCD.SCREEN_WIDTH, EV3GraphicsLCD.SCREEN_HEIGHT);
    }

}
