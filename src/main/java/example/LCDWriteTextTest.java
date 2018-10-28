package example;

import ev3dev.actuators.LCD;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LCDWriteTextTest {

    public static GraphicsLCD lcd = LCD.getInstance();

    public static void main(final String[] args) {
        LOGGER.info("EV3 LCD Example");

        clear();
        writeMessage("Juanito");
        Delay.msDelay(5000);
        clear();
        writeMessage("Jorgito");
        Delay.msDelay(5000);
        clear();
        writeMessage("Pablito");
        Delay.msDelay(5000);
    }


    public static void writeMessage(final String message){
        //lcd.setColor(Color.BLACK);
        lcd.setColor(0,0,0);
        lcd.drawString(message, 50,50, 0);
        lcd.refresh();
    }

    public static void clear(){
        //lcd.setColor(Color.WHITE);
        lcd.setColor(255,255,255);
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());

    }
}
