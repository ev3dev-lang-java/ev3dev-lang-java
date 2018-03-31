package examples;

import ev3dev.sensors.Battery;
import ev3dev.sensors.ev3.EV3TouchSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class TouchSensorDemo {

	//Robot Configuration
	private static EV3TouchSensor touch1 = new EV3TouchSensor(SensorPort.S1);
	
	//Configuration
	private static int HALF_SECOND = 500;
	
	public static void main(String[] args) {

		final SampleProvider sp = touch1.getTouchMode();
		int touchValue = 0;

        //Control loop
        final int iteration_threshold = 20;
        for(int i = 0; i <= iteration_threshold; i++) {

        	float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            touchValue = (int)sample[0];

			System.out.println("Iteration: " + i);
			System.out.println("Touch: " + touchValue);
            
            Delay.msDelay(HALF_SECOND);
        }

        System.out.println(Battery.getInstance().getVoltage());
		
	}

}
