package ev3dev.java.examples;

import java.io.File;

import ev3dev.hardware.Sound;

public class Test5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("/home/cncsounds/commander.wav");
		Sound.setVolume(100);
		Sound.playSample(file);
		Sound.beep();
		Sound.twoBeeps();
		Sound.playTone(300, 50000, 100);
		System.out.println(Sound.getVolume());
	}

}
