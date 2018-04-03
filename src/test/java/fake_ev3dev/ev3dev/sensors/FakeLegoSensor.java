package fake_ev3dev.ev3dev.sensors;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.BaseElement;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeLegoSensor extends BaseElement {


    public FakeLegoSensor(final EV3DevPlatform ev3DevPlatform) throws IOException {

        Path batteryPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH);
        this.createDirectories(batteryPath);


        if(ev3DevPlatform.equals(EV3DevPlatform.EV3BRICK)) {

            createStructureSensor1("in1");

        } else {

            //BrickPi3
            createStructurePort1("spi0.1:S1");

            createStructureSensor1("spi0.1:S1");

        }
    }

    private void createStructureSensor1(final String inputPort) throws IOException {

        Path sensor1 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        SENSOR1
        );
        this.createDirectories(sensor1);

        Path addressPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_SENSOR_PATH + "/" +
                        SENSOR1 + "/" +
                        SENSOR_ADDRESS);
        this.createFile(addressPath, inputPort);
    }

    private void createStructurePort1(final String inputPort) throws IOException {

        Path portPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH);
        this.createDirectories(portPath);

        Path sensorPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT1
        );
        this.createDirectories(sensorPath);

        Path addressPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT1 + "/" +
                        PORT_ADDRESS);
        this.createFile(addressPath, inputPort);

        Path modePath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT1 + "/" +
                        PORT1_MODE);
        this.createFile(modePath);
    }

}
