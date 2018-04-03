package fake_ev3dev.ev3dev.actuators;

import ev3dev.actuators.LCD;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.BaseElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeLCD extends BaseElement{

    public FakeLCD(final EV3DevPlatform ev3DevPlatform) throws IOException {

        if(ev3DevPlatform.equals(EV3DevPlatform.EV3BRICK)) {
            Path devicesPath = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LCD.EV3DEV_EV3_DEVICES_PATH);
            createDirectories(devicesPath);


            Path lcdPath = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LCD.EV3DEV_EV3_DEVICES_PATH + "/" +
                            LCD.EV3DEV_EV3_LCD_NAME);
            createFile(lcdPath);
        }else {
            resetEV3DevInfrastructure();
        }
    }

}
