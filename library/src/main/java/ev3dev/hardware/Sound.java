package ev3dev.hardware;

import java.io.*;

import ev3dev.utils.Shell;
import lejos.utility.Delay;

/**
 * Class that provides access methods for the local audio device
 * 
 * @author Juan Antonio Breña Moral
 *
 */
public class Sound extends EV3DevSysfs {

    public final static String SOUND_PATH = "/sys/devices/platform/snd-legoev3/";
    public final static String TONE_PATH = SOUND_PATH + "tone";
    public final static String VOLUME_PATH = SOUND_PATH + "volume";
    public final static String CMD_BEEP = "beep";
    public final static String CMD_APLAY ="aplay";
    
    private static Sound Instance;

    public static Sound getInstance() {
        if (Instance == null) {
        	Instance = new Sound();
        }

        return Instance;
    }

    // Prevent duplicate objects
    private Sound() {

    }
    
    /**
     * Beeps once.
     */
    public void beep() {
        Shell.execute(CMD_BEEP);
        pause(100);
    }

    /**
     * Beeps twice.
     */
    public void twoBeeps() {
        beep();
        beep();
    }


    public void pause(int t) {
        Delay.msDelay(t);
    }

    /**
     * Plays a tone, given its frequency and duration. 
     * @param aFrequency The frequency of the tone in Hertz (Hz).
     * @param aDuration The duration of the tone, in milliseconds.
     * @param aVolume The volume of the playback 100 corresponds to 100%
     */
    public void playTone(int frequency, int duration, int volume) {
    	writeString(VOLUME_PATH,"" + volume);
    	String cmd2 = " " + frequency + " " + duration;
    	writeString(TONE_PATH,cmd2);
    	pause(duration);
    }
    
    /**
     * Plays a tone, given its frequency and duration. 
     * @param freq The frequency of the tone in Hertz (Hz).
     * @param duration The duration of the tone, in milliseconds.
     */
    public void playTone(int frequency, int duration) {
    	String cmd2 = " " + frequency + " " + duration;
    	writeString(TONE_PATH,cmd2);
    	pause(duration);
    }

    /**
     * Play a wav file. Must be mono, from 8kHz to 48kHz, and 8-bit or 16-bit.
     * @param file the 8-bit or 16-bit PWM (WAV) sample file
     * @param vol the volume percentage 0 - 100
     * @return The number of milliseconds the sample will play for or < 0 if
     *         there is an error.
     * @throws FileNotFoundException 
     */
    public int playSample(File file, int vol) {
    	writeInteger(VOLUME_PATH, vol);
    	Shell.execute(CMD_APLAY + " " + file.toString());
        return 1;
    }


    /**
     * Play a wav file. Must be mono, from 8kHz to 48kHz, and 8-bit or 16-bit.
     * @param file the 8-bit or 16-bit PWM (WAV) sample file
     * @return The number of milliseconds the sample will play for or < 0 if
     *         there is an error.
     * @throws FileNotFoundException 
     */
    public int playSample(File file) {
    	Shell.execute(CMD_APLAY + " " + file.toString());
        return 1;//audio.playSample(file);
    }

    /**
     * Set the master volume level
     * @param vol 0-100
     */
    public void setVolume(int vol) {
    	writeString(VOLUME_PATH,"" + vol);
    }

    /**
     * Get the current master volume level
     * @return the current master volume 0-100
     */
    public int getVolume() {
        return readInteger(VOLUME_PATH);
    }

}
