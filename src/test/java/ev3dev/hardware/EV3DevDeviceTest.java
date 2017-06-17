package ev3dev.hardware;

import lejos.hardware.port.MotorPort;
import mocks.MockBaseTest;
import mocks.ev3dev.sensors.BatteryMock;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EV3DevDeviceTest extends MockBaseTest {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EV3DevDeviceTest.class);

    @Before
    public void onceExecutedBeforeAll() throws IOException {
        getGlobalPaths();
        createEV3DevMocksPath();
    }

    public static class EV3DevDeviceChild extends EV3DevDevice {

    }

    @Test
    public void testEV3DevPlatformOnEV3BrickTest() throws IOException {

        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        batteryMock.createEV3DevMocksEV3BrickPlatformPath();

        System.out.println(batteryMock.getTempBatteryFolder());

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, tempMocksFolder.getAbsolutePath().toString());

        EV3DevDeviceChild device = new EV3DevDeviceChild();
        LOGGER.debug(device.getROOT_PATH());
        LOGGER.debug(device.getMotorPort(MotorPort.A));
        assertThat(device.getPlatform(), is(EV3DevPlatform.EV3BRICK));

        /*
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