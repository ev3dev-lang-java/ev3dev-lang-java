package examples.sensors.ev3;

import ev3dev.sensors.ev3.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class ColorSensorDemo3 {

	//Robot Configuration
	private static EV3ColorSensor color1 = new EV3ColorSensor(SensorPort.S1);
	
	//Configuration
	private static int HALF_SECOND = 500;
	
	public static void main(String[] args) {

		//Color ID
		log.info("Switching to Color ID Mode");
		SampleProvider sp = color1.getColorIDMode();
		
		int sampleSize = sp.sampleSize();
		float[] sample = new float[sampleSize];

        log.info("Switching to Color ID Mode");
		sp = color1.getColorIDMode();

		int value = 0;

        // Takes some samples and prints them
        for (int i = 0; i < 100; i++) {
        	sp.fetchSample(sample, 0);
			value = (int)sample[0];

			log.info("N={} Sample={}", i, value);
            
            Delay.msDelay(HALF_SECOND);

			if(value == Color.BLUE){
				log.info("Blue ball found");
				break;
			}
        }

	}

}
