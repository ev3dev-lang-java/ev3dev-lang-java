package examples;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class UnregulatedMotorDemo2 {

	//Robot Definition
	private static EV3LargeRegulatedMotor umotor1 = new EV3LargeRegulatedMotor(MotorPort.A);

    //Configuration
    private final static int motorPower = 30;
    private final static int ONE_SECOND = 1000;
	
	public static void main(String[] args) {

		//Set power for DC Motors

		umotor1.resetTachoCount();
		umotor1.suspendRegulation();
		umotor1.setSpeed(motorPower);
		umotor1.coast();

		//Testing DC-Motor 1
		umotor1.forward();
		System.out.println("" + umotor1.isMoving());
		Delay.msDelay(ONE_SECOND);
		umotor1.stop();
		System.out.println("" + umotor1.isMoving());
		umotor1.backward();
		System.out.println("" + umotor1.isMoving());
		Delay.msDelay(ONE_SECOND);
		umotor1.stop();
		System.out.println("" + umotor1.isMoving());
		umotor1.forward();
		System.out.println("" + umotor1.isMoving());
		Delay.msDelay(ONE_SECOND);
		umotor1.stop();

	}

}
