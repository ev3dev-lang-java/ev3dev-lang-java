package examples;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class LargeMotorDemo2 {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting motors on A");
        final EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
        mA.setSpeed(500);
        mA.brake();

        System.out.println("Forward");
        mA.forward();
        System.out.println("Large Motor is moving: " +  mA.isMoving() + " at speed {}" + mA.getSpeed());
        Delay.msDelay(2000);
        mA.stop();
        System.out.println("Stop");
        System.out.println("Backward");
        mA.backward();
        System.out.println("Large Motor is moving: " + mA.isMoving() + " at speed {}" + mA.getSpeed());
        Delay.msDelay(2000);
        System.out.println("Stop");
        mA.stop();
        System.out.println("Forward");
        mA.forward();
        Delay.msDelay(2000);
        mA.stop();
        System.out.println("Stop");

    }

}
