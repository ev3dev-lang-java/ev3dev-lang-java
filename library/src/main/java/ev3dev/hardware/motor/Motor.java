package ev3dev.hardware.motor;

import ev3dev.hardware.Device;
import ev3dev.hardware.DeviceException;

public class Motor {

    private final String SYSTEM_CLASS_NAME = "tacho-motor";
	private Device internalDevice = null;
	
	public Motor(String motorPort){
		try {
			internalDevice = new Device(SYSTEM_CLASS_NAME, motorPort);
		} catch (DeviceException e) {
			e.printStackTrace();
		} 
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
