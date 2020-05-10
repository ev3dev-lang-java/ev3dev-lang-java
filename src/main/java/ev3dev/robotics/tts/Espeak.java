package ev3dev.robotics.tts;

import ev3dev.utils.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Espeak wrapper
 */
public class Espeak {

    private static final Logger log = LoggerFactory.getLogger(Espeak.class);

    private static final String ESPEAK = "espeak";
    private static final String CMD_APLAY = "aplay";

    public static final String VOICE_ENGLISH = "en";
    public static final String VOICE_SPANISH = "es";
    public static final int DEFAULT_SPEED_READING = 105;
    public static final int DEFAULT_PITCH = 60;

    private String voice = null;
    private int volume = -1;
    private int speedReading = -1;
    private int pitch = -1;
    private String message = null;
    private String filePath = null;
    private String command = null;

    public void setVoice(final String voice) {
        this.voice = voice;
    }

    public void setVolume(final int volume) {
        this.volume = volume;
    }

    public void setSpeedReading(final int speed) {
        this.speedReading = speed;
    }

    public void setPitch(final int pitch) {
        this.pitch = pitch;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setFilePath(final String filePath) {
        this.filePath = filePath;
    }

    //TODO Evolve to builder
    private void build() {
        StringBuilder sb = new StringBuilder();
        sb.append(ESPEAK);
        sb.append(" ");
        //TODO Refactor
        if (this.voice != null) {
            sb.append(" -v ").append(this.voice);
        } else {
            sb.append(" -v ").append(Espeak.VOICE_ENGLISH);
        }
        if (this.volume != -1) {
            sb.append(" -a ").append(this.volume);
        }
        if (this.speedReading != -1) {
            sb.append(" -s ").append(this.speedReading);
        } else {
            sb.append(" -s ").append(this.DEFAULT_SPEED_READING);
        }
        if (this.pitch != -1) {
            sb.append(" -p ").append(this.pitch);
        } else {
            sb.append(" -p ").append(this.DEFAULT_PITCH);
        }
        //TODO Refactor
        if (message != null) {
            sb.append(" --stdout ");
            sb.append("\"").append(this.message).append("\"");
        } else {
            //TODO Refactor
            if (filePath != null) {
                sb.append("--stdout ");
                sb.append(" -f ").append(this.filePath).append("\"");
            } else {
                throw new IllegalArgumentException("Message is null or FilePath.");
            }
        }
        sb.append(" | ");
        sb.append(CMD_APLAY);
        this.command = sb.toString();
    }

    /**
     * Execute the TTS
     *
     * <p>Examples:
     * espeak -ves -a 200 -s 200 -p 50  --stdout -f quijote.txt | aplay
     * espeak -ves --stdout "soy un robot bueno" | aplay
     */
    public void say() {
        this.build();
        final String program = "/bin/sh";
        final String flag = "-c";
        final String[] cmd = {program, flag, this.command};
        log.debug("Command: {} {} {}", program, flag, this.command);
        Shell.execute(cmd);
    }

}
