package examples;

import ev3dev.actuators.Sound;
import ev3dev.utils.JarResource;
import lejos.utility.Delay;

import java.io.File;
import java.io.IOException;

public class SoundDemo {

	//Configuration
	private static int MAX_VOLUME = 100;
	private static String filePath = "nod_low_power.wav";
    private final static int ONE_SECOND = 1000;
	
    private static final int FREQ1	= 300;
    private static final int FREQ2 = 400;
    private static final int variation = 10;
    
	public static void main(String[] args) throws IOException {

		System.out.println("Sound example");

		Sound sound = Sound.getInstance();
		
//		sound.setVolume(MAX_VOLUME);
//		System.out.println("Volume: " + sound.getVolume());

		JarResource.export(filePath);
		File file = new File(filePath);
		sound.playSample(file);

		sound.beep();
		sound.twoBeeps();
		
		Delay.msDelay(ONE_SECOND);

		for(int i = FREQ1; i <= FREQ2; i += variation) {
			sound.playTone(i, 500, 100);
		}
		
		sound.playTone(300, 500);
	}

}
