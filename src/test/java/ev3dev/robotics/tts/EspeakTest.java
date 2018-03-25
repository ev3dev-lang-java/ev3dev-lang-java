package ev3dev.robotics.tts;

import org.junit.Test;

public class EspeakTest {

    @Test
    public void playMessageTest(){

        Espeak espeak = new Espeak();
        espeak.setVoice(Espeak.VOICE_SPANISH);
        espeak.setMessage("Soy un robot bueno");
        espeak.setPitch(Espeak.DEFAULT_PITCH);
        espeak.setSpeedReading(Espeak.DEFAULT_SPEED_READING);
        espeak.setVolume(100);
        espeak.say();

    }

    @Test
    public void playFilePathTest(){

        Espeak espeak = new Espeak();
        espeak.setVoice(Espeak.VOICE_SPANISH);
        espeak.setFilePath("./BAD_PATH.wav");
        espeak.setPitch(Espeak.DEFAULT_PITCH);
        espeak.setSpeedReading(Espeak.DEFAULT_SPEED_READING);
        espeak.setVolume(100);
        espeak.say();

    }
}
