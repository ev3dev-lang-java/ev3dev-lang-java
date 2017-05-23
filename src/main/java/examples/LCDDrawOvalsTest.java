package examples;

import ev3dev.actuators.lcd.LCDGraphics;
import lejos.utility.Delay;

import java.awt.*;

public class LCDDrawOvalsTest {

    public static LCDGraphics lcdGraphics = new LCDGraphics();

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
            if(y>=LCDGraphics.SCREEN_HEIGHT-45) dy=-1;

            // x+=dx; //to make the ball move
            y+=dy;

            //clear();
            lcdGraphics.setColor(Color.BLACK);
            lcdGraphics.fillOval(x, y,5, 5);
            lcdGraphics.flush();
            Delay.msDelay(500);
        }

    }

    public static void clear(){
        lcdGraphics.setColor(Color.WHITE);
        lcdGraphics.fillRect(0,0, LCDGraphics.SCREEN_WIDTH, LCDGraphics.SCREEN_HEIGHT);
    }

}
