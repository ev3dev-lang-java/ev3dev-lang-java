package examples.hardware.sensors.ev3;

import ev3dev.hardware.ports.SensorPort;
import ev3dev.hardware.sensors.ev3.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class USSensorDemo2 {

	//Robot Configuration
	private static EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S1);
	
	//Configuration
	private static int HALF_SECOND = 500;
	
	public static void main(String[] args) {

		SampleProvider sp;

		for(int i = 0; i <= 10; i++) {

			sp = us1.getListenMode();
			int sampleSize = sp.sampleSize();
			float[] sample = new float[sampleSize];
			sp.fetchSample(sample, 0);

			Delay.msDelay(2000);

			sp = us1.getDistanceMode();
			sampleSize = sp.sampleSize();
			sample = new float[sampleSize];
			sp.fetchSample(sample, 0);

		}
		
	}

}
