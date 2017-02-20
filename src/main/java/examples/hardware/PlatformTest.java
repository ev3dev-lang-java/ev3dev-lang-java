package examples.hardware;

import ev3dev.hardware.EV3DevDevice;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class PlatformTest extends EV3DevDevice {

	public static void main(String[] args) {
		final PlatformTest platform = new PlatformTest();
		final String value = platform.getPlatform();
		log.info("Using the platform: {}", value);
	}

}
