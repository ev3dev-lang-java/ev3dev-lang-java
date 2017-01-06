package examples.hardware.actuators.motors;

import ev3dev.hardware.actuators.motors.EV3LargeRegulatedMotor;
import ev3dev.hardware.ports.MotorPort;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class LargeMotorDemo2 {

    public static void main(String[] args) throws InterruptedException {

        log.info("Starting motors on A");
        final EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
        mA.setSpeed(500);
        mA.brake();
        mA.forward();
        log.info(String.format("Large Motor is moving: %s at speed %d", mA.isMoving(), mA.getSpeed()));
        Delay.msDelay(2000);
        mA.stop();
        log.info("Stopped motors");
        Delay.msDelay(2000);
        log.info("Reversing direction motors");
        mA.backward();
        Delay.msDelay(2000);
        log.info("Stopped motors");
        mA.stop();

    }

}
