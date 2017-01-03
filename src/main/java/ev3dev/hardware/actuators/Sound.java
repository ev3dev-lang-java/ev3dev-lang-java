package ev3dev.hardware.actuators;

import ev3dev.hardware.DeviceNotSupportedException;
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
 * @author Juan Antonio Breña Moral
 *
 */
public @Slf4j class Sound extends EV3DevDevice {

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
    	if(!this.getPlatform().equals(SupportedPlatform.EV3BRICK)){
    		throw new DeviceNotSupportedException("This device is not supported in this platform");
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
    public void playTone(int frequency, int duration, int volume) {
        Sysfs.writeString(VOLUME_PATH,"" + volume);
    	this.playTone(frequency, duration);
    }
    
    /**
     * Plays a tone, given its frequency and duration. 
     * @param frequency The frequency of the tone in Hertz (Hz).
     * @param duration The duration of the tone, in milliseconds.
     */
    public void playTone(int frequency, int duration) {
        final String cmdTone = CMD_BEEP + " -f " + frequency + " -l " + duration;
        Shell.execute(cmdTone);
    }

    /**
     * Play a wav file. Must be mono, from 8kHz to 48kHz, and 8-bit or 16-bit.
     * @param file the 8-bit or 16-bit PWM (WAV) sample file
     * @param vol the volume percentage 0 - 100
     * @return The number of milliseconds the sample will play for or less 0 if
     *         there is an error.
     */
    public int playSample(File file, int vol) {
        Sysfs.writeInteger(VOLUME_PATH, vol);
    	Shell.execute(CMD_APLAY + " " + file.toString());
        return 1;
    }


    /**
     * Play a wav file. Must be mono, from 8kHz to 48kHz, and 8-bit or 16-bit.
     * @param file the 8-bit or 16-bit PWM (WAV) sample file
     * @return The number of milliseconds the sample will play for or less 0 if
     *         there is an error.
     */
    public int playSample(final File file) {
    	Shell.execute(CMD_APLAY + " " + file.toString());
        return 1;//audio.playSample(file);
    }

    /**
     * Set the master volume level
     * @param vol 0-100
     */
    public void setVolume(int vol) {
        Sysfs.writeString(VOLUME_PATH,"" + vol);
    }

    /**
     * Get the current master volume level
     * @return the current master volume 0-100
     */
    public int getVolume() {
        return Sysfs.readInteger(VOLUME_PATH);
    }

}
