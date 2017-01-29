package examples.actuators.motors;

import ev3dev.actuators.motors.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class UnregulatedMotorDemo2 {

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
		umotor1.brake();

		//Testing DC-Motor 1
		umotor1.forward();		
		log.info("{}", umotor1.isMoving());
		Delay.msDelay(ONE_SECOND);
		umotor1.stop();
		log.info("{}", umotor1.isMoving());
		umotor1.backward();
		log.info("{}", umotor1.isMoving());
		Delay.msDelay(ONE_SECOND);
		umotor1.stop();
		log.info("{}", umotor1.isMoving());
		umotor1.forward();
		log.info("{}", umotor1.isMoving());
		Delay.msDelay(ONE_SECOND);
		umotor1.stop();

	}

}
