package fake_ev3dev.ev3dev.actuators;

import ev3dev.actuators.ev3.EV3Led;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.BaseElement;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeLed extends BaseElement{

    public FakeLed(final EV3DevPlatform ev3DevPlatform) throws IOException {

        Path ledRightGreen = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        EV3Led.RIGHT_LED +
                        EV3Led.GREEN_LED);
        createDirectories(ledRightGreen);

        Path ledRightGreenBrightness = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        EV3Led.RIGHT_LED +
                        EV3Led.GREEN_LED + "/" +
                        EV3Led.BRIGHTNESS);
        createFile(ledRightGreenBrightness);

        Path ledRightRed = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        EV3Led.RIGHT_LED +
                        EV3Led.RED_LED);
        createDirectories(ledRightRed);

        Path ledRightRedBrightness = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        EV3Led.RIGHT_LED +
                        EV3Led.RED_LED + "/" +
                        EV3Led.BRIGHTNESS);
        createFile(ledRightRedBrightness);

        //Left

        Path ledLeftGreen = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        EV3Led.LEFT_LED +
                        EV3Led.GREEN_LED);
        createDirectories(ledLeftGreen);

        Path ledLeftGreenBrightness = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        EV3Led.LEFT_LED +
                        EV3Led.GREEN_LED + "/" +
                        EV3Led.BRIGHTNESS);
        createFile(ledLeftGreenBrightness);

        Path ledLeftRed = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        EV3Led.LEFT_LED +
                        EV3Led.RED_LED);
        createDirectories(ledLeftRed);

        Path ledLeftRedBrightness = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        EV3Led.LEFT_LED +
                        EV3Led.RED_LED + "/" +
                        EV3Led.BRIGHTNESS);
        createFile(ledLeftRedBrightness);

    }

}
