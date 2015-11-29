package ev3dev.hardware.sensor;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.DeviceException;

public class Sensor {

    private final String SYSTEM_CLASS_NAME = "lego-sensor";
	protected EV3DevDevice internalDevice = null;
	
	public Sensor(String sensorPort){
		try {
			internalDevice = new EV3DevDevice(SYSTEM_CLASS_NAME, sensorPort);
		} catch (DeviceException e) {
			e.printStackTrace();
		}
	}
	
}
