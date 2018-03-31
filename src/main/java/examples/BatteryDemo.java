package examples;

import ev3dev.hardware.EV3DevPlatform;
import ev3dev.hardware.EV3DevPlatforms;
import ev3dev.sensors.Battery;
import lejos.utility.Delay;

public class BatteryDemo extends EV3DevPlatforms {

	public static void main(String[] args) {

		BatteryDemo example = new BatteryDemo();

        final Battery battery = Battery.getInstance();

		for(int x = 0; x < 20; x++){
			System.out.println("Battery Voltage: " + battery.getVoltage());

			if(example.getPlatform().equals(EV3DevPlatform.EV3BRICK)) {
				System.out.println("Battery Current: " + battery.getBatteryCurrent());
			}

			Delay.msDelay(1000);
		}
	}

}
