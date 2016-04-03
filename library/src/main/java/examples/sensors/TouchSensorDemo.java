package examples.sensors;

import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import ev3dev.hardware.Battery;
import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.sensor.ev3.EV3TouchSensor;

//java -cp ev3-lang-java-0.2-SNAPSHOT.jar TouchSensorDemo
public class TouchSensorDemo {

	//Robot Configuration
	private static EV3TouchSensor touch1 = new EV3TouchSensor(SensorPort.S2);
	
	//Configuration
	private static int HALF_SECOND = 500;
	
	public static void main(String[] args) {

		final SampleProvider sp = touch1.getTouchMode();
		int touchValue = 0;

        //Robot control loop
        final int iteration_threshold = 5;
        for(int i = 0; i <= iteration_threshold; i++) {

        	float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            touchValue = (int)sample[0];
        	
        	System.out.println("Iteration: " + i);
            System.out.println("Battery: " + Battery.getInstance().getVoltage());
            System.out.println("Touch: " + touchValue);
            System.out.println();
            
            Delay.msDelay(HALF_SECOND);
        }
		
	}

}
