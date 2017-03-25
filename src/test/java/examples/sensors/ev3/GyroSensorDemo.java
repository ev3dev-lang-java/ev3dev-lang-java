package examples.sensors.ev3;

import ev3dev.sensors.ev3.EV3GyroSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class GyroSensorDemo {

	//Robot Configuration
	private static EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S1);
	
	//Configuration
	private static int HALF_SECOND = 500;
	
	public static void main(String[] args) {

		final SampleProvider sp = gyroSensor.getAngleMode();
		int value = 0;

        //Control loop
        final int iteration_threshold = 20;
        for(int i = 0; i <= iteration_threshold; i++) {

        	float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            value = (int)sample[0];
        	
        	log.info("Iteration: {}", i);
            log.info("Gyro angle: {}", value);
            
            Delay.msDelay(HALF_SECOND);
        }
		
	}

}
