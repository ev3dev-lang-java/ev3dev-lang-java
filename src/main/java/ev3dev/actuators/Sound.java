package ev3dev.actuators;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.EV3DevDistro;
import ev3dev.hardware.EV3DevDistros;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.Shell;
import ev3dev.utils.Sysfs;
import lejos.utility.Delay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.net.URL;
import java.net.MalformedURLException;


/**
 * Class that provides access methods for the local audio device
 *
 * <p>The class is implemented as Singleton.</p>
 *
 * <p>Note: Only tested with EV3Brick</p>
 *
 * <p><b>Sample format notes</b>: the <code>playSample()</code>
 * methods accept sound formats that are playable by the AudioSystem
 * class.  See the platform JavaDocs for details.  However, the
 * current recommendation is to stay with simple formats that are
 * easily decoded (the EV3 does not have a fast CPU).  Mono
 * (single-channel) 16-bit WAV files have been tested at 11, 22, and
 * 44kHz for short clips without issue.  Other formats may work, but
 * note that sound quality can also be affected by CPU load.</p>
 *
 * @author Juan Antonio Breña Moral
 */
public class Sound extends EV3DevDevice {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sound.class);

    private static final String EV3_PHYSICAL_SOUND_PATH = "/sys/devices/platform/snd-legoev3";
    public static final String EV3DEV_SOUND_KEY = "EV3DEV_SOUND_KEY";
    private static String EV3_SOUND_PATH;

    private static final String CMD_BEEP = "beep";
    public static final String VOLUME = "volume";

    private static String VOLUME_PATH;
    private static final String DISABLED_FEATURE_MESSAGE = "This feature is disabled for this platform.";

    private final EV3DevDistro CURRENT_DISTRO;

    private static Sound instance;

    private int volume = 0;

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

        EV3_SOUND_PATH = Objects.nonNull(System.getProperty(EV3DEV_SOUND_KEY))
            ? System.getProperty(EV3DEV_SOUND_KEY) : EV3_PHYSICAL_SOUND_PATH;
        VOLUME_PATH = EV3_SOUND_PATH + "/" + VOLUME;

        CURRENT_DISTRO = EV3DevDistros.getInstance().getDistro();
    }

    /**
     * Beeps once.  Thread execution waits until the beep is complete.
     */
    public void beep() {
        if (CURRENT_PLATFORM.equals(EV3DevPlatform.EV3BRICK)) {
            LOGGER.debug(CMD_BEEP);
            Shell.execute(CMD_BEEP);
            Delay.msDelay(100);
        } else {
            LOGGER.warn(DISABLED_FEATURE_MESSAGE);
        }
    }

    /**
     * Beeps twice.  Thread execution waits until the beeps are complete.
     */
    public void twoBeeps() {
        if (CURRENT_PLATFORM.equals(EV3DevPlatform.EV3BRICK)) {
            beep();
            beep();
        } else {
            LOGGER.debug(DISABLED_FEATURE_MESSAGE);
        }
    }

    /**
     * Convenience method to set the volume, then play a tone.
     *
     * @param frequency The frequency of the tone in Hertz (Hz).
     * @param duration  The duration of the tone, in milliseconds.
     * @param volume    The volume of the playback
     *
     * @see playTone(int, int)
     */
    public void playTone(final int frequency, final int duration, final int volume) {
        if (CURRENT_PLATFORM.equals(EV3DevPlatform.EV3BRICK)) {
            this.setVolume(volume);
            this.playTone(frequency, duration);
        } else {
            LOGGER.debug(DISABLED_FEATURE_MESSAGE);
        }
    }

    /**
     * Plays a tone, given its frequency and duration.  Thread execution
     * waits until the tone is complete.
     *
     * @param frequency The frequency of the tone in Hertz (Hz).
     * @param duration  The duration of the tone, in milliseconds.
     */
    public void playTone(final int frequency, final int duration) {
        if (CURRENT_PLATFORM.equals(EV3DevPlatform.EV3BRICK)) {
            final String cmdTone = CMD_BEEP + " -f " + frequency + " -l " + duration;
            Shell.execute(cmdTone);
        } else {
            LOGGER.debug(DISABLED_FEATURE_MESSAGE);
        }
    }

    /**
     * Convenience method to set the sound volume, then play a sample.
     *
     * @param url    URL to the sample
     * @param volume the volume level
     *
     * @see playSample(URL)
     * @see setVolume(int)
     */
    public void playSample(final URL url, final int volume) {
        this.setVolume(volume);
        this.playSample(url);
    }

    /**
     * Convenience method to set the sound volume, then play a sample.
     *
     * @param file   File path to the sample
     * @param volume the volume level
     *
     * @see playSample(File)
     * @see setVolume(int)
     */
    public void playSample(final File file, final int volume) {
        this.setVolume(volume);
        this.playSample(file);
    }

    /**
     * Convenience method to set the sound volume, then play a sample.
     *
     * @param resource Resource path to the sample
     * @param volume   the volume level
     *
     * @see playSample(String)
     * @see setVolume(int)
     */
    public void playSample(final String resource, final int volume) {
        this.setVolume(volume);
        this.playSample(resource);
    }

    /**
     * Play a sound sample loaded from the provided URL.  See notes in the
     * class documentation for recommended sample formats.
     *
     * @param url URL of the sound sample to play
     */
    public void playSample(final URL url) {
        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(url)) {

            Clip clip = AudioSystem.getClip();
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
     * Play a sound sample loaded from the local filesystem.  See notes in the
     * class documentation for recommended file formats.
     *
     * @param file path to the sample file
     */
    public void playSample(final File file) {
        try {
            playSample(file.toURI().toURL());
        }
        catch (MalformedURLException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>
     * Play a sound sample loaded as a resource from the program's classpath.
     * This allows files bundled inside a JAR to be loaded and played
     * without a dependency on filesystem location.
     * </p>
     *
     * <p>
     * To use this method, bundle a sound resource in the application's
     * classpath.  Then, provide a relative path to the resource as the
     * parameter to this method.  For example, if the file "sample.wav"
     * is at the root level of the application JAR file, you would just
     * pass "sample.wav" as the parameter to this method.
     * </p>
     *
     * @param resource resource name to locate, load, and play
     */
    public void playSample(final String resource) {
        playSample(Sound.class.getClassLoader().getResource(resource));
    }

    /**
     * Play a file from the local file system using the Linux "aplay"
     * utility (rather than native Java).  Any supported file type will
     * play, subject to the limitations of the EV3 CPU.
     *
     * @param file the absolute pathname to the file to play
     */
    public void playAlsa(final String file) {
        Shell.execute(new String[] {"aplay", file});
    }

    /**
     * Set the master volume level, expressed as a percentage from 0 - 100%.
     *
     * @param volume 0-100
     */
    public void setVolume(final int volume) {

        this.volume = volume;

        if (CURRENT_DISTRO.equals(EV3DevDistro.JESSIE)) {
            //TODO Review to move to this.setIntegerAttribute();
            Sysfs.writeString(VOLUME_PATH, "" + volume);
        } else {
            // There are now two output devices, PCM (samples) and
            // "Beep" (tones), so set the volume for both:
            final String[] mixers = {"PCM", "Beep"};
            for (String item : mixers) {
                Shell.execute(new String[]
                    {"amixer", "set", item+",0", ""+volume+"%"});
            }
        }
    }

    /**
     * Get the current master volume level, expressed as a percentage
     * from 0 - 100%.
     *
     * @return the current master volume 0-100
     */
    public int getVolume() {

        if (CURRENT_DISTRO.equals(EV3DevDistro.JESSIE)) {
            //TODO Review to move to this.getIntegerAttribute()
            return Sysfs.readInteger(VOLUME_PATH);
        } else {
            return this.volume;
        }
    }

}
