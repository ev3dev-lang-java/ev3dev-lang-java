package fake_ev3dev.ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FakeEV3GyroSensor extends FakeLegoSensor {

    public FakeEV3GyroSensor(EV3DevPlatform ev3DevPlatform) throws IOException {
        super(ev3DevPlatform);

        populateValues(Arrays.asList(10, 10));

        Path rawData = Paths.get(SENSOR1_BASE, "bin_data");
        createFile(rawData, new byte[]{
            0x10, 0x00, // first value
            0x10, 0x00 // second value
        });
    }
}
