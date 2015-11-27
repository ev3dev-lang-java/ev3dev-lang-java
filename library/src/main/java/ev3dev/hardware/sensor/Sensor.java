package ev3dev.hardware.sensor;

import ev3dev.hardware.DeviceNew;
import ev3dev.hardware.DeviceException;

public class Sensor {

    private final String SYSTEM_CLASS_NAME = "lego-sensor";
	protected DeviceNew internalDevice = null;
	
	public Sensor(String sensorPort){
		try {
			internalDevice = new DeviceNew(SYSTEM_CLASS_NAME, sensorPort);
		} catch (DeviceException e) {
			e.printStackTrace();
		}
	}
	
}
