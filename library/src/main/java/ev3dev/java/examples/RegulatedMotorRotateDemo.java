package ev3dev.java.examples;

import lejos.utility.Delay;
import ev3dev.hardware.Battery;
import ev3dev.hardware.Sound;
import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.motor.EV3LargeRegulatedMotor;

//java -cp ev3-lang-java-0.2-SNAPSHOT.jar ev3dev.java.examples.RegulatedMotorRotateDemo
public class RegulatedMotorRotateDemo {
	
    public static void main(String[] args) {
    	
		Sound sound = Sound.getInstance();
		
    	final int degreesToTurn = 90;
    	
        final EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
        mA.setSpeed(100);

        sound.beep();
        System.out.println(mA.getTachoCount());
        mA.rotate(degreesToTurn);
        sound.beep();
        Delay.msDelay(1000);
        System.out.println(mA.getTachoCount());
        mA.rotate(degreesToTurn);
        sound.beep();  
        Delay.msDelay(1000);
        System.out.println(mA.getTachoCount());
        mA.rotate(degreesToTurn);
        sound.beep();
        Delay.msDelay(1000);
        System.out.println(mA.getTachoCount());
        mA.rotate(degreesToTurn);
        sound.beep();
        Delay.msDelay(1000);
        System.out.println(mA.getTachoCount());
        System.out.println(Battery.getInstance().getVoltage());
    }
}
