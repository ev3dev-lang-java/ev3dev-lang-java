package ev3dev.hardware.sensor.ev3;

import ev3dev.hardware.sensor.Sensor;

public class IRSensor extends Sensor {

	public IRSensor(String sensorPort) {
		super(sensorPort);
	}

	public int getDistance(){
		String attribute = "value0";
		String rawValue = this.internalDevice.getAttribute(attribute);
		
		int value = -1;
		try {
			value = Integer.parseInt(rawValue);
		} catch (NumberFormatException e) {
			value = -1;			
		}
		
		return value;
	}
	
	
}
