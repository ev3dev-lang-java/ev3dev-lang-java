package ev3dev.actuators;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.Interpolation;
import ev3dev.utils.Shell;
import lejos.utility.Delay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(Sound.class);

    private static final String EV3_PHYSICAL_SOUND_PATH = "/sys/devices/platform/snd-legoev3";
    public  static final String EV3DEV_SOUND_KEY = "EV3DEV_SOUND_KEY";
    private static String EV3_SOUND_PATH;

    private static final String CMD_BEEP = "beep";
    public  static final String VOLUME = "volume";

    private static String VOLUME_PATH;
    private final static  String DISABLED_FEATURE_MESSAGE = "This feature is disabled for this platform.";

    private Clip clip;

    private static Sound instance;

    /**
     * Return a Instance of Sound.
     *
     * @return A Sound instance
     */
    public static Sound getInstance() {

        LOGGER.info("Providing a Sound instance");

        if (Objects.isNull(instance)) {
            instance = new Sound();
        }
        return instance;
    }

    // Prevent duplicate objects
    private Sound() {

        LOGGER.info("Creating a instance of Sound");

        EV3_SOUND_PATH  = Objects.nonNull(System.getProperty(EV3DEV_SOUND_KEY)) ? System.getProperty(EV3DEV_SOUND_KEY) : EV3_PHYSICAL_SOUND_PATH;
        VOLUME_PATH = EV3_SOUND_PATH + "/" + VOLUME;

        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Beeps once.
     */
    public void beep() {
        if(this.getPlatform().equals(EV3DevPlatform.EV3BRICK)){
            LOGGER.debug(CMD_BEEP);
            Shell.execute(CMD_BEEP);
            Delay.msDelay(100);
        } else {
            LOGGER.warn(DISABLED_FEATURE_MESSAGE);
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
            LOGGER.debug(DISABLED_FEATURE_MESSAGE);
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
            LOGGER.debug(DISABLED_FEATURE_MESSAGE);
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
            LOGGER.debug(DISABLED_FEATURE_MESSAGE);
        }
    }

    /**
     * Play a wav file. Must be mono, from 8kHz to 48kHz, and 8-bit or 16-bit.
     * @param file the 8-bit or 16-bit PWM (WAV) sample file
     * @param volume the volume percentage 0 - 100
     */
    public void playSample(final File file, final int volume) {
        this.setVolume(volume);
        this.playSample(file);
    }


    /**
     * Play a wav file. Must be mono, from 8kHz to 48kHz, and 8-bit or 16-bit.
     * @param file the 8-bit or 16-bit PWM (WAV) sample file
     */
    public void playSample(final File file) {
        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL())) {

            clip.open(audioIn);
            clip.start();
            Delay.usDelay(clip.getMicrosecondLength());
            clip.close();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Set the master volume level
     * @param volume 0-100
     */
    public void setVolume(final int volume) {

        final Mixer.Info [] mixers = AudioSystem.getMixerInfo();
        for (Mixer.Info mixerInfo : mixers) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            Line.Info [] lineInfos = mixer.getTargetLineInfo(); // target, not source
            for (Line.Info lineInfo : lineInfos) {
                Line line = null;
                boolean opened = true;
                try {
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;
                    if (!opened) {
                        line.open();
                    }
                    FloatControl volumeControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                    volumeControl.setValue(convertToDbs(volume));
                } catch (LineUnavailableException | IllegalArgumentException e) {
                    LOGGER.error(e.getLocalizedMessage(), e);
                } finally {
                    if (line != null && !opened) {
                        line.close();
                    }
                }
            }
        }
    }

    /**
     * 100 - 1.0
     * 30 - x
     * 0 - 0.0
     */
    private float convertToDbs(int volume) {

        final float x = 40;
        final float x0 = 100.0f;
        final float x1 = 0.0f;

        final float y0 = 1.0f;
        final float y1 = 0.0f;

        return Interpolation.interpolate(volume, x0, x1, y0, y1);
    }

    /**
     * Get the current master volume level
     * @return the current master volume 0-100
     */
    public int getVolume() {
        final Mixer.Info [] mixers = AudioSystem.getMixerInfo();
        for (Mixer.Info mixerInfo : mixers) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            Line.Info [] lineInfos = mixer.getTargetLineInfo(); // target, not source
            for (Line.Info lineInfo : lineInfos) {
                Line line = null;
                boolean opened = true;
                try {
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;
                    if (!opened) {
                        line.open();
                    }
                    FloatControl volumeControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                    return convertToVolume(volumeControl.getValue());
                } catch (LineUnavailableException | IllegalArgumentException e) {
                    LOGGER.error(e.getLocalizedMessage(), e);
                } finally {
                    if (line != null && !opened) {
                        line.close();
                    }
                }
            }
        }

        throw new RuntimeException("Something goes wrong");
    }

    /**
     * 1.0 - 100
     * 0.4 - x
     * 0.0 - 0
     */
    private int convertToVolume(float db) {

        final float x = db;
        final float x0 = 1.0f;
        final float x1 = 0.0f;

        final float y0 = 100.0f;
        final float y1 = 0.0f;

        return Math.round(Interpolation.interpolate(x, x0, x1, y0, y1));
    }

}
