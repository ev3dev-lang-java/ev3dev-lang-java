package fake_ev3dev.ev3dev.actuators;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.BaseElement;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeLed extends BaseElement{

    public static final String LEFT_LED = "leds/ev3:left";
    public static final String RIGHT_LED = "leds/ev3:right";
    public static final String RED_LED = ":red:ev3dev";
    public static final String GREEN_LED = ":green:ev3dev";
    public static final String BRIGHTNESS = "brightness";

    public FakeLed(final EV3DevPlatform ev3DevPlatform) throws IOException {

        Path ledRightGreen = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        RIGHT_LED +
                        GREEN_LED);
        createDirectories(ledRightGreen);

        Path ledRightGreenBrightness = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        RIGHT_LED +
                        GREEN_LED + "/" +
                        BRIGHTNESS);
        createFile(ledRightGreenBrightness);

        Path ledRightRed = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        RIGHT_LED +
                        RED_LED);
        createDirectories(ledRightRed);

        Path ledRightRedBrightness = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        RIGHT_LED +
                        RED_LED + "/" +
                        BRIGHTNESS);
        createFile(ledRightRedBrightness);

        //Left

        Path ledLeftGreen = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEFT_LED +
                        GREEN_LED);
        createDirectories(ledLeftGreen);

        Path ledLeftGreenBrightness = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEFT_LED +
                        GREEN_LED + "/" +
                        BRIGHTNESS);
        createFile(ledLeftGreenBrightness);

        Path ledLeftRed = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEFT_LED +
                        RED_LED);
        createDirectories(ledLeftRed);

        Path ledLeftRedBrightness = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        LEFT_LED +
                        RED_LED + "/" +
                        BRIGHTNESS);
        createFile(ledLeftRedBrightness);

    }

}
