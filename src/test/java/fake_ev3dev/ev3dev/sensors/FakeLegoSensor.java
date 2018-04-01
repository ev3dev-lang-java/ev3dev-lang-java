package fake_ev3dev.ev3dev.sensors;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.BaseElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeLegoSensor extends BaseElement {

    protected static final String LEGO_SENSOR_PATH = "lego-sensor";
    protected static final String LEGO_PORT_PATH = "lego-port";

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
        } else {
            Path portPath = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LEGO_PORT_PATH);
            Files.createDirectories(portPath);
            System.out.println(Files.exists(portPath));

            Path port1 = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LEGO_PORT_PATH + "/" +
                            "port1"
            );

            Files.createDirectories(port1);
            System.out.println(Files.exists(batteryPath));

            Path addressPath = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LEGO_PORT_PATH + "/" +
                            "port1" + "/" +
                            "address");
            Files.createFile(addressPath);

            //Review real content to simulate better
            Files.write(addressPath, "spi0.1:S1".getBytes());

            Path modePath = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LEGO_PORT_PATH + "/" +
                            "port1" + "/" +
                            "mode");
            Files.createFile(modePath);

            //Lego Sensors
            Path legoSensorPath = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LEGO_SENSOR_PATH + "/" +
                            "sensor1"
            );

            Files.createDirectories(legoSensorPath);
            System.out.println(Files.exists(legoSensorPath));

            Path legoSensorAddressPath = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LEGO_SENSOR_PATH + "/" +
                            "sensor1" + "/" +
                            "address");
            Files.createFile(legoSensorAddressPath);

            //Review real content to simulate better
            Files.write(legoSensorAddressPath, "spi0.1:S1".getBytes());


        }
    }

}
