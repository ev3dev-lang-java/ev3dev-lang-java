package ev3dev.robotics.tts;

import ev3dev.utils.Shell;


public class Espeak {

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
	
	private void build(){
		StringBuilder sb = new StringBuilder();
		sb.append("espeak ");
		if(this.voice != null){
			sb.append(" -v " + this.voice);
		}
		if(this.volume != -1){
			sb.append(" -a " + this.volume);
		}
		if(this.speedReading != -1){
			sb.append(" -s " + this.speedReading);
		}
		if(this.pitch != -1){
			sb.append(" -p " + this.pitch);
		}
		if(message != null){
			sb.append(" --stdout ");
			sb.append("\"" + this.message + "\"");
		}else{
			sb.append("--stdout ");
			sb.append(" -f " + this.filePath + "\"");
		}
		sb.append(" | aplay");
		this.command = sb.toString();
	}
	
	//espeak -ves -a 200 -s 200 -p 50  --stdout -f quijote.txt | aplay
	//espeak -ves --stdout "soy un robot bueno" | aplay
	public void say(){
		this.build();
		String[] cmd = { "/bin/sh", "-c", this.command };
		Shell.execute(cmd);
	}

}
