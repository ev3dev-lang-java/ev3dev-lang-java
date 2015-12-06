package ev3dev.hardware;

public class Battery {

	private final static String DEVICE_ROOT_PATH = "/sys/class";
	private final static String batteryPath = DEVICE_ROOT_PATH + "/power_supply/legoev3-battery";

	public static int getVoltageMilliVolt() {
		final String attribute = "voltage_now";
		return Sysfs.readInteger(batteryPath + "/" +  attribute);
	}

	public static float getVoltage() {
		final String attribute = "voltage_now";
		return Sysfs.readInteger(batteryPath + "/" +  attribute);
	}

	public float getBatteryCurrent() {
		final String attribute = "current_now";
		return Sysfs.readInteger(batteryPath + "/" +  attribute);
	}

	public float getMotorCurrent() {
		return -99.0f;//power.getMotorCurrent();
	}
}
