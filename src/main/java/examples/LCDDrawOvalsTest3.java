package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;

public class LCDDrawOvalsTest3 {

    public static EV3GraphicsLCD EV3GraphicsLCD = new EV3GraphicsLCD();

    public static void main(final String[] args){

        clear();

        EV3GraphicsLCD.setColor(Color.BLACK);

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

        EV3GraphicsLCD.translate( EV3GraphicsLCD.SCREEN_WIDTH / 2, EV3GraphicsLCD.SCREEN_HEIGHT / 5 * 2 ) ;
        EV3GraphicsLCD.scale( current_drawing_scale, current_drawing_scale ) ;
        EV3GraphicsLCD.fill( heart_shape ) ;


        EV3GraphicsLCD.flush();

    }

    public static void clear(){
        EV3GraphicsLCD.setColor(Color.WHITE);
        EV3GraphicsLCD.fillRect(0,0, EV3GraphicsLCD.SCREEN_WIDTH, EV3GraphicsLCD.SCREEN_HEIGHT);
    }

}
