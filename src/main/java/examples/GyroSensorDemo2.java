package examples;

import ev3dev.sensors.ev3.EV3GyroSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;

public class GyroSensorDemo2 {

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
