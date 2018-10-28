package example;

import ev3dev.actuators.LCD;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LCDTest {

    public static GraphicsLCD lcd = LCD.getInstance();

    public static void main(final String[] args) {

        LOGGER.info("EV3 LCD Example");


        //lcd.setColor(Color.BLACK);
        lcd.setColor(0,0,0);
        lcd.drawRect(0,0, lcd.getWidth(), lcd.getHeight());
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());

        //lcd.setColor(Color.WHITE);
        lcd.setColor(255,255,255);
        lcd.drawString("Hello World", (lcd.getWidth()/ 2)-30, lcd.getHeight()/2, 0);

        lcd.refresh();

        Delay.msDelay(5000);
    }


    public static void clear(){
        //lcd.setColor(Color.WHITE);
        lcd.setColor(255,255,255);
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());

    }
}
