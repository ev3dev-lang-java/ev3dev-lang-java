package ev3dev.java.examples;

import lejos.utility.Delay;
import ev3dev.hardware.Sound;
import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.motor.UnregulatedMotor;

public class Test2 {

	public static void main(String[] args) {

		UnregulatedMotor umotor1 = new UnregulatedMotor(MotorPort.C);
		UnregulatedMotor umotor2 = new UnregulatedMotor(MotorPort.D);
		umotor1.setPower(25);
		umotor2.setPower(100);
		umotor1.forward();
		
		System.out.println(umotor1.isMoving());
		Delay.msDelay(1000);
		
		umotor1.stop();
		umotor1.backward();
		Delay.msDelay(1000);
		umotor1.stop();
		
		
		System.out.println(umotor1.isMoving());
		Sound.beep();
		umotor2.forward();
		
		Delay.msDelay(1000);
		
		umotor2.stop();
		Sound.twoBeeps();
		
		
	}

}
