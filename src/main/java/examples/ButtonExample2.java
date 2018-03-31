package examples;

import ev3dev.sensors.Button;

public class ButtonExample2 {

    public static void main(final String[] args){
        System.out.println("Extended Button Example");

        System.out.println("Press UP");
        Button.UP.waitForPress();
        System.out.println("Pressed UP");

        System.out.println("Press DOWN");
        Button.DOWN.waitForPress();
        System.out.println("Pressed DOWN");

        System.out.println("Press LEFT");
        Button.LEFT.waitForPress();
        System.out.println("Pressed LEFT");

        System.out.println("Press RIGHT");
        Button.RIGHT.waitForPress();
        System.out.println("Pressed RIGHT");

        System.out.println("Press ESCAPE");
        Button.ESCAPE.waitForPress();
        System.out.println("Pressed ESCAPE");

        System.out.println("Press ENTER");
        Button.ENTER.waitForPress();
        System.out.println("Pressed ENTER");

    }
}