package examples.sensors;

import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.sensor.ev3.EV3GyroSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

//java -cp ev3-lang-java-0.2-SNAPSHOT.jar GyroSensorDemo
public class GyroSensorDemo {

	//Robot Configuration
	private static EV3GyroSensor touch1 = new EV3GyroSensor(SensorPort.S3);
	
	//Configuration
	private static int HALF_SECOND = 500;
	
	public static void main(String[] args) {

		final SampleProvider sp = touch1.getAngleMode();
		int value = 0;

        //Robot control loop
        final int iteration_threshold = 50;
        for(int i = 0; i <= iteration_threshold; i++) {

        	float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            value = (int)sample[0];
        	
        	System.out.println("Iteration: " + i);
            System.out.println("Touch: " + value);
            System.out.println();
            
            Delay.msDelay(HALF_SECOND);
        }
		
	}

}
