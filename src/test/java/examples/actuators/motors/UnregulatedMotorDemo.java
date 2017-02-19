package examples.actuators.motors;

import ev3dev.actuators.motors.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class UnregulatedMotorDemo {

	//Robot Definition
	private static UnregulatedMotor umotor1 = new UnregulatedMotor(MotorPort.A);

    //Configuration
    private final static int motorPower = 50;
    private final static int ONE_SECOND = 1000;
	
	public static void main(String[] args) {

		//Set power for DC Motors
		umotor1.setPower(motorPower);
		
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
