package ev3dev.java.examples;

import ev3dev.robotics.tts.Espeak;

public class TTSDemo {

	public static void main(String[] args) {

		Espeak TTS = new Espeak();
		
		//Spanish example
		TTS.setVoice("es");
		TTS.setVolume(100);
		TTS.setSpeedReading(200);
		TTS.setPitch(50);
		TTS.setMessage("Soy un robot LEGO");
		TTS.say();
		
		//English example
		TTS.setVoice("en");
		TTS.setSpeedReading(105);
		TTS.setPitch(60);
		TTS.setMessage("I am a LEGO robot");
		TTS.say();
	}

}
