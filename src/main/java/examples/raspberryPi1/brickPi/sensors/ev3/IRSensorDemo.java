package examples.raspberryPi1.brickPi.sensors.ev3;

import ev3dev.hardware.ports.SensorPortBrickPi;
import ev3dev.sensors.ev3.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class IRSensorDemo {

	//Robot Configuration
	private static EV3IRSensor ir1 = new EV3IRSensor(SensorPortBrickPi.S1);
	
	//Configuration
	private static int HALF_SECOND = 500;
	
	public static void main(String[] args) {

		final SampleProvider sp = ir1.getDistanceMode();
		int distanceValue = 0;

		//Control loop
		final int iteration_threshold = 10;
		for(int i = 0; i <= iteration_threshold; i++) {
		
			float [] sample = new float[sp.sampleSize()];
		    sp.fetchSample(sample, 0);
		    distanceValue = (int)sample[0];
		  	
		  	log.info("Iteration: {}", i);
		  	log.info("Distance: {}", distanceValue);
		      
		    Delay.msDelay(HALF_SECOND);
		}
		
	}

}
