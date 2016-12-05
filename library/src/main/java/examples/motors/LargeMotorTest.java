package examples.motors;

import lejos.utility.Delay;
import ev3dev.hardware.Battery;
import ev3dev.hardware.Sound;
import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.motor.EV3LargeRegulatedMotor;

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
        System.out.println("Battery: " + Battery.getInstance().getVoltage());
        Sound.getInstance().playTone(1000, 100);

        System.exit(0);
    }

}
