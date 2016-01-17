package ev3dev.java.examples.sensors;

import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import ev3dev.hardware.Battery;
import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.sensor.ev3.EV3UltrasonicSensor;

//java -cp ev3-lang-java-0.2-SNAPSHOT.jar ev3dev.java.examples.sensors.USSensorDemo
public class USSensorDemo {

	//Robot Configuration
	private static EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S3);
	
	//Configuration
	private static int HALF_SECOND = 500;
	
	public static void main(String[] args) {

		final SampleProvider sp = us1.getDistanceMode();
		int distanceValue = 0;

        //Robot control loop
        final int iteration_threshold = 50;
        for(int i = 0; i <= iteration_threshold; i++) {

        	float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            distanceValue = (int)sample[0];
        	
        	System.out.println("Iteration: " + i);
            System.out.println("Battery: " + Battery.getInstance().getVoltage());
            System.out.println("Touch: " + distanceValue);
            System.out.println();
            
            Delay.msDelay(HALF_SECOND);
        }
		
	}

}
