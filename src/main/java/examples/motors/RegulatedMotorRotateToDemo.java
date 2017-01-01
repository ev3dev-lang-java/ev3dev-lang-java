package examples.motors;

import ev3dev.hardware.actuator.Sound;
import ev3dev.hardware.actuator.motor.EV3LargeRegulatedMotor;
import ev3dev.hardware.port.MotorPort;
import lejos.utility.Delay;

//java -cp ev3-lang-java-0.2-SNAPSHOT.jar RegulatedMotorRotateToDemo
public class RegulatedMotorRotateToDemo {
	
    public static void main(String[] args) {
    	
		Sound sound = Sound.getInstance();
    	
    	final int degreesToTurn = 90;
    	int currentDegrees = 90;
    	
        EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
        mA.setSpeed(100);

        System.out.println(mA.getTachoCount());
        currentDegrees += degreesToTurn;
        System.out.println(currentDegrees);
        mA.rotateTo(currentDegrees);
        sound.beep();  
        Delay.msDelay(1000);
        System.out.println(mA.getTachoCount());
        currentDegrees += degreesToTurn;
        System.out.println(currentDegrees);
        mA.rotateTo(currentDegrees);
        sound.beep();
        Delay.msDelay(1000);
        
        /*
        System.out.println(mA.getTachoCount());
        currentDegrees += degreesToTurn;
        System.out.println(currentDegrees);
        mA.rotateTo(currentDegrees);
        Sound.beep();
        Delay.msDelay(1000);
        System.out.println(mA.getTachoCount());
        currentDegrees += degreesToTurn;
        System.out.println(currentDegrees);
        mA.rotateTo(currentDegrees);
        Sound.beep();
        System.out.println(mA.getTachoCount());
        currentDegrees += degreesToTurn;
        System.out.println(currentDegrees);
        mA.rotateTo(currentDegrees);
        Sound.beep();
        System.out.println(mA.getTachoCount());
        */

        mA.close();
        System.exit(0);
    }
}
