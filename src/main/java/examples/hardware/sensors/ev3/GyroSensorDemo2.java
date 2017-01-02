package examples.hardware.sensors.ev3;

import ev3dev.hardware.ports.SensorPort;
import ev3dev.hardware.sensors.ev3.EV3GyroSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class GyroSensorDemo2 {

	//Robot Configuration
	private static EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S1);

	public static void main(String[] args) {

		SampleProvider sp = gyroSensor.getRateMode();
		float[] sample = new float[sp.sampleSize()];
		sp.fetchSample(sample, 0);
		sp = gyroSensor.getAngleMode();
		sample = new float[sp.sampleSize()];
		sp.fetchSample(sample, 0);
		sp = gyroSensor.getAngleAndRateMode();
		sample = new float[sp.sampleSize()];
		sp.fetchSample(sample, 0);

		gyroSensor.reset();
	}

}
