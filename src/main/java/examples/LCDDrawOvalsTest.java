package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class LCDDrawOvalsTest {

    public static GraphicsLCD lcd = new EV3GraphicsLCD();

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
            if(y>= lcd.getHeight()-45) dy=-1;

            // x+=dx; //to make the ball move
            y+=dy;

            //clear();
            lcd.setColor(Color.BLACK);
            //lcd.fillOval(x, y,5, 5);
            lcd.refresh();
            Delay.msDelay(500);
        }

    }

    public static void clear(){
        lcd.setColor(lejos.robotics.Color.WHITE);
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());
    }

}
