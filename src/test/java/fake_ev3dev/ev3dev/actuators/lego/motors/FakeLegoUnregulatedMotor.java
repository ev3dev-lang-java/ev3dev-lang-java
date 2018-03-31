package fake_ev3dev.ev3dev.actuators.lego.motors;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.BaseElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeLegoUnregulatedMotor extends BaseElement {

    protected static final String LEGO_PORT_PATH = "lego-port";
    protected static final String LEGO_TACHO_PATH = "dc-motor";

    public FakeLegoUnregulatedMotor(final EV3DevPlatform ev3DevPlatform) throws IOException {


        if(ev3DevPlatform.equals(EV3DevPlatform.EV3BRICK)) {

            populatePortA();
        }
    }

    private void populatePortA() throws IOException {
        //Lego Port Steps
        Path batteryPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH);
        Files.createDirectories(batteryPath);
        System.out.println(Files.exists(batteryPath));

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
        Files.write(addressPath, "outA".getBytes());

        Path modePath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        "port1" + "/" +
                        "mode");
        Files.createFile(modePath);


        //Tacho Motor Steps
        Path tachoPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH);
        Files.createDirectories(tachoPath);
        System.out.println(Files.exists(tachoPath));

        Path motor1 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        "motor1"
        );

        Files.createDirectories(motor1);
        System.out.println(Files.exists(motor1));

        Path motorAddress1Path = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        "motor1" + "/" +
                        "address");
        Files.createFile(motorAddress1Path);

        //Review real content to simulate better
        Files.write(motorAddress1Path, "outA".getBytes());

        Path motor1CommandPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        "motor1" + "/" +
                        "command");
        Files.createFile(motor1CommandPath);
    }

}
