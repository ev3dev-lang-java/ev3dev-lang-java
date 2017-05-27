package ev3dev.actuators.ev3;

import lejos.hardware.LED;
import lejos.utility.Delay;

public class LEDExample {

    /**
     * @param args
     */
    public static void main(String[] args) {

        LED led = new EV3Led(EV3Led.LEFT);
        led.setPattern(0);
        Delay.msDelay(1000);
        led.setPattern(1);
        Delay.msDelay(1000);
        led.setPattern(2);
        Delay.msDelay(1000);
        led.setPattern(3);
        Delay.msDelay(1000);
        led.setPattern(0);

        LED led2 = new EV3Led(EV3Led.RIGHT);
        led2.setPattern(0);
        Delay.msDelay(1000);
        led2.setPattern(1);
        Delay.msDelay(1000);
        led2.setPattern(2);
        Delay.msDelay(1000);
        led2.setPattern(3);
        Delay.msDelay(1000);
        led2.setPattern(0);
    }

}