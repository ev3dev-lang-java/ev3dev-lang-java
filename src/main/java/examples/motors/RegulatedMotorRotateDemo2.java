package examples.motors;

import ev3dev.hardware.actuator.Sound;
import ev3dev.hardware.actuator.motor.EV3MediumRegulatedMotor;
import ev3dev.hardware.port.MotorPort;
import lejos.utility.Delay;

//java -cp ev3-lang-java-0.2-SNAPSHOT.jar RegulatedMotorRotateDemo2
public class RegulatedMotorRotateDemo2 {
	
    public static void main(String[] args) {
    	
		Sound sound = Sound.getInstance();
    	
    	final int degreesToTurn = 90;
    	
        final EV3MediumRegulatedMotor mA = new EV3MediumRegulatedMotor(MotorPort.A);
        mA.setSpeed(100);

        sound.beep();
        System.out.println(mA.getTachoCount());
        mA.rotate(degreesToTurn);
        sound.beep();
        Delay.msDelay(1000);
        System.out.println(mA.getTachoCount());
        mA.rotate(degreesToTurn);
        sound.beep();

        /*
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
        */
        
        mA.close();
        System.exit(0);
    }
}
