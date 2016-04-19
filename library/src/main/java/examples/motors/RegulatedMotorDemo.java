package examples.motors;

import lejos.utility.Delay;
import ev3dev.hardware.Battery;
import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.motor.EV3MediumRegulatedMotor;

//java -cp ev3-lang-java-0.2-SNAPSHOT.jar RegulatedMotorDemo
public class RegulatedMotorDemo {
	
    public static void main(String[] args) {
    	
        final EV3MediumRegulatedMotor mA = new EV3MediumRegulatedMotor(MotorPort.A);
        mA.setSpeed(500);
      
        int ONE_SECOND = 1000;
 
        mA.forward();		
		System.out.println(mA.isMoving());
		Delay.msDelay(ONE_SECOND);
		mA.stop();
		System.out.println(mA.isMoving());
		mA.backward();
		System.out.println(mA.isMoving());
		Delay.msDelay(ONE_SECOND);
		mA.stop();
		System.out.println(mA.isMoving());
		mA.forward();
		System.out.println(mA.isMoving());
		Delay.msDelay(ONE_SECOND);
		mA.stop();
        System.out.println(Battery.getInstance().getVoltage());

        mA.close();
        System.exit(0);
    }
}
