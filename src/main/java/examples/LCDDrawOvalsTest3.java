package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;

import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;

public class LCDDrawOvalsTest3 {

    public static GraphicsLCD lcd = new EV3GraphicsLCD();

    public static void main(final String[] args){

        clear();

        lcd.setColor(Color.BLACK);

        //  Here we create a GeneralPath object that will contain the
        //  outline points of the shape of a heart. The two topmost "elements"
        //  of this heart are demiballs, created as Arc2D.Double objects.
        //  The zero point of the heart is between the two demiballs.

        GeneralPath heart_shape  =  new  GeneralPath() ;

        heart_shape.moveTo( 0, 0 ) ;
        heart_shape.append( new Arc2D.Double( -100, -50, 100, 100, 0, 180,
                Arc2D.OPEN ), true ) ;
        heart_shape.curveTo( -90, 60, -5, 90, 0, 150 ) ; // lower left side
        heart_shape.curveTo(   5, 90, 90, 60, 100, 0 ) ; // lower right side
        heart_shape.append( new Arc2D.Double(    0, -50, 100, 100, 0, 180,
                Arc2D.OPEN ), true ) ;
        heart_shape.closePath() ;

        double current_drawing_scale  =  1.00 ;

        /*
        lcd.translate( EV3GraphicsLCD.SCREEN_WIDTH / 2, EV3GraphicsLCD.SCREEN_HEIGHT / 5 * 2 ) ;
        lcd.scale( current_drawing_scale, current_drawing_scale ) ;
        lcd.fill( heart_shape ) ;
        */

        lcd.refresh();

    }

    public static void clear(){
        lcd.setColor(lejos.robotics.Color.WHITE);
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());
    }

}
