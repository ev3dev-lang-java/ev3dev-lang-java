package examples.sensors;

import ev3dev.actuators.Sound;
import ev3dev.sensors.Battery;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BatteryDemo {

	public static void main(String[] args) {
        final Battery battery = Battery.getInstance();

		for(int x = 0; x < 500; x++){
			log.info("Battery Voltage: {}", battery.getVoltage());
			log.info("Battery Current: {}", battery.getBatteryCurrent());
			Sound.getInstance().beep();
			Delay.msDelay(1000);
		}
	}

}
