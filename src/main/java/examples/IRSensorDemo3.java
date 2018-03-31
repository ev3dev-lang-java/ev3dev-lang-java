package examples;

import ev3dev.sensors.ev3.EV3IRSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class IRSensorDemo3 {

	//Robot Configuration
	private static EV3IRSensor ir1 = new EV3IRSensor(SensorPort.S1);
	
	//Configuration
	private static int HALF_SECOND = 500;
	
	public static void main(String[] args) {

		final SampleProvider sp = ir1.getRemoteMode();

		int beaconInfo1 = 0;
		int beaconInfo2 = 0;
		int beaconInfo3 = 0;
		int beaconInfo4 = 0;

		//Control loop
		final int iteration_threshold = 50;
		for(int i = 0; i <= iteration_threshold; i++) {
		
			float [] sample = new float[sp.sampleSize()];
		    sp.fetchSample(sample, 0);

			beaconInfo1 = (int)sample[0];
			beaconInfo2 = (int)sample[1];
			beaconInfo3 = (int)sample[2];
			beaconInfo4 = (int)sample[3];

			System.out.println("Iteration: {}" + i);
			System.out.println("Beacon Channel 1: Remote: {}" + beaconInfo1);
			System.out.println("Beacon Channel 2: Remote: {}" + beaconInfo2);
			System.out.println("Beacon Channel 3: Remote: {}" + beaconInfo3);
			System.out.println("Beacon Channel 4: Remote: {}" + beaconInfo4);

		    Delay.msDelay(HALF_SECOND);
		}
		
	}

}
