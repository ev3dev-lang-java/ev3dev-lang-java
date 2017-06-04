package ev3dev.actuators;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.Shell;
import ev3dev.utils.Sysfs;
import lejos.utility.Delay;
import org.slf4j.Logger;

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
public class Sound extends EV3DevDevice {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Sound.class);

    private final static String SOUND_PATH = "/sys/devices/platform/snd-legoev3/";
    private final static String CMD_BEEP = "beep";
    private final static String CMD_APLAY ="aplay";
    private final static String VOLUME = "volume";
    private final static String VOLUME_PATH = SOUND_PATH + VOLUME;
    private final static  String DISABLED_FEATURE_MESSAGE = "This feature is disabpled for this platform.";

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

    }
    
    /**
     * Beeps once.
     */
    public void beep() {
        if(this.getPlatform().equals(EV3DevPlatform.EV3BRICK)){
            log.debug(CMD_BEEP);
            Shell.execute(CMD_BEEP);
            Delay.msDelay(100);
        } else {
            log.debug(DISABLED_FEATURE_MESSAGE);
        }
    }

    /**
     * Beeps twice.
     */
    public void twoBeeps() {
        if(this.getPlatform().equals(EV3DevPlatform.EV3BRICK)){
            beep();
            beep();
        } else {
            log.debug(DISABLED_FEATURE_MESSAGE);
        }
    }

    /**
     * Plays a tone, given its frequency and duration. 
     * @param frequency The frequency of the tone in Hertz (Hz).
     * @param duration The duration of the tone, in milliseconds.
     * @param volume The volume of the playback 100 corresponds to 100%
     */
    public void playTone(final int frequency, final int duration, final int volume) {
        if(this.getPlatform().equals(EV3DevPlatform.EV3BRICK)){
            this.setVolume(volume);
    	    this.playTone(frequency, duration);
        } else {
            log.debug(DISABLED_FEATURE_MESSAGE);
        }
    }
    
    /**
     * Plays a tone, given its frequency and duration. 
     * @param frequency The frequency of the tone in Hertz (Hz).
     * @param duration The duration of the tone, in milliseconds.
     */
    public void playTone(final int frequency, final int duration) {
        if(this.getPlatform().equals(EV3DevPlatform.EV3BRICK)) {
            final String cmdTone = CMD_BEEP + " -f " + frequency + " -l " + duration;
            Shell.execute(cmdTone);
        } else {
            log.debug(DISABLED_FEATURE_MESSAGE);
        }
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
        //TODO Review to move to this.setIntegerAttribute();
        Sysfs.writeString(VOLUME_PATH,"" + volume);
    }

    /**
     * Get the current master volume level
     * @return the current master volume 0-100
     */
    public int getVolume() {
        //TODO Review to move to this.getIntegerAttribute()
        return Sysfs.readInteger(VOLUME_PATH);
    }

}
