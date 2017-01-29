package examples.sensors.ev3;

import ev3dev.actuators.Sound;
import ev3dev.sensors.ev3.EV3GyroSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class GyroSensorDemo3 {

	//Robot Configuration
	private static EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S1);

	public static void main(String[] args) {

		final SampleProvider sp = gyroSensor.getAngleAndRateMode();
		int value = 0;

        //Control loop
		while(true){
			float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            value = (int)sample[0];

            log.info("Gyro angle/rate: {}", value);

			if(value >= 90){
				Sound.getInstance().beep();
				break;
			}
		}
		
	}

}
