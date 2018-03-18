package ev3dev.hardware;

import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lejos.hardware.port.MotorPort;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class EV3DevMotorDeviceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void resetTest() throws IOException {
        FakeBattery.deleteEV3DevFakeSystemPath();
        FakeBattery.createEV3DevFakeSystemPath();
    }

    public class EV3DevMotorDeviceChild extends EV3DevMotorDevice {

    }

    @Test
    public void testEV3DevPlatformOnEV3BrickTest() throws IOException {

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        EV3DevMotorDeviceChild device = new EV3DevMotorDeviceChild();
        LOGGER.debug(device.getROOT_PATH());
        LOGGER.debug(device.getMotorPort(MotorPort.A));

        assertThat(device.getPlatform(), is(EV3DevPlatform.EV3BRICK));

        /*
        LOGGER.debug(device.getMotorPort("adsf"));
        LOGGER.debug(device.getSensorPort("asdf"));
        device.detect("demo","ui");
        device.getIntegerAttribute("demo");
        device.getPlatform();
        device.getStringAttribute("demo");
        device.setIntegerAttribute("", 0);
        device.setStringAttribute("","");
        */
        
    }
}