package examples.actuators.motors;

import ev3dev.actuators.Sound;
import ev3dev.actuators.motors.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class RegulatedMotorRotateDemo {
	
    public static void main(String[] args) {

		Sound sound = Sound.getInstance();

    	final int degreesToTurn = 90;
    	
        final EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
        mA.resetTachoCount();
        mA.setSpeed(100);
        mA.brake();

        sound.beep();
        log.info("{}", mA.getTachoCount());
        mA.rotate(degreesToTurn);
        sound.beep();
        Delay.msDelay(1000);
        log.info("{}", mA.getTachoCount());
        mA.rotate(degreesToTurn);
        sound.beep();
        Delay.msDelay(1000);
        log.info("{}", mA.getTachoCount());
        mA.rotate(degreesToTurn);
        sound.beep();
        Delay.msDelay(1000);
        log.info("{}", mA.getTachoCount());
        mA.rotate(degreesToTurn);
        sound.beep();
        Delay.msDelay(1000);
        log.info("{}", mA.getTachoCount());
    }
}
