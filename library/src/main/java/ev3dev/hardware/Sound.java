package ev3dev.hardware;

import java.io.*;

import lejos.utility.Delay;

/**
 * Class that provides access methods for the local audio device
 * 
 * @author Juan Antonio Breña Moral
 *
 */
public class Sound implements Sounds {

    public final static String SOUND_PATH = "/sys/devices/platform/snd-legoev3";
    public final static String CMD_BEEP = "beep";
    
    private Sound() {
    }
    
    public static int C2 = 523;

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

    /**
     * Downward tones.
     */
    public static void beepSequence()
    {
        //systemSound(true, DESCENDING);
    }

    /**
     * Upward tones.
     */
    public static void beepSequenceUp()
    {
        //systemSound(true, ASCENDING);
    }

    /**
     * Low buzz 
     */
    public static void buzz()
    {
        //systemSound(true, BUZZ);
    }

    public static void pause(int t)
    {
        Delay.msDelay(t);
    }

    /**
     * Returns the number of milliseconds remaining of the current tone or sample.
     * @return milliseconds remaining
     */
    public static int getTime()
    {
        return 0;
    }
    

    /**
     * Plays a tone, given its frequency and duration. 
     * @param aFrequency The frequency of the tone in Hertz (Hz).
     * @param aDuration The duration of the tone, in milliseconds.
     * @param aVolume The volume of the playback 100 corresponds to 100%
     */
    public static void playTone(int aFrequency, int aDuration, int aVolume)
    {
        //audio.playTone(aFrequency, aDuration, aVolume);
    }
    
    /**
     * Plays a tone, given its frequency and duration. 
     * @param freq The frequency of the tone in Hertz (Hz).
     * @param duration The duration of the tone, in milliseconds.
     */
    public static void playTone(int freq, int duration)
    {
        //audio.playTone(freq, duration);
    }

    /**
     * Play a wav file. Must be mono, from 8kHz to 48kHz, and 8-bit or 16-bit.
     * @param file the 8-bit or 16-bit PWM (WAV) sample file
     * @param vol the volume percentage 0 - 100
     * @return The number of milliseconds the sample will play for or < 0 if
     *         there is an error.
     * @throws FileNotFoundException 
     */
    public static int playSample(File file, int vol)
    {
        return 1;//audio.playSample(file, vol);
    }


    /**
     * Play a wav file. Must be mono, from 8kHz to 48kHz, and 8-bit or 16-bit.
     * @param file the 8-bit or 16-bit PWM (WAV) sample file
     * @return The number of milliseconds the sample will play for or < 0 if
     *         there is an error.
     * @throws FileNotFoundException 
     */
    public static int playSample(File file)
    {
        return 1;//audio.playSample(file);
    }


    /**
     * Queue a series of PCM samples to play at the
     * specified volume and sample rate.
     *  
     * @param data Buffer containing the samples
     * @param offset Offset of the first sample in the buffer
     * @param len Number of samples to queue
     * @param freq Sample rate
     * @param vol playback volume
     * @return Number of samples actually queued
     */
    public static int playSample(byte [] data, int offset, int len, int freq, int vol)
    {
        return 1;//audio.playSample(data, offset, len, freq, vol);

    }

    /**
     * <p>Play a note with attack, decay, sustain and release shape, which is known as
     * a ADSR envelope. This function plays a more musical sounding note compared to 
     * playTone(). It uses a set of supplied "instrument" parameters to define the 
     * shape of the note's envelope.</p>
     * 
     * <p>Instruments are defined in the Sounds interface, which is inherited by this class.
     * For example, the piano instrument array looks like this: </p>
     * <code>public final static int[] PIANO = new int[]{4, 25, 500, 7000, 5};</code>
     * 
     * <p>The parameter len is the total time of the note to be played
     * val 0 is the attack time the time for the volume to go from near zero to 100%
     * val 1 is the decay time during this period the volume decreases by val[2]/100 
     * to the sustain level.
     * the sustain period is (len - val[0] - val[1] - val[4]) during this period the 
     * volume decreases by val[3]/100.
     * the final decay period is val[4] and the volume reduces to zero.
     * All of the times are units of 2mS (except for len which is in mS).</p>
     * <p>Because values equal 2ms, the piano array numbers are doubled:<br>
     * 1. take 8mS to increase the volume from zero to 100%<br>
     * 2. take 50mS to decrese the volume by 5%<br>
     * 3. take len - 8 - 25 - 10 mS to decrease the volume by 70%<br>
     * 4. take 10mS to decrease the volume to 0</p>
     * 
     * @param inst Instrument definition (5 ints in an array).
     * @param freq The note to play (in Hz)
     * @param len  The duration of the note (in ms)
     */
    public static void playNote(int[] inst, int freq, int len)
    {
        //audio.playNote(inst, freq, len);

    }

    /**
     * Set the master volume level
     * @param vol 0-100
     */
    public static void setVolume(int vol)
    {
        //audio.setVolume(vol);
    }

    /**
     * Get the current master volume level
     * @return the current master volume 0-100
     */
    public static int getVolume()
    {
        return 2;//audio.getVolume();
    }
    
    /**
     * Load the current system settings associated with this class. Called
     * automatically to initialize the class. May be called if it is required
     * to reload any settings.
     */
    public static void loadSettings()
    {
        //audio.loadSettings();
    }
}
