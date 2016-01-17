package ev3dev.hardware;

public class BatteryFactory {

	public static Power getBattery(final String criteria) {

		if ( criteria.equals("Default") ) {
			return SystemBattery.getInstance();
	    } else {
	    	return SystemBattery.getInstance();
	    }

	}

}