package fake_ev3dev.ev3dev.sensors.mindsensors;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeNXTCamV5Sensor extends FakeLegoSensor {

    public FakeNXTCamV5Sensor(EV3DevPlatform ev3DevPlatform) throws IOException {
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
        Files.write(addressPath1, "2".getBytes());

        Path addressPath2 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "value1");
        if(!Files.exists(addressPath2)) {
            Files.createFile(addressPath2);
        }

        //Review real content to simulate better
        Files.write(addressPath2, "100".getBytes());

        Path addressPath3 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "value2");
        if(!Files.exists(addressPath3)) {
            Files.createFile(addressPath3);
        }

        //Review real content to simulate better
        Files.write(addressPath3, "200".getBytes());

        Path addressPathMode = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "mode");

        if(!Files.exists(addressPathMode)) {
            Files.createFile(addressPathMode);
        }

        Files.write(addressPathMode, "TRACK".getBytes());

        Path addressPathCommand = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "command");

        if(!Files.exists(addressPathCommand)) {
            Files.createFile(addressPathCommand);
        }

    }
}
