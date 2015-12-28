package ev3dev.java.examples;

import java.io.File;

import lejos.utility.Delay;
import ev3dev.hardware.Sound;

public class SoundDemo {

	//Configuration
	private static int MAX_VOLUME = 100;
	private static String filePath = "/home/cncsounds/commander.wav";
    private final static int ONE_SECOND = 1000;
	
    private static final int FREQ1	= 300;
    private static final int FREQ2 = 400;
    private static final int variation = 10;
    
	public static void main(String[] args) {
 
		Sound.setVolume(MAX_VOLUME);
		System.out.println("Volume: " + Sound.getVolume());

		File file = new File(filePath);
		Sound.playSample(file);

		Sound.beep();
		Sound.twoBeeps();
		
		Delay.msDelay(ONE_SECOND);

		for(int i = FREQ1; i <= FREQ2; i += variation) {
			Sound.playTone(i, 500, 100);
		}
		
		Sound.playTone(300, 500);
		
        System.exit(0);
	}

}
