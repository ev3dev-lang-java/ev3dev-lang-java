package ev3dev.test;

import ev3dev.hardware.Battery;
import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.motor.EV3LargeRegulatedMotor;
import ev3dev.hardware.sensor.ev3.EV3IRSensor;
import lejos.robotics.SampleProvider;

public class MotorTest {

	public static void main(String[] args) {

		EV3IRSensor ir1 = new EV3IRSensor(SensorPort.S2);
        SampleProvider sp = ir1.getDistanceMode();
        int distance = 255;

		EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
        mA.setSpeed(50);
        mB.setSpeed(50);

        final int distance_threshold = 35;
        final int iteration_threshold = 100;

        for(int i = 0; i <= iteration_threshold; i++) {
        	System.out.println(Battery.getVoltage());
            mA.forward();
            mB.forward();

            float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            distance = (int)sample[0];
            
            if(distance <= distance_threshold){
                mA.stop();
                mB.stop();
                break;
            }else {
                System.out.println(distance);
            }
        }

        mA.stop();
        mB.stop();		
	}

}
