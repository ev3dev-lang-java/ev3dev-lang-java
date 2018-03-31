package examples;

import ev3dev.actuators.ev3.EV3Led;
import lejos.hardware.LED;
import lejos.utility.Delay;

public class LEDExample {

    /**
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("Example using EV3 Led");

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