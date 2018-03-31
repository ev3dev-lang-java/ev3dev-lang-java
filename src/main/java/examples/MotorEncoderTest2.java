package examples;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

/**
 * Created by jabrena on 28/6/17.
 */
public class MotorEncoderTest2 {

    public static void main(String[] args){

        RegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
        motor.resetTachoCount();
        motor.coast();
        motor.setSpeed(500);
        motor.forward();

        for (int x=1; x < 20; x++){

            System.out.println(motor.getTachoCount());

            Delay.msDelay(500);
        }

        motor.stop();

    }

}
