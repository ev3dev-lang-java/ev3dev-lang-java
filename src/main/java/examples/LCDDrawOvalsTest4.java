package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;

public class LCDDrawOvalsTest4 {

    public static GraphicsLCD lcd = new EV3GraphicsLCD();

    public static void main(final String[] args){

        clear();

        lcd.setColor(Color.BLACK);

        /*
        //Loop for animation 'heart' :p ;
        for(int i =  0;i < 100;i++){
            lcd.fillOval(0 + i, 0 + i, 6,35);

            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {}
            lcd.refresh();
        }
        for(int i =  0;i < 100;i++){
            lcd.fillOval(0 + i,100 - i , 6,35);
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {}
            lcd.refresh();
        }
        */

    }

    public static void clear(){
        lcd.setColor(lejos.robotics.Color.WHITE);
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());
    }

}
