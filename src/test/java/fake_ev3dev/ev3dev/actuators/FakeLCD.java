package fake_ev3dev.ev3dev.actuators;

import ev3dev.actuators.LCD;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.hardware.EV3DevPlatforms;
import fake_ev3dev.BaseElement;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeLCD extends BaseElement{

    public FakeLCD(final EV3DevPlatform ev3DevPlatform) throws IOException {
        EV3DevPlatforms conf = new EV3DevPlatforms(ev3DevPlatform);

        if (ev3DevPlatform.equals(EV3DevPlatform.EV3BRICK)) {
            Path lcdPath = Paths.get(EV3DEV_FAKE_SYSTEM_PATH,
                                     conf.getFramebufferInfo().getKernelPath());
            Path devPath = lcdPath.getParent();

            createDirectories(devPath);
            createFile(lcdPath);
        } else {
            resetEV3DevInfrastructure();
        }
    }

}
