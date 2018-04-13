package fake_ev3dev.ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FakeEV3UltrasonicSensor extends FakeLegoSensor {

    public FakeEV3UltrasonicSensor(EV3DevPlatform ev3DevPlatform) throws IOException {
        super(ev3DevPlatform);

        populateValues(Arrays.asList(20));

        Path mode = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        SENSOR1 + "/" +
                        SENSOR_MODE);

        createFile(mode);
    }

    public void setListenMode() throws IOException {

        Path value0 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        SENSOR1 + "/" +
                        VALUE0);
        createFile(value0, String.valueOf(0));

    }
}
