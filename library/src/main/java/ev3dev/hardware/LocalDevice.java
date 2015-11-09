package ev3dev.hardware;

import java.io.File;
import java.util.ArrayList;


public class LocalDevice {

    public static File getMotorPort(String portName) {
    	
    	final String tachoMotorPath = "/sys/class/tacho-motor/";
    	ArrayList<File> motorPorts = Sysfs.getElements(tachoMotorPath);
    	
    	File pathMotor = null;
    	String pathMotorName = "";
    	for(int x=0; x < motorPorts.size(); x++) {
    		pathMotor = motorPorts.get(x);
    		pathMotorName = pathMotor + "/port_name";
    		if (Sysfs.readString(pathMotorName).equals(portName)){
    			return pathMotor;
    		}
    	}

    	throw new IllegalArgumentException("No such port " + portName);
        
    }
	
}
