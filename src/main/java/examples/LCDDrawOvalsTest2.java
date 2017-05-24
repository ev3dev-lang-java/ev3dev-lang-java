package examples;

import ev3dev.actuators.lcd.EV3GraphicsLCD;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;

import java.awt.*;

public class LCDDrawOvalsTest2 {

    public static GraphicsLCD lcd = new EV3GraphicsLCD();

    public static void main(final String[] args){

        clear();
        lcd.setColor(Color.BLACK);
        lcd.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 70));
        lcd.drawString("\u2665", 50, 50, 0);
        lcd.drawString("\u2661", 70, 70, 0);
        lcd.refresh();
    }

    public static void clear(){
        lcd.setColor(lejos.robotics.Color.WHITE);
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());
    }

}
