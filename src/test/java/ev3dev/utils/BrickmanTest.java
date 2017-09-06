package ev3dev.utils;

import ev3dev.hardware.EV3DevFileSystem;
import mocks.MockBaseTest;
import mocks.ev3dev.sensors.BatteryMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;

public class BrickmanTest extends MockBaseTest{

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(BrickmanTest.class);

    @Before
    public void onceExecutedBeforeAll() throws IOException {
        getGlobalPaths();
        createEV3DevMocksPath();
        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, tempMocksFolder.getAbsolutePath().toString());
        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        batteryMock.createEV3DevMocksBrickPiPlatformPath();
    }

    //OK
    //OK PiStorms

    @Ignore
    @Test
    public void disableBrickmanOnEV3Test() throws Exception {
        Brickman.disable();
    }

    @Test
    public void disableBrickmanOnBrickPiTest() throws Exception {
        Brickman.disable();
    }

}
