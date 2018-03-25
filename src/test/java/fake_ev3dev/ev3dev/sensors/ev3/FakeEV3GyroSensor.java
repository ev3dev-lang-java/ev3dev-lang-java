package fake_ev3dev.ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.Sysfs;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeEV3GyroSensor extends FakeLegoSensor {

    public FakeEV3GyroSensor(EV3DevPlatform ev3DevPlatform) throws IOException {
        super(ev3DevPlatform);

        Path addressPath1 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "value0");
        if(!Files.exists(addressPath1)) {
            Files.createFile(addressPath1);
        }

        //Review real content to simulate better
        Files.write(addressPath1, "10".getBytes());

        Path addressPath2 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "value1");
        if(!Files.exists(addressPath2)) {
            Files.createFile(addressPath2);
        }

        //Review real content to simulate better
        Files.write(addressPath2, "10".getBytes());

        Path addressPathMode = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "mode");

        if(!Files.exists(addressPathMode)) {
            Files.createFile(addressPathMode);
        }

    }

    public String getCurrentMode() {
        return Sysfs.readString(EV3DEV_FAKE_SYSTEM_PATH + "/" +
                LEGO_SENSOR_PATH + "/" +
                "sensor1" + "/" +
                "mode");
    }
}
