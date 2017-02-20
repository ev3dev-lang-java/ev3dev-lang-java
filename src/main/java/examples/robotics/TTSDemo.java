package examples.robotics;

import ev3dev.robotics.tts.Espeak;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class TTSDemo {

	public static void main(String[] args) {

		log.info("Testing Espeak on EV3Brick");

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
