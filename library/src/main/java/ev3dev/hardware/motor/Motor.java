package ev3dev.hardware.motor;

import ev3dev.hardware.Device;

public class Motor {

    private final String SYSTEM_CLASS_NAME = "tacho-motor";
	private Device internalDevice = null;
	
	public Motor(String motorPort){
		internalDevice = new Device(SYSTEM_CLASS_NAME, motorPort); 
	}
	
	public void setSpeed(int speed){
		final String attribute = "/duty_cycle_sp";
		internalDevice.setAttribute(attribute, "" + speed);
	}
	
	public void forward(){
		final String attribute = "command";
		final String value = "run-forever";
		internalDevice.setAttribute(attribute, value);
	}
	
	public void stop(){
		final String attribute = "command";
		final String value = "stop";
		internalDevice.setAttribute(attribute, value);
	}
}
