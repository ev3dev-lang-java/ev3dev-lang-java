package fake_ev3dev.ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeEV3IRSensor extends FakeLegoSensor {

    public FakeEV3IRSensor(EV3DevPlatform ev3DevPlatform) throws IOException {
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

        Path addressPath3 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "value2");
        if(!Files.exists(addressPath3)) {
            Files.createFile(addressPath3);
        }

        //Review real content to simulate better
        Files.write(addressPath3, "10".getBytes());

        Path addressPath4 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "value3");
        if(!Files.exists(addressPath4)) {
            Files.createFile(addressPath4);
        }

        //Review real content to simulate better
        Files.write(addressPath4, "10".getBytes());

        Path addressPath5 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "value4");
        if(!Files.exists(addressPath5)) {
            Files.createFile(addressPath5);
        }

        //Review real content to simulate better
        Files.write(addressPath5, "10".getBytes());

        Path addressPath6 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "value5");
        if(!Files.exists(addressPath6)) {
            Files.createFile(addressPath6);
        }

        //Review real content to simulate better
        Files.write(addressPath6, "10".getBytes());

        Path addressPath7 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "value6");
        if(!Files.exists(addressPath7)) {
            Files.createFile(addressPath7);
        }

        //Review real content to simulate better
        Files.write(addressPath7, "10".getBytes());

        Path addressPath8 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "value7");
        if(!Files.exists(addressPath8)) {
            Files.createFile(addressPath8);
        }

        //Review real content to simulate better
        Files.write(addressPath8, "10".getBytes());

        Path addressPathMode = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        "sensor1" + "/" +
                        "mode");

        if(!Files.exists(addressPathMode)) {
            Files.createFile(addressPathMode);
        }

    }
}
