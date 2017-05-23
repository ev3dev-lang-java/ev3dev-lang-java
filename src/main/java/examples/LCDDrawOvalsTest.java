package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;
import lejos.utility.Delay;

import java.awt.*;

public class LCDDrawOvalsTest {

    public static EV3GraphicsLCD EV3GraphicsLCD = new EV3GraphicsLCD();

    public static void main(final String[] args){

        clear();

        int threshold = 20;

        int y = 0;
        int dy = 0;

        for(int x = 0; x<= threshold; x++) {

            // to make the ball act upon collision
            /*
            if(x<0) dx=1;
            if(x>=getWidth()-45)
            dx=-1;
            */

            if(y<0) dy=1;
            if(y>= EV3GraphicsLCD.SCREEN_HEIGHT-45) dy=-1;

            // x+=dx; //to make the ball move
            y+=dy;

            //clear();
            EV3GraphicsLCD.setColor(Color.BLACK);
            EV3GraphicsLCD.fillOval(x, y,5, 5);
            EV3GraphicsLCD.flush();
            Delay.msDelay(500);
        }

    }

    public static void clear(){
        EV3GraphicsLCD.setColor(Color.WHITE);
        EV3GraphicsLCD.fillRect(0,0, EV3GraphicsLCD.SCREEN_WIDTH, EV3GraphicsLCD.SCREEN_HEIGHT);
    }

}
