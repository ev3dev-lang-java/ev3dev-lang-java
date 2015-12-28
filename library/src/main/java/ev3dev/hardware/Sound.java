package ev3dev.hardware;

import java.io.*;

import lejos.utility.Delay;

/**
 * Class that provides access methods for the local audio device
 * 
 * @author Juan Antonio Breña Moral
 *
 */
public class Sound {

    public final static String SOUND_PATH = "/sys/devices/platform/snd-legoev3/";
    public final static String TONE_PATH = SOUND_PATH + "tone";
    public final static String VOLUME_PATH = SOUND_PATH + "volume";
    public final static String CMD_BEEP = "beep";
    public final static String CMD_APLAY ="aplay";
    
    private Sound() {
    }
    
    /**
     * Beeps once.
     */
    public static void beep()
    {
        Shell.execute(CMD_BEEP);
    }

    /**
     * Beeps twice.
     */
    public static void twoBeeps()
    {
        Shell.execute(CMD_BEEP);
        Shell.execute(CMD_BEEP);
    }


    public static void pause(int t)
    {
        Delay.msDelay(t);
    }

    /**
     * Plays a tone, given its frequency and duration. 
     * @param aFrequency The frequency of the tone in Hertz (Hz).
     * @param aDuration The duration of the tone, in milliseconds.
     * @param aVolume The volume of the playback 100 corresponds to 100%
     */
    public static void playTone(int aFrequency, int aDuration, int aVolume) {
    	Sysfs.writeString(VOLUME_PATH,"" + aVolume);
    	String cmd2 = " " + aFrequency + " " + aDuration;
    	Sysfs.writeString(TONE_PATH,cmd2);
    	Delay.msDelay(aDuration);
    }
    
    /**
     * Plays a tone, given its frequency and duration. 
     * @param freq The frequency of the tone in Hertz (Hz).
     * @param duration The duration of the tone, in milliseconds.
     */
    public static void playTone(int aFrequency, int aDuration) {
    	String cmd2 = " " + aFrequency + " " + aDuration;
    	Sysfs.writeString(TONE_PATH,cmd2);
    	Delay.msDelay(aDuration);
    }

    /**
     * Play a wav file. Must be mono, from 8kHz to 48kHz, and 8-bit or 16-bit.
     * @param file the 8-bit or 16-bit PWM (WAV) sample file
     * @param vol the volume percentage 0 - 100
     * @return The number of milliseconds the sample will play for or < 0 if
     *         there is an error.
     * @throws FileNotFoundException 
     */
    public static int playSample(File file, int vol) {
    	Sysfs.writeString(VOLUME_PATH,"" + vol);
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
    public static int playSample(File file) {
    	Shell.execute(CMD_APLAY + " " + file.toString());
        return 1;//audio.playSample(file);
    }

    /**
     * Set the master volume level
     * @param vol 0-100
     */
    public static void setVolume(int vol) {
    	Sysfs.writeString(VOLUME_PATH,"" + vol);
    }

    /**
     * Get the current master volume level
     * @return the current master volume 0-100
     */
    public static int getVolume() {
        return Integer.parseInt(Sysfs.readString(VOLUME_PATH));
    }

}
