package examples.hardware;

import ev3dev.hardware.EV3DevDevice;
import lombok.extern.slf4j.Slf4j;

public @Slf4j class PlatformTest {

	public static void main(String[] args) {
		final EV3DevDevice platform = new EV3DevDevice();
		final String value = platform.getPlatform();
		log.info("Using the platform: {}", value);
	}

}
