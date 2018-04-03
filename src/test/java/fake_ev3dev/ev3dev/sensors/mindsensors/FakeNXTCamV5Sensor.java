package fake_ev3dev.ev3dev.sensors.mindsensors;

import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.Sysfs;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeNXTCamV5Sensor extends FakeLegoSensor {

    public FakeNXTCamV5Sensor(EV3DevPlatform ev3DevPlatform) throws IOException {
        super(ev3DevPlatform);

        //TODO Think in some Fake method to populate values on Sensors
        Path value0 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        SENSOR1 + "/" +
                        VALUE0);
        createFile(value0, "2");

        Path value1 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        SENSOR1 + "/" +
                        VALUE1);
        createFile(value1, "100");

        Path value2 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        SENSOR1 + "/" +
                        VALUE2);
        createFile(value2, "200");

        //TODO It is possible to generalize a bit more
        Path mode = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        SENSOR1 + "/" +
                        SENSOR_MODE);
        createFile(mode);

        Path command = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        SENSOR1 + "/" +
                        SENSOR_COMMAND);
        createFile(command);

    }


    public String getCurrentCommand() {
        return Sysfs.readString(EV3DEV_FAKE_SYSTEM_PATH + "/" +
                LEGO_SENSOR_PATH + "/" +
                SENSOR1 + "/" +
                SENSOR_COMMAND);
    }
}
