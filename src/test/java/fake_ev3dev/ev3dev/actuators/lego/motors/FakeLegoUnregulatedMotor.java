package fake_ev3dev.ev3dev.actuators.lego.motors;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.BaseElement;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeLegoUnregulatedMotor extends BaseElement {

    protected static final String LEGO_PORT_PATH = "lego-port";
    protected static final String LEGO_TACHO_PATH = "dc-motor";
    protected static final String MOTOR1 = "motor1";
    protected static final String MOTOR_COMMAND = "command";
    protected static final String MOTOR_POLARITY ="polarity";
    protected static final String MOTOR_STOP_ACTION ="stop_action";
    protected static final String MOTOR_STATE ="state";
    protected static final String MOTOR_DUTY_CYCLE_SP ="duty_cycle_sp";

    public FakeLegoUnregulatedMotor(final EV3DevPlatform ev3DevPlatform) throws IOException {


        if(ev3DevPlatform.equals(EV3DevPlatform.EV3BRICK)) {

            populatePortA();
        }
    }

    private void populatePortA() throws IOException {

        //Lego Port Steps
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
        createFile(addressPath, "ev3-ports:outA");

        Path modePath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT1 + "/" +
                        PORT_MODE);
        createFile(modePath);

        //Tacho Motor Steps
        Path motor1 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1);
        createDirectories(motor1);

        Path motorAddress1Path = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1 + "/" +
                        PORT_ADDRESS);
        createFile(motorAddress1Path, "ev3-ports:outA");

        Path motor1CommandPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1 + "/" +
                        MOTOR_COMMAND);
        createFile(motor1CommandPath);

        Path polarity = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1 + "/" +
                        MOTOR_POLARITY);
        createFile(polarity);

        Path duty_cycle_sp = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1 + "/" +
                        MOTOR_DUTY_CYCLE_SP);
        createFile(duty_cycle_sp);

        Path state = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1 + "/" +
                        MOTOR_STATE);
        createFile(state, "running");

        Path stop_action = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1 + "/" +
                        MOTOR_STOP_ACTION);
        createFile(stop_action);
    }

}
