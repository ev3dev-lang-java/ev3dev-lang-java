package ev3dev.hardware.sensor.ev3;

import ev3dev.hardware.sensor.BaseSensor;

public class GyroSensor extends BaseSensor {

	public GyroSensor(String sensorPort) {
		super(sensorPort);
	}

	public int getAngle(){
		String attribute = "value0";
		String rawValue = this.getAttribute(attribute);
		
		int value = -1;
		try {
			value = Integer.parseInt(rawValue);
		} catch (NumberFormatException e) {
			value = -1;			
		}
		
		return value;
	}
	
	
}
