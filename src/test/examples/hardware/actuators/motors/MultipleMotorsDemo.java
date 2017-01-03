package examples.hardware.actuators.motors;

import ev3dev.hardware.actuators.motors.EV3LargeRegulatedMotor;
import ev3dev.hardware.actuators.motors.EV3MediumRegulatedMotor;
import ev3dev.hardware.ports.MotorPort;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class MultipleMotorsDemo {

    public static void main(String[] args) throws InterruptedException {

        log.info("Starting motor on A");
        final EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
        log.info("Starting motor on B");
        final EV3MediumRegulatedMotor mB = new EV3MediumRegulatedMotor(MotorPort.B);

        mA.setSpeed(500);
        mA.forward();
        log.info(String.format("Large Motor is moving: %s at speed %d", mA.isMoving(), mA.getSpeed()));

        mB.setSpeed(500);
        mB.forward();
        log.info(String.format("Medium Motor is moving: %s at speed %d", mB.isMoving(), mB.getSpeed()));

        Delay.msDelay(2000);
        mA.stop();
        mB.stop();
        log.info("Stopped motors");
    }

}
