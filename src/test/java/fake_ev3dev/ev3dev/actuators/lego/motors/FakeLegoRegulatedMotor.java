package fake_ev3dev.ev3dev.actuators.lego.motors;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.BaseElement;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeLegoRegulatedMotor extends BaseElement {

    protected static final String LEGO_PORT_PATH = "lego-port";
    protected static final String LEGO_TACHO_PATH = "tacho-motor";
    protected static final String MOTOR = "motor";
    protected static final String MOTOR1 = "motor1";
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

            populate(1);
            populate(2);
            populate(3);
            populate(4);

        }
    }

    private void populate(final int portNumber) throws IOException {

        String addressValue = "";
        if(portNumber == 1) {
            addressValue = "ev3-ports:outA";
        } else if(portNumber == 2) {
            addressValue = "ev3-ports:outB";
        } else if(portNumber == 3) {
            addressValue = "ev3-ports:outC";
        } else if(portNumber == 4) {
            addressValue = "ev3-ports:outD";
        }

        //Lego Port Steps
        Path port1 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT + portNumber
        );
        createDirectories(port1);

        Path addressPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT + portNumber + "/" +
                        PORT_ADDRESS);
        createFile(addressPath, addressValue);

        Path modePath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_PORT_PATH + "/" +
                        PORT + portNumber + "/" +
                        PORT_MODE);
        createFile(modePath);

        //Tacho Motor Steps
        Path motor1 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR + portNumber);
        createDirectories(motor1);

        Path motorAddress1Path = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR + portNumber + "/" +
                        PORT_ADDRESS);
        createFile(motorAddress1Path, addressValue);

        Path motor1CommandPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR + portNumber + "/" +
                        MOTOR_COMMAND);
        createFile(motor1CommandPath);
    }

}
