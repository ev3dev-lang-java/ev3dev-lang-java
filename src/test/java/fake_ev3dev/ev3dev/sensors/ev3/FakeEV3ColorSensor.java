package fake_ev3dev.ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FakeEV3ColorSensor extends FakeLegoSensor {

    public FakeEV3ColorSensor(EV3DevPlatform ev3DevPlatform) throws IOException {
        super(ev3DevPlatform);

        populateValues(Arrays.asList(2, 100, 200));

        Path mode = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        SENSOR1 + "/" +
                        SENSOR_MODE);

        createFile(mode);
    }
}
