package examples.actuators.motors;

import ev3dev.actuators.motors.NXTRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class NXTLargeMotorDemo {

    public static void main(String[] args) throws InterruptedException {

        log.info("Starting motors on A");
        final NXTRegulatedMotor mA = new NXTRegulatedMotor(MotorPort.A);
        mA.setSpeed(500);

        mA.forward();
        log.info("NXT Large Motor is moving: {} at speed {}", mA.isMoving(), mA.getSpeed());
        Delay.msDelay(2000);
        mA.stop();

        mA.backward();
        log.info("NXT Large Motor is moving: {} at speed {}", mA.isMoving(), mA.getSpeed());
        Delay.msDelay(2000);
        mA.stop();

        log.info("Stopped motors");
    }

}
