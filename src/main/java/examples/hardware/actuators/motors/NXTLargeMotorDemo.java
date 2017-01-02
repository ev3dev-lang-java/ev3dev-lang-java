package examples.hardware.actuators.motors;

import ev3dev.hardware.actuators.motors.NXTRegulatedMotor;
import ev3dev.hardware.ports.MotorPort;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class NXTLargeMotorDemo {

    public static void main(String[] args) throws InterruptedException {

        log.info("Starting motors on A");
        final NXTRegulatedMotor mA = new NXTRegulatedMotor(MotorPort.A);
        mA.setSpeed(500);
        mA.forward();
        log.info(String.format("NXT Large Motor is moving: %s at speed %d", mA.isMoving(), mA.getSpeed()));
        Delay.msDelay(2000);
        mA.stop();
        log.info("Stopped motors");
    }

}
