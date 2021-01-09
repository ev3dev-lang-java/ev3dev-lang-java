package fake_ev3dev.ev3dev.sensors;

import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.Sysfs;
import fake_ev3dev.BaseElement;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeLegoSensor extends BaseElement {

    public FakeLegoSensor(final EV3DevPlatform ev3DevPlatform) throws IOException {

        Path batteryPath = Paths.get(EV3DEV_FAKE_SYSTEM_PATH, LEGO_SENSOR_PATH);
        this.createDirectories(batteryPath);

        if(ev3DevPlatform.equals(EV3DevPlatform.EV3BRICK)) {
            String ev3Ports = "ev3-ports:in1";
            LOGGER.trace("Creating ports: {}", ev3Ports);
            createStructureSensor1(ev3Ports);
        } else {
            //BrickPi3
            String brickPiPorts = "spi0.1:S1";
            LOGGER.trace("Creating ports: {}", brickPiPorts);
            createStructurePort1(brickPiPorts);
            createStructureSensor1(brickPiPorts);
        }
    }

    private void createStructureSensor1(final String inputPort) throws IOException {
        // create sensor1
        this.createDirectoriesDirect(SENSOR1_BASE);

        // set sensor1 address
        this.writeFileDirect(inputPort, SENSOR1_BASE, SENSOR_ADDRESS);

        //TODO: Remove in the future. Every Fake sensor need to complete the contract.
        // set sensor1 mode
        this.writeFileDirect("BOGUS-MODE", SENSOR1_BASE, SENSOR_MODE);

        // set sensor1 command to BOGUS-CMD
        this.writeFileDirect("BOGUS-CMD", SENSOR1_BASE, SENSOR_COMMAND);
    }

    private void createStructurePort1(final String inputPort) throws IOException {
        // create lego port1
        this.createDirectoriesDirect(PORT1_BASE);

        // set port1 address to <inputPort>
        this.writeFileDirect(inputPort, PORT1_BASE, PORT_ADDRESS);

        // set port1 mode to ev3-bogus
        this.writeFileDirect("ev3-bogus", PORT1_BASE, PORT_MODE);
    }

    public String getCurrentMode() {
        return Sysfs.readString(Paths.get(SENSOR1_BASE, SENSOR_MODE).toString());
    }

}
