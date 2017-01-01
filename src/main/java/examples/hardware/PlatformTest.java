package examples.hardware;

import ev3dev.hardware.Platform;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlatformTest {

	public static void main(String[] args) {
		final Platform platform = new Platform();
		final String value = platform.getPlatform();
		log.info("Using the platform: {}", value);
	}

}
