package fake_ev3dev.ev3dev.actuators;

import ev3dev.actuators.Sound;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.BaseElement;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FakeSound extends BaseElement{

    public FakeSound(final EV3DevPlatform ev3DevPlatform) throws IOException {

        LOGGER.info("Adding a Sound device in the FileSystem for: {}", ev3DevPlatform);

        Path volumePath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        Sound.VOLUME);
        createFile(volumePath, "0");
    }

}
