package examples.sensors;

import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.sensor.ev3.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

//java -cp ev3-lang-java-0.2-SNAPSHOT.jar IRSensorDemo
public class IRSensorDemo {

	//Robot Configuration
	private static EV3IRSensor ir1 = new EV3IRSensor(SensorPort.S1);
	
	//Configuration
	private static int HALF_SECOND = 500;
	
	public static void main(String[] args) {

		final SampleProvider sp = ir1.getDistanceMode();
		int distanceValue = 0;

		//Robot control loop
		final int iteration_threshold = 50;
		for(int i = 0; i <= iteration_threshold; i++) {
		
			float [] sample = new float[sp.sampleSize()];
		    sp.fetchSample(sample, 0);
		    distanceValue = (int)sample[0];
		  	
		  	System.out.println("Iteration: " + i);
		  	System.out.println("Distance: " + distanceValue);
		    System.out.println();
		      
		    Delay.msDelay(HALF_SECOND);
		}
		
	}

}
