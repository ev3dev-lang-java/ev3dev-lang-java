package ev3dev.hardware;

import java.io.File;
import java.util.ArrayList;

public class EV3DevDevice extends Device {

    private final String DEVICE_ROOT_PATH = "/sys/class";
    private final String DEVICE_PORT_NAME = "port_name";
    private boolean connected = false;    

	private File pathDevice = null;
	
    public EV3DevDevice(final String type, final String portName) throws DeviceException {
    	
    	this.connect(type, portName);

    	if(this.connected == false){
    		throw new DeviceException("The device was not detected in: " + portName);
    	}
    }
    
    public void connect(final String type, final String portName){

    	final String devicePath = DEVICE_ROOT_PATH + "/" + type;
    	ArrayList<File> deviceAvailables = Sysfs.getElements(devicePath);

    	String pathDeviceName = "";
    	for(int x=0; x < deviceAvailables.size(); x++) {
    		pathDevice = deviceAvailables.get(x);
    		pathDeviceName = pathDevice + "/" + DEVICE_PORT_NAME;
    		if (Sysfs.readString(pathDeviceName).equals(portName)){
    			this.connected = true;
    			break;
    		}
    	}
    }
    
    public String getAttribute(String attribute){
        return Sysfs.readString(pathDevice + "/" +  attribute);
    }
    
    public void setAttribute(String attribute, String value){
    	Sysfs.writeString(this.pathDevice + "/" +  attribute, value);
    }
    
    public File getPathDevice(){
    	return this.pathDevice;
    }
   
}
