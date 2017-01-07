package ev3dev.robotics.tts;

import ev3dev.utils.Shell;
import lombok.extern.slf4j.Slf4j;

/**
 * Espeak wrapper
 */
public @Slf4j class Espeak {

	private static final String ESPEAK = "espeak";
	private final static String CMD_APLAY ="aplay";
	private String voice = null;
	private int volume = -1;
	private int speedReading = -1;
	private int pitch = -1;
	private String message = null;
	private String filePath = null;
	private String command = null;
	
	public void setVoice(final String voice){
		this.voice = voice;
	}
	
	public void setVolume(final int volume){
		this.volume = volume;
	}
	
	public void setSpeedReading(final int speed){
		this.speedReading = speed;
	}
	
	public void setPitch(final int pitch){
		this.pitch = pitch;
	}
	
	public void setMessage(final String message){
		this.message = message;
	}	

	public void setFilePath(final String filePath){
		this.filePath = filePath;
	}

	//TODO Evolve to builder
	private void build(){
		StringBuilder sb = new StringBuilder();
		sb.append(ESPEAK);
		sb.append(" ");
		if(this.voice != null){
			sb.append(" -v ").append(this.voice);
		}
		if(this.volume != -1){
			sb.append(" -a ").append(this.volume);
		}
		if(this.speedReading != -1){
			sb.append(" -s ").append(this.speedReading);
		}
		if(this.pitch != -1){
			sb.append(" -p ").append(this.pitch);
		}
		if(message != null){
			sb.append(" --stdout ");
			sb.append("\"").append(this.message).append("\"");
		}else{
			sb.append("--stdout ");
			sb.append(" -f ").append(this.filePath).append("\"");
		}
		sb.append(" | ");
		sb.append(CMD_APLAY);
		this.command = sb.toString();
	}
	
	//espeak -ves -a 200 -s 200 -p 50  --stdout -f quijote.txt | aplay
	//espeak -ves --stdout "soy un robot bueno" | aplay
	public void say(){
		this.build();
		final String program = "/bin/sh";
		final String flag = "-c";
		final String[] cmd = { program, flag, this.command };
		log.debug("Command: {} {} {}", program, flag, this.command);
		Shell.execute(cmd);
	}

}
