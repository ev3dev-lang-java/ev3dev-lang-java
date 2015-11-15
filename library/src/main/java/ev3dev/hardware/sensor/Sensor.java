package ev3dev.hardware.sensor;

import ev3dev.hardware.Device;
import ev3dev.hardware.DeviceException;

public class Sensor {

    private final String SYSTEM_CLASS_NAME = "lego-sensor";
	protected Device internalDevice = null;
	
	public Sensor(String sensorPort){
		try {
			internalDevice = new Device(SYSTEM_CLASS_NAME, sensorPort);
		} catch (DeviceException e) {
			e.printStackTrace();
		}
	}
	
}
