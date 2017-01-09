package ev3dev.actuators;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.SupportedPlatform;
import ev3dev.utils.Shell;
import ev3dev.utils.Sysfs;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * Class that provides access methods for the local audio device
 *
 * The class is implemented as Singleton.
 *
 * Note: Only tested with EV3Brick
 *
 * @author Juan Antonio Breña Moral
 *
 */
public @Slf4j class Sound extends EV3DevDevice {

    private final static String SOUND_PATH = "/sys/devices/platform/snd-legoev3/";
    private final static String CMD_BEEP = "beep";
    private final static String CMD_APLAY ="aplay";
    private final static String VOLUME = "volume";
    private final static String VOLUME_PATH = SOUND_PATH + VOLUME;

    private static Sound Instance;

    /**
     * Return a Instance of Sound.
     *
     * @return A Sound instance
     */
    public static Sound getInstance() {
        if (Instance == null) {
        	Instance = new Sound();
        }
        return Instance;
    }

    // Prevent duplicate objects
    private Sound() {
    	if(!this.getPlatform().equals(SupportedPlatform.EV3BRICK)){
    		throw new RuntimeException("This device is not supported in this platform");
    	}
    }
    
    /**
     * Beeps once.
     */
    public void beep() {
        log.debug(CMD_BEEP);
        Shell.execute(CMD_BEEP);
        Delay.msDelay(100);
    }

    /**
     * Beeps twice.
     */
    public void twoBeeps() {
        beep();
        beep();
    }

    /**
     * Plays a tone, given its frequency and duration. 
     * @param frequency The frequency of the tone in Hertz (Hz).
     * @param duration The duration of the tone, in milliseconds.
     * @param volume The volume of the playback 100 corresponds to 100%
     */
    public void playTone(final int frequency, final int duration, final int volume) {
        this.setVolume(volume);
    	this.playTone(frequency, duration);
    }
    
    /**
     * Plays a tone, given its frequency and duration. 
     * @param frequency The frequency of the tone in Hertz (Hz).
     * @param duration The duration of the tone, in milliseconds.
     */
    public void playTone(final int frequency, final int duration) {
        final String cmdTone = CMD_BEEP + " -f " + frequency + " -l " + duration;
        Shell.execute(cmdTone);
    }

    /**
     * Play a wav file. Must be mono, from 8kHz to 48kHz, and 8-bit or 16-bit.
     * @param file the 8-bit or 16-bit PWM (WAV) sample file
     * @param volume the volume percentage 0 - 100
     */
    public void playSample(final File file, final int volume) {
        this.setVolume(volume);
    	Shell.execute(CMD_APLAY + " " + file.toString());
    }


    /**
     * Play a wav file. Must be mono, from 8kHz to 48kHz, and 8-bit or 16-bit.
     * @param file the 8-bit or 16-bit PWM (WAV) sample file
     */
    public void playSample(final File file) {
    	Shell.execute(CMD_APLAY + " " + file.toString());
    }

    /**
     * Set the master volume level
     * @param volume 0-100
     */
    public void setVolume(final int volume) {
        Sysfs.writeString(VOLUME_PATH,"" + volume);
    }

    /**
     * Get the current master volume level
     * @return the current master volume 0-100
     */
    public int getVolume() {
        return Sysfs.readInteger(VOLUME_PATH);
    }

}
