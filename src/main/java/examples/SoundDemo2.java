package examples;

import ev3dev.actuators.Sound;
import ev3dev.utils.JarResource;

import java.io.File;
import java.io.IOException;

public class SoundDemo2 {

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
		
		sound.setVolume(20);
		System.out.println("Volume: " + sound.getVolume());

		JarResource.export(filePath);
		File file = new File(filePath);
		sound.playSample(file);

		sound.setVolume(40);
		sound.playSample(file);

		sound.setVolume(60);
		sound.playSample(file);

		sound.setVolume(80);
		sound.playSample(file);

		sound.setVolume(100);
		sound.playSample(file);

		sound.beep();
	}

}
