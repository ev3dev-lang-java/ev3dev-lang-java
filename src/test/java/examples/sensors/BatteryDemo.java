package examples.sensors;

import ev3dev.sensors.Battery;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BatteryDemo {

	public static void main(String[] args) {
        final Battery battery = Battery.getInstance();

		for(int x = 0; x < 50; x++){
			log.info("Battery Voltage: {}", battery.getVoltage());
			log.info("Battery Current: {}", battery.getBatteryCurrent());
			Delay.msDelay(1000);
		}
	}

}
