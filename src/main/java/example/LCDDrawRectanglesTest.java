package example;

import ev3dev.hardware.display.SystemDisplay;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LCDDrawRectanglesTest {

    public static GraphicsLCD lcd = SystemDisplay.initializeRealLCD();

    public static void main(final String[] args) {

        LOGGER.info("EV3 LCD Example");

        clear();
        //lcd.setColor(Color.BLACK);
        lcd.setColor(0,0,0);
        lcd.drawRect(0, 0, 20, 20);
        lcd.drawRect(40, 40, 80, 80);
        lcd.refresh();

        Delay.msDelay(5000);
    }


    public static void clear(){
        //lcd.setColor(Color.WHITE);
        lcd.setColor(255,255,255);
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());
    }

}
