package ev3dev.java.examples.motors;

import lejos.utility.Delay;
import ev3dev.hardware.Battery;
import ev3dev.hardware.Sound;
import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.motor.EV3LargeRegulatedMotor;

//java -cp ev3-lang-java-0.2-SNAPSHOT.jar ev3dev.examples.Test4
public class Test4 {
	
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
        System.out.println(Battery.getInstance().getVoltage());
    }
}
