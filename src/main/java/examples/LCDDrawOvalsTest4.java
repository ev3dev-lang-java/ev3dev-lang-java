package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;

public class LCDDrawOvalsTest4 {

    public static EV3GraphicsLCD EV3GraphicsLCD = new EV3GraphicsLCD();

    public static void main(final String[] args){

        clear();

        EV3GraphicsLCD.setColor(Color.BLACK);

        //Loop for animation 'heart' :p ;
        for(int i =  0;i < 100;i++){
            //EV3GraphicsLCD.setColor(Color.red);
            EV3GraphicsLCD.fillOval(0 + i, 0 + i, 6,35);

            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                //Logger.getLogger(Heart.class.getName()).log(Level.SEVERE, null, ex);
            }
            EV3GraphicsLCD.flush();
        }
        for(int i =  0;i < 100;i++){
            //EV3GraphicsLCD.setColor(Color.red);
            EV3GraphicsLCD.fillOval(0 + i,100 - i , 6,35);
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                //Logger.getLogger(Heart.class.getName()).log(Level.SEVERE, null, ex);
            }
            EV3GraphicsLCD.flush();
        }



    }

    public static void clear(){
        EV3GraphicsLCD.setColor(Color.WHITE);
        EV3GraphicsLCD.fillRect(0,0, EV3GraphicsLCD.SCREEN_WIDTH, EV3GraphicsLCD.SCREEN_HEIGHT);
    }

}
