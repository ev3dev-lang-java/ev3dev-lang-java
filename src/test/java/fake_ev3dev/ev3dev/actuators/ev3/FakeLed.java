package fake_ev3dev.ev3dev.actuators.ev3;

import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.Shell;
import fake_ev3dev.BaseElement;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeLed extends BaseElement{

    public static final String LEFT_LED = "leds/led0";
    public static final String RIGHT_LED = "leds/led1";
    public static final String RED_LED = ":red:brick-status";
    public static final String GREEN_LED = ":green:brick-status";
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

        var result = Shell.execute("tree " + EV3DEV_FAKE_SYSTEM_PATH);
        LOGGER.info(result);
    }

}
