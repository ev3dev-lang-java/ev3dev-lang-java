package examples;

import ev3dev.utils.JarResource;
import lejos.utility.Delay;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class PlayMP3 {

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        String filePath = "nod_low_power.wav";
        String result = JarResource.export(filePath);
        System.out.println(result);

        AudioInputStream audioIn = AudioSystem
                .getAudioInputStream(
                        new File("/home/robot/nod_low_power.wav").toURI().toURL());

        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
        Delay.usDelay(clip.getMicrosecondLength());
    }

}
