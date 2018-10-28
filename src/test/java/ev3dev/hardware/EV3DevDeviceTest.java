package ev3dev.hardware;

import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;


@Slf4j
public class EV3DevDeviceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
    }

    public static class EV3DevDeviceChild extends EV3DevDevice {

    }

    @Test
    public void badIntegerAttributeTest() throws IOException {

        thrown.expect(RuntimeException.class);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        EV3DevDeviceChild device = new EV3DevDeviceChild();

        device.setIntegerAttribute("BAD_ATTRIBUTE", 10);
    }

    @Test
    public void badStringAttributeTest() throws IOException {

        thrown.expect(RuntimeException.class);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        EV3DevDeviceChild device = new EV3DevDeviceChild();

        device.setStringAttribute("BAD_ATTRIBUTE", "value");
    }

}

