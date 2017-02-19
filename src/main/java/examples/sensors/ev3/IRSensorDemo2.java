package examples.sensors.ev3;

import ev3dev.sensors.ev3.EV3IRSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class IRSensorDemo2 {

	//Robot Configuration
	private static EV3IRSensor ir1 = new EV3IRSensor(SensorPort.S1);
	
	//Configuration
	private static int HALF_SECOND = 500;
	
	public static void main(String[] args) {

		final SampleProvider sp = ir1.getSeekMode();

		int beaconInfo1H = 0;
		int beaconInfo2H = 0;
		int beaconInfo3H = 0;
		int beaconInfo4H = 0;
		int beaconInfo1D = 0;
		int beaconInfo2D = 0;
		int beaconInfo3D = 0;
		int beaconInfo4D = 0;

		//Control loop
		final int iteration_threshold = 50;
		for(int i = 0; i <= iteration_threshold; i++) {
		
			float [] sample = new float[sp.sampleSize()];
		    sp.fetchSample(sample, 0);

		    beaconInfo1H = (int)sample[0];
			beaconInfo1D = (int)sample[1];

			beaconInfo2H = (int)sample[2];
			beaconInfo2D = (int)sample[3];

			beaconInfo3H = (int)sample[4];
			beaconInfo3D = (int)sample[5];

			beaconInfo4H = (int)sample[6];
			beaconInfo4D = (int)sample[7];

		  	log.info("Iteration: {}", i);
		  	log.info("Beacon Channel 1: Heading: {}, Distance: {}", beaconInfo1H, beaconInfo1D);
			log.info("Beacon Channel 2: Heading: {}, Distance: {}", beaconInfo2H, beaconInfo2D);
			log.info("Beacon Channel 3: Heading: {}, Distance: {}", beaconInfo3H, beaconInfo3D);
			log.info("Beacon Channel 4: Heading: {}, Distance: {}", beaconInfo4H, beaconInfo4D);

		    Delay.msDelay(HALF_SECOND);
		}
		
	}

}
