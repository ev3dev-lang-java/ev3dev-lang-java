package ev3dev.java.examples;

import lejos.utility.Delay;
import ev3dev.hardware.Sound;
import ev3dev.hardware.motor.EV3LargeRegulatedMotor;
import ev3dev.hardware.port.MotorPort;

public class Test6 {

	public static void main(String[] args) {

		final int motorSpeed = 400;
        final EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
        final EV3LargeRegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
        mA.setSpeed(motorSpeed);
        mB.setSpeed(motorSpeed);
        System.out.println(mA.getSpeed());
        System.out.println(mB.getSpeed());

       
        mA.forward();
        mB.forward();
        Delay.msDelay(1000);

        mA.stop();
        mB.stop();
        Delay.msDelay(1000);

        mA.backward();
        mB.backward();
        System.out.println(mA.getSpeed());
        Delay.msDelay(1000);
		
        mA.stop();
        mB.stop();
        System.out.println(mA.getSpeed());
        Delay.msDelay(1000);

        mA.forward();
        mB.forward();
        System.out.println(mA.getSpeed());
        Delay.msDelay(1000);

        mA.stop();
        mB.stop();
        System.out.println(mA.getSpeed());
        Delay.msDelay(1000);
        
        System.exit(0);

	}

}
