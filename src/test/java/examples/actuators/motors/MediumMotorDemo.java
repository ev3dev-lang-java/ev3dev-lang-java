package examples.actuators.motors;

import ev3dev.actuators.motors.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class MediumMotorDemo {
	
    public static void main(String[] args) {

		log.info("Testing some features for a medium Motor");
        final EV3MediumRegulatedMotor mA = new EV3MediumRegulatedMotor(MotorPort.A);
        //mA.setSpeed(500);
      
        int ONE_SECOND = 1000;
 
        mA.forward();		
		log.info("{}", mA.isMoving());
		Delay.msDelay(ONE_SECOND);
		mA.stop();
		log.info("{}", mA.isMoving());
		mA.backward();
		log.info("{}", mA.isMoving());
		Delay.msDelay(ONE_SECOND);
		mA.stop();
		log.info("{}", mA.isMoving());
		mA.forward();
		log.info("{}", mA.isMoving());
		Delay.msDelay(ONE_SECOND);
		mA.stop();
    }
}
