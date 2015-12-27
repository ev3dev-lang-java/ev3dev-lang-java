package ev3dev.java.examples;

import ev3dev.hardware.Battery;
import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.motor.EV3LargeRegulatedMotor;
import ev3dev.hardware.sensor.ev3.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

//java -cp ev3-lang-java-0.2-SNAPSHOT.jar ev3dev.java.examples.Test
public class Test {
	
    public static void main(String[] args) {
    	
		final int motorSpeed = 500;
		final int incrementSpeed = 50;
        final EV3IRSensor ir1 = new EV3IRSensor(SensorPort.S2);
        final SampleProvider sp = ir1.getDistanceMode();
        int distance = 255;

        final EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
        final EV3LargeRegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
        
        final int distance_threshold = 35;
        final int iteration_threshold = 100;

        for(int i = 0; i <= iteration_threshold; i++) {
            mA.setSpeed(motorSpeed);
            mB.setSpeed(motorSpeed);
            mA.forward();
            mB.forward();

            float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            distance = (int)sample[0];

            if(distance <= distance_threshold){
                mA.backward();
                mB.backward();
                Delay.msDelay(1000);
                mA.stop();
                mB.stop();
                mA.setSpeed(motorSpeed + incrementSpeed);
                mB.setSpeed(motorSpeed + incrementSpeed);
                mA.backward();
                mB.forward();
                Delay.msDelay(1000);
                mA.stop();
                mB.stop();
            }

        	System.out.println("Iteration: " + i);
            System.out.println("Battery: " + Battery.getVoltage());
            System.out.println("Distance: " + distance);
            System.out.println();
        }

        mA.stop();
        mB.stop();
        System.exit(0);
    }
}
