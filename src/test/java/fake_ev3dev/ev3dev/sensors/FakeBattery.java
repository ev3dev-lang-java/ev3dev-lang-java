package fake_ev3dev.ev3dev.sensors;

import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.Shell;
import fake_ev3dev.BaseElement;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FakeBattery extends BaseElement {

    private static final String BATTERY_PATH = "power_supply";
    private static final String BATTERY_EV3_SUBPATH = "lego-ev3-battery";
    private static final String BATTERY_BRICKPI_SUBPATH = "brickpi-battery";
    private static final String BATTERY_BRICKPI3_SUBPATH = "brickpi3-battery";
    private static final String BATTERY_PISTORMS_SUBPATH = "pistorms-battery";

    private String batterySubpath;

    private static final String BATTERY_FIELD_VOLTAGE = "voltage_now";
    private static final String BATTERY_FIELD_CURRENT = "current_now";
    public static final String BATTERY_FIELD_VOLTAGE_VALUE = "8042133";
    public static final String BATTERY_FIELD_CURRENT_VALUE = "162666";

    public FakeBattery(final EV3DevPlatform ev3DevPlatform) throws IOException {

        LOGGER.info("Adding a Battery device in the FileSystem for: {}", ev3DevPlatform);

        if(ev3DevPlatform.equals(EV3DevPlatform.EV3BRICK)) {
            batterySubpath = BATTERY_EV3_SUBPATH;
        }else if(ev3DevPlatform.equals(EV3DevPlatform.BRICKPI)) {
            batterySubpath = BATTERY_BRICKPI_SUBPATH;
        }else if(ev3DevPlatform.equals(EV3DevPlatform.BRICKPI3)) {
            batterySubpath = BATTERY_BRICKPI3_SUBPATH;
        }else if(ev3DevPlatform.equals(EV3DevPlatform.PISTORMS)) {
            batterySubpath = BATTERY_PISTORMS_SUBPATH;
        }

        if(ev3DevPlatform != EV3DevPlatform.UNKNOWN) {
            Path batteryPath = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            BATTERY_PATH + "/" +
                            batterySubpath);
            this.createDirectories(batteryPath);

            Path batteryVoltagePath = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            BATTERY_PATH + "/" +
                            batterySubpath + "/" +
                            BATTERY_FIELD_VOLTAGE);
            this.createFile(batteryVoltagePath, BATTERY_FIELD_VOLTAGE_VALUE);

            Path current = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            BATTERY_PATH + "/" +
                            batterySubpath + "/" +
                            BATTERY_FIELD_CURRENT);
            this.createFile(current, BATTERY_FIELD_CURRENT_VALUE);

            var result = Shell.execute("tree " + EV3DEV_FAKE_SYSTEM_PATH);
            LOGGER.info(result);

        } else {
            resetEV3DevInfrastructure();
        }
    }
}
