package fake_ev3dev.ev3dev.actuators.lego.motors;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.BaseElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeLegoRegulatedMotor extends BaseElement {

    protected static final String LEGO_PORT_PATH = "lego-port";
    protected static final String LEGO_TACHO_PATH = "tacho-motor";
    protected static final String MOTOR = "motor";
    protected static final String MOTOR1 = "motor1";
    protected static final String MOTOR2 = "motor2";
    protected static final String MOTOR3 = "motor3";
    protected static final String MOTOR4 = "motor4";
    protected static final String MOTOR_COMMAND = "command";
    protected static final String MOTOR_STOP_ACTION ="stop_action";
    protected static final String MOTOR_POLARITY ="polarity";
    protected static final String MOTOR_SPEED_SP ="speed_sp";
    protected static final String MOTOR_POSITION ="position";
    protected static final String MOTOR_STATE ="state";
    protected static final String MOTOR_POSITION_SP ="position_sp";
    protected static final String MOTOR_DUTY_CYCLE_SP ="duty_cycle_sp";

    public FakeLegoRegulatedMotor(final EV3DevPlatform ev3DevPlatform) throws IOException {


        if(ev3DevPlatform.equals(EV3DevPlatform.EV3BRICK)) {

            populatePortA();
            populatePortB();
            populatePortC();
            populatePortD();
        }
    }

    private void populatePortA() throws IOException {

        //Lego Port Steps
        Path port = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH);
        createDirectories(port);

        Path port1 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT1
        );
        createDirectories(port1);

        Path addressPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT1 + "/" +
                        PORT_ADDRESS);
        createFile(addressPath, "outA");

        Path modePath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT1 + "/" +
                        PORT_MODE);
        createFile(modePath);

        //Tacho Motor Steps
        Path tachoPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH);
        createDirectories(tachoPath);

        Path motor1 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1
        );

        Files.createDirectories(motor1);
        System.out.println(Files.exists(motor1));

        Path motorAddress1Path = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1 + "/" +
                        PORT_ADDRESS);
        Files.createFile(motorAddress1Path);

        //Review real content to simulate better
        Files.write(motorAddress1Path, "outA".getBytes());

        Path motor1CommandPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1 + "/" +
                        MOTOR_COMMAND);
        Files.createFile(motor1CommandPath);
    }

    private void populatePortB() throws IOException {

        //Lego Port Steps
        Path port = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT2);
        createDirectories(port);

        Path addressPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT2 + "/" +
                        PORT_ADDRESS);
        createFile(addressPath,"outB");

        Path modePath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT2 + "/" +
                        PORT_MODE);
        createFile(modePath);

        //Tacho Motor Steps
        Path motor2 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR2
        );
        createDirectories(motor2);

        Path motorAddress1Path = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR2 + "/" +
                        PORT_ADDRESS);
        createFile(motorAddress1Path, "outB");

        Path motor1CommandPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR2 + "/" +
                        MOTOR_COMMAND);
        createFile(motor1CommandPath);
    }

    private void populatePortC() throws IOException {

        //Lego Port Steps
        Path port1 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT3);
        createDirectories(port1);

        Path addressPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT3 + "/" +
                        PORT_ADDRESS);
        createFile(addressPath, "outC");

        Path modePath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT3 + "/" +
                        PORT_MODE);
        createFile(modePath);

        //Tacho Motor Steps
        Path motor1 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR3);
        createDirectories(motor1);

        Path motorAddress1Path = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR3 + "/" +
                        PORT_ADDRESS);
        createFile(motorAddress1Path, "outC");

        Path motor1CommandPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR3 + "/" +
                        MOTOR_COMMAND);
        createFile(motor1CommandPath);
    }

    private void populatePortD() throws IOException {

        //Lego Port Steps
        Path port1 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT4);
        createDirectories(port1);

        Path addressPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT4 + "/" +
                        PORT_ADDRESS);
        createFile(addressPath, "outD");

        Path modePath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT4 + "/" +
                        PORT_MODE);
        createFile(modePath);

        //Tacho Motor Steps
        Path motor1 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR4);
        createDirectories(motor1);

        Path motorAddress1Path = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR4 + "/" +
                        PORT_ADDRESS);
        createFile(motorAddress1Path, "outD");

        Path motor1CommandPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR4 + "/" +
                        MOTOR_COMMAND);
        createFile(motor1CommandPath);
    }


}
