package fake_ev3dev.ev3dev.actuators.lego.motors;

import ev3dev.hardware.EV3DevPlatform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FakeEV3LargeRegulatedMotor extends FakeLegoRegulatedMotor {

    public FakeEV3LargeRegulatedMotor(EV3DevPlatform ev3DevPlatform) throws IOException {
        super(ev3DevPlatform);
        
        Path polarity = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1 + "/" +
                        MOTOR_POLARITY);
        createFile(polarity);

        Path speed_sp = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1 + "/" +
                        MOTOR_SPEED_SP);
        createFile(speed_sp);

        Path position = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1 + "/" +
                        MOTOR_POSITION);
        createFile(position, "0");

        Path state = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1 + "/" +
                        MOTOR_STATE);
        createFile(state, "running");

        Path position_sp = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1 + "/" +
                        MOTOR_POSITION_SP);
        createFile(position_sp, "90");

        Path duty_cycle_sp = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1 + "/" +
                        MOTOR_DUTY_CYCLE_SP);
        createFile(duty_cycle_sp);

        Path stop_action = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1 + "/" +
                        MOTOR_STOP_ACTION);
        createFile(stop_action);
    }

    public static void updateState(String newState) throws IOException {

        Path state = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEGO_TACHO_PATH + "/" +
                        MOTOR1 + "/" +
                        MOTOR_STATE);
        if(!Files.exists(state)) {
            Files.createFile(state);
        }

        //Review real content to simulate better
        Files.write(state, newState.getBytes());
    }
}
