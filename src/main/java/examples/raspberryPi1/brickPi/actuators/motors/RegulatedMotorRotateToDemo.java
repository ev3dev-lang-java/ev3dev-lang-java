package examples.raspberryPi1.brickPi.actuators.motors;

import ev3dev.actuators.motors.EV3LargeRegulatedMotor;
import ev3dev.hardware.ports.MotorPortBrickPi;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class RegulatedMotorRotateToDemo {
	
    public static void main(String[] args) {

    	final int degreesToTurn = 90;
    	int currentDegrees =  0;

        EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPortBrickPi.A);
        mA.resetTachoCount();
        mA.setSpeed(100);
        mA.hold();

        log.info("{}", mA.getTachoCount());
        currentDegrees += degreesToTurn;
        log.info("{}", currentDegrees);
        mA.rotateTo(currentDegrees);
        Delay.msDelay(1000);

        log.info("{}", mA.getTachoCount());
        currentDegrees += degreesToTurn;
        log.info("{}", currentDegrees);
        mA.rotateTo(currentDegrees);
        Delay.msDelay(1000);

    }
}
