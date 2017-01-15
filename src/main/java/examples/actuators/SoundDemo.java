package examples.actuators;

import ev3dev.actuators.Sound;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class SoundDemo {

	//Configuration
	private static int MAX_VOLUME = 100;
	private static String filePath = "/home/cncsounds/commander.wav";
    private final static int ONE_SECOND = 1000;
	
    private static final int FREQ1	= 300;
    private static final int FREQ2 = 400;
    private static final int variation = 10;
    
	public static void main(String[] args) {
 
		Sound sound = Sound.getInstance();
		
		sound.setVolume(MAX_VOLUME);
		log.info("Volume: {}", sound.getVolume());

		//File file = new File(filePath);
		//sound.playSample(file);

		sound.beep();
		sound.twoBeeps();
		
		Delay.msDelay(ONE_SECOND);

		for(int i = FREQ1; i <= FREQ2; i += variation) {
			sound.playTone(i, 500, 100);
		}
		
		sound.playTone(300, 500);
	}

}
