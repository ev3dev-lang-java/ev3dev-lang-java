package fake_ev3dev.ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeEV3UltrasonicSensor extends FakeLegoSensor {

    public FakeEV3UltrasonicSensor(EV3DevPlatform ev3DevPlatform) throws IOException {
        super(ev3DevPlatform);

        Path addressPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "value0");
        if(!Files.exists(addressPath)) {
            Files.createFile(addressPath);
        }

        //Review real content to simulate better
        Files.write(addressPath, "20".getBytes());

        Path addressPath1 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "mode");

        if(!Files.exists(addressPath1)) {
            Files.createFile(addressPath1);
        }

    }
}
