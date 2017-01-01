package examples.sensors;

import ev3dev.hardware.sensor.Battery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BatteryDemo {

	public static void main(String[] args) {
		log.info("Battery Voltage: {}", Battery.getInstance().getVoltage());
        log.info("Battery Current: {}", Battery.getInstance().getBatteryCurrent());
	}

}
