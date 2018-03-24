package fake_ev3dev.ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeEV3TouchSensor extends FakeLegoSensor {

    public FakeEV3TouchSensor(EV3DevPlatform ev3DevPlatform) throws IOException {
        super(ev3DevPlatform);
    }

    public void pressed() throws IOException {
        Path addressPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "value0");

        if(!Files.exists(addressPath)) {
            Files.createFile(addressPath);
        }

        //Review real content to simulate better
        Files.write(addressPath, "1".getBytes());
    }

    public void unpressed() throws IOException {
        Path addressPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "value0");
        if(!Files.exists(addressPath)) {
            Files.createFile(addressPath);
        }

        //Review real content to simulate better
        Files.write(addressPath, "0".getBytes());
    }
}
