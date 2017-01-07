package examples.hardware.actuators.motors;

import ev3dev.hardware.actuators.motors.EV3LargeRegulatedMotor;
import ev3dev.hardware.ports.MotorPort;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class LargeMotorDemo3 {

    public static void main(String[] args) throws InterruptedException {

        log.info("Starting motors on B");
        final EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.B);
        mA.setSpeed(500);
        mA.brake();
        log.info("Forward");
        mA.forward();
        log.info("Large Motor is moving: {} at speed {}", mA.isMoving(), mA.getSpeed());
        Delay.msDelay(2000);
        mA.stop();
        log.info("Stop");
        log.info("Backward");
        mA.backward();
        log.info("Large Motor is moving: {} at speed {}", mA.isMoving(), mA.getSpeed());
        Delay.msDelay(2000);
        mA.stop();
        log.info("Stop");
        log.info("Forward");
        mA.forward();
        Delay.msDelay(2000);
        mA.stop();
        log.info("Stop");

    }

}
