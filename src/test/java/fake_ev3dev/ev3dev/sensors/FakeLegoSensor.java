package fake_ev3dev.ev3dev.sensors;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.BaseElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeLegoSensor extends BaseElement {

    protected static final String LEGO_SENSOR_PATH = "lego-sensor";

    public FakeLegoSensor(final EV3DevPlatform ev3DevPlatform) throws IOException {

        Path batteryPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH);
        Files.createDirectories(batteryPath);
        System.out.println(Files.exists(batteryPath));

        if(ev3DevPlatform.equals(EV3DevPlatform.EV3BRICK)) {
            Path port1 = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LEGO_SENSOR_PATH + "/" +
                            "sensor1"
            );

            Files.createDirectories(port1);
            System.out.println(Files.exists(batteryPath));

            Path addressPath = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LEGO_SENSOR_PATH + "/" +
                            "sensor1" + "/" +
                            "address");
            Files.createFile(addressPath);

            //Review real content to simulate better
            Files.write(addressPath, "in1".getBytes());
        }
    }

}
