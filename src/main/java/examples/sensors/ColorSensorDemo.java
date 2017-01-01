package examples.sensors;

import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.sensor.ev3.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

//java -cp ev3-lang-java-0.2-SNAPSHOT.jar ColorSensorDemo
public class ColorSensorDemo {

	//Robot Configuration
	private static EV3ColorSensor color1 = new EV3ColorSensor(SensorPort.S3);
	
	//Configuration
	private static int HALF_SECOND = 500;
	
	public static void main(String[] args) {

		//Red Mode
		SampleProvider sp = color1.getRedMode();
		
		int sampleSize = sp.sampleSize();
		float[] sample = new float[sampleSize];

        // Takes some samples and prints them
        for (int i = 0; i < 10; i++) {
        	sp.fetchSample(sample, 0);
            System.out.println("N=" + i + " Sample=" + (int)sample[0]);
            
            Delay.msDelay(HALF_SECOND);
        }

        //Color ID
		sp = color1.getColorIDMode();
		
		sampleSize = sp.sampleSize();
		sample = new float[sampleSize];

        // Takes some samples and prints them
        for (int i = 0; i < 10; i++) {
        	sp.fetchSample(sample, 0);
            System.out.println("N=" + i + " Sample=" + (int)sample[0]);
            
            Delay.msDelay(HALF_SECOND);
        }

        //Ambient Mode
		sp = color1.getAmbientMode();
		
		sampleSize = sp.sampleSize();
		sample = new float[sampleSize];

        // Takes some samples and prints them
        for (int i = 0; i < 10; i++) {
        	sp.fetchSample(sample, 0);
            System.out.println("N=" + i + " Sample=" + (int)sample[0]);
            
            Delay.msDelay(HALF_SECOND);
        }
		
		//RGB
		sp = color1.getRGBMode();
		
		sampleSize = sp.sampleSize();
		sample = new float[sampleSize];

        // Takes some samples and prints them
        for (int i = 0; i < 10; i++) {
        	sp.fetchSample(sample, 0);
            System.out.println("N=" + i + " Sample=" + (int)sample[0]);
            System.out.println("N=" + i + " Sample=" + (int)sample[1]);
            System.out.println("N=" + i + " Sample=" + (int)sample[2]);
            
            Delay.msDelay(HALF_SECOND);
        }

	}

}
