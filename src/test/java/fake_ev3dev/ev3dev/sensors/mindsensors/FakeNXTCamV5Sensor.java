package fake_ev3dev.ev3dev.sensors.mindsensors;

import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.Sysfs;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

public class FakeNXTCamV5Sensor extends FakeLegoSensor {

    public FakeNXTCamV5Sensor(EV3DevPlatform ev3DevPlatform) throws IOException {
        super(ev3DevPlatform);

        populateValues(Arrays.asList(1, 1, 100, 100, 200, 200));
    }


    public String getCurrentCommand() {
        return Sysfs.readString(Paths.get(SENSOR1_BASE, SENSOR_COMMAND).toString());
    }
}
