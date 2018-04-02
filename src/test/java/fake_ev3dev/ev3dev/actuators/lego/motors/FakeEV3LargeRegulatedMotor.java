package fake_ev3dev.ev3dev.actuators.lego.motors;

import ev3dev.hardware.EV3DevPlatform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeEV3LargeRegulatedMotor extends FakeLegoRegulatedMotor {

    public FakeEV3LargeRegulatedMotor(EV3DevPlatform ev3DevPlatform) throws IOException {
        super(ev3DevPlatform);

        Path addressPath1 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        "tacho-motor" + "/" +
                        "motor1" + "/" +
                        "stop_action");
        if(!Files.exists(addressPath1)) {
            Files.createFile(addressPath1);
        }

        Path polarity = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        "tacho-motor" + "/" +
                        "motor1" + "/" +
                        "polarity");
        if(!Files.exists(polarity)) {
            Files.createFile(polarity);
        }

        Path speed_sp = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        "tacho-motor" + "/" +
                        "motor1" + "/" +
                        "speed_sp");
        if(!Files.exists(speed_sp)) {
            Files.createFile(speed_sp);
        }

        Path position = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        "tacho-motor" + "/" +
                        "motor1" + "/" +
                        "position");
        if(!Files.exists(position)) {
            Files.createFile(position);
        }

        //Review real content to simulate better
        Files.write(position, "0".getBytes());

        Path state = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        "tacho-motor" + "/" +
                        "motor1" + "/" +
                        "state");
        if(!Files.exists(state)) {
            Files.createFile(state);
        }

        //Review real content to simulate better
        Files.write(state, "running".getBytes());

        Path position_sp = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        "tacho-motor" + "/" +
                        "motor1" + "/" +
                        "position_sp");
        if(!Files.exists(position_sp)) {
            Files.createFile(position_sp);
        }

        //Review real content to simulate better
        Files.write(position_sp, "90".getBytes());

        Path duty_cycle_sp = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        "tacho-motor" + "/" +
                        "motor1" + "/" +
                        "duty_cycle_sp");
        if(!Files.exists(duty_cycle_sp)) {
            Files.createFile(duty_cycle_sp);
        }

    }

    public static void updateState(String newState) throws IOException {

        Path state = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        "tacho-motor" + "/" +
                        "motor1" + "/" +
                        "state");
        if(!Files.exists(state)) {
            Files.createFile(state);
        }

        //Review real content to simulate better
        Files.write(state, newState.getBytes());

    }
}
