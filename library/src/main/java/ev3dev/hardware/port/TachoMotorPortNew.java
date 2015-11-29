package ev3dev.hardware.port;

import ev3dev.hardware.DeviceNew;
import ev3dev.hardware.DeviceException;

/*
 * 
 */
public class TachoMotorPortNew {

    private final String SYSTEM_CLASS_NAME = "tacho-motor";
	private DeviceNew internalDevice = null;
	
	public TachoMotorPortNew(String motorPort){
		try {
			internalDevice = new DeviceNew(SYSTEM_CLASS_NAME, motorPort);
		} catch (DeviceException e) {
			e.printStackTrace();
		} 
	}
}
