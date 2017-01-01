package examples.motors;

import ev3dev.hardware.actuator.Sound;
import ev3dev.hardware.actuator.motor.EV3LargeRegulatedMotor;
import ev3dev.hardware.port.MotorPort;
import lejos.utility.Delay;

public class LargeMotorTest {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting motor on A");
        final EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
        mA.setSpeed(500);
        mA.forward();
        System.out.println(String.format("Large Motor is moving: %s at speed %d", mA.isMoving(), mA.getSpeed()));
        Delay.msDelay(2000);
        mA.stop();
        System.out.println("Stopped motor");
        Sound.getInstance().playTone(1000, 100);

        System.exit(0);
    }

}
