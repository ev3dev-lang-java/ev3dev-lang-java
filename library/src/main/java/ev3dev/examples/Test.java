package ev3dev.examples;

import ev3dev.hardware.motor.Motor;
import ev3dev.hardware.sensor.ev3.GyroSensor;
import ev3dev.hardware.sensor.ev3.IRSensor;

public class Test {

	public static void main(String[] args) {
		
		Motor mA = new Motor("outA");
		mA.setSpeed(50);
		Motor mB = new Motor("outB");
		mB.setSpeed(50);
		IRSensor ir1 = new IRSensor("in2");
		GyroSensor gyro1 = new GyroSensor("in1");
		System.out.println(gyro1.getAngle());

		final int distance_threshold = 35;
		final int iteration_threshold = 100;
		
		for(int i = 0; i <= iteration_threshold; i++) {
			mA.forward();
			mB.forward();
			
			if(ir1.getDistance() <= distance_threshold){
				mA.stop();
				mB.stop();
				break;
			}else {
				System.out.println(ir1.getDistance());
			}
		}

		mA.stop();
		mB.stop();

	}

}
