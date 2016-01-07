package ev3dev.java.examples;

import java.util.Arrays;

import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.sensor.ev3.EV3ColorSensor;

//java -cp ev3-lang-java-0.2-SNAPSHOT.jar ev3dev.java.examples.ColorSensorDemo
public class ColorSensorDemo {

	//Robot Configuration
	private static EV3ColorSensor color1 = new EV3ColorSensor(SensorPort.S3);
	
	//Configuration
	private static int HALF_SECOND = 500;
	
	public static void main(String[] args) {

		//final SampleProvider sp = color1.getRedMode();
		//final SampleProvider sp = color1.getColorIDMode();
		//final SampleProvider sp = color1.getAmbientMode();
		final SampleProvider sp = color1.getRGBMode();
		
		int sampleSize = sp.sampleSize();
		float[] sample = new float[sampleSize];

        // Takes some samples and prints them
        for (int i = 0; i < 20; i++) {
        	sp.fetchSample(sample, 0);
            System.out.println("N=" + i + " Sample=" + (int)sample[0]);
            System.out.println("N=" + i + " Sample=" + (int)sample[1]);
            System.out.println("N=" + i + " Sample=" + (int)sample[2]);
            
            Delay.msDelay(HALF_SECOND);
        }

	}

}
