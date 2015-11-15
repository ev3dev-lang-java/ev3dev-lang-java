package ev3dev.hardware;

import java.io.File;
import java.util.ArrayList;

public class Device {

    private final String DEVICE_ROOT_PATH = "/sys/class";
    
    private boolean connected = false;
	private File pathDevice = null;
	
    public Device(String type, String portName) {
    	
    	final String devicePath = DEVICE_ROOT_PATH + "/" + type;
    	ArrayList<File> deviceAvailables = Sysfs.getElements(devicePath);

    	String pathDeviceName = "";
    	for(int x=0; x < deviceAvailables.size(); x++) {
    		pathDevice = deviceAvailables.get(x);
    		pathDeviceName = pathDevice + "/port_name";
    		if (Sysfs.readString(pathDeviceName).equals(portName)){
    			this.connected = true;
    		}
    	}
    	
    }
	
    public boolean isConnected(){
    	return this.connected;
    }
    
    public String getAttribute(String attribute){
        return Sysfs.readString(pathDevice + "/" +  attribute);
    }
    
    public void setAttribute(String attribute, String value){
    	Sysfs.writeString(this.pathDevice + "/" +  attribute,value);
    }
    
}
