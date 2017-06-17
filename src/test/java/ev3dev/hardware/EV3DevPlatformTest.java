package ev3dev.hardware;

import mocks.MockBaseTest;
import mocks.ev3dev.sensors.BatteryMock;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EV3DevPlatformTest extends MockBaseTest {

    @Before
    public void onceExecutedBeforeAll() throws IOException {
        getGlobalPaths();
        createEV3DevMocksPath();
        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, tempMocksFolder.getAbsolutePath().toString());
    }

    public class EV3DevPlatformChild extends EV3DevPlatforms {

    }

    @Test
    public void testEV3DevPlatformOnEV3BrickTest() throws IOException {

        //Inject a MockBattery object
        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        batteryMock.createEV3DevMocksEV3BrickPlatformPath();

        EV3DevPlatformChild epc = new EV3DevPlatformChild();
        assertThat(epc.getPlatform(), is(EV3DevPlatform.EV3BRICK));
    }

    @Test
    public void testEV3DevPlatformOnPiStormsTest() throws IOException {

        //Inject a MockBattery object
        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        batteryMock.createEV3DevMocksPiStormsPlatformPath();

        EV3DevPlatformChild epc = new EV3DevPlatformChild();
        assertThat(epc.getPlatform(), is(EV3DevPlatform.PISTORMS));
    }

    @Test
    public void testEV3DevPlatformOnBrickPiTest() throws IOException {

        //Inject a MockBattery object
        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        batteryMock.createEV3DevMocksBrickPiPlatformPath();

        EV3DevPlatformChild epc = new EV3DevPlatformChild();
        assertThat(epc.getPlatform(), is(EV3DevPlatform.BRICKPI));
    }
}