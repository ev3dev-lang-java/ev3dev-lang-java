package examples;

import ev3dev.actuators.Sound;
import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.hardware.EV3DevPlatforms;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class RegulatedMotorRotateToDemo extends EV3DevPlatforms {
	
    public static void main(String[] args) {

        RegulatedMotorRotateToDemo example = new RegulatedMotorRotateToDemo();

        Sound sound = Sound.getInstance();
    	
    	final int degreesToTurn = 90;
    	int currentDegrees =  0;

        EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
        mA.resetTachoCount();
        mA.setSpeed(100);

        if(example.getPlatform().equals(EV3DevPlatform.EV3BRICK)) {
            mA.hold();

            System.out.println("" + mA.getTachoCount());
            currentDegrees += degreesToTurn;
            System.out.println("" + currentDegrees);
            mA.rotateTo(currentDegrees);
            sound.beep();
            Delay.msDelay(1000);

            System.out.println("" + mA.getTachoCount());
            currentDegrees += degreesToTurn;
            System.out.println("" + currentDegrees);
            mA.rotateTo(currentDegrees);
            sound.beep();
            Delay.msDelay(1000);

            System.out.println(mA.getTachoCount());
        } else {
            System.out.println("This feature is exclusive of EV3 Brick with accuracy");
        }

    }
}
