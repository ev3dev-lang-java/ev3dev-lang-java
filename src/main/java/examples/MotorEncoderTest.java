package examples;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.Motor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

/**
 * Created by jabrena on 28/6/17.
 */
public class MotorEncoderTest {

    public static void main(String[] args){

        RegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
        motor.resetTachoCount();
        motor.coast();

        for (int x=1; x < 20; x++){

            System.out.println(motor.getTachoCount());

            Delay.msDelay(500);
        }

    }

}
