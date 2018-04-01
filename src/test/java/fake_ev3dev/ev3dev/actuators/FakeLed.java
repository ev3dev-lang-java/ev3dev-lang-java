package fake_ev3dev.ev3dev.actuators;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.BaseElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeLed extends BaseElement{

    public FakeLed(final EV3DevPlatform ev3DevPlatform) throws IOException {

        Path devicesPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        "leds/ev3:right:green:ev3dev");
        Files.createDirectories(devicesPath);

        Path lcdPath = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        "leds/ev3:right:green:ev3dev" + "/" +
                        "brightness");
        Files.createFile(lcdPath);

        Path devicesPath2 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        "leds/ev3:right:red:ev3dev");
        Files.createDirectories(devicesPath2);

        Path lcdPath2 = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        "leds/ev3:right:red:ev3dev" + "/" +
                        "brightness");
        Files.createFile(lcdPath2);

        //Left

        Path leftGreen = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        "leds/ev3:left:green:ev3dev");
        Files.createDirectories(leftGreen);

        Path leftGreenBrightness = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        "leds/ev3:left:green:ev3dev" + "/" +
                        "brightness");
        Files.createFile(leftGreenBrightness);

        Path leftRed = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        "leds/ev3:left:red:ev3dev");
        Files.createDirectories(leftRed);

        Path leftRedBrightness = Paths.get(
                EV3DEV_FAKE_SYSTEM_PATH + "/" +
                        "leds/ev3:left:red:ev3dev" + "/" +
                        "brightness");
        Files.createFile(leftRedBrightness);

    }

}
