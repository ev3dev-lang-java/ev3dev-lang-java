package ev3dev.utils;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lombok.extern.slf4j.Slf4j;
import mocks.MockBaseTest;
import mocks.ev3dev.sensors.BatteryMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;

@Slf4j
public class BrickmanTest {

    @Before
    public void resetTest() throws IOException {
        FakeBattery.deleteEV3DevFakeSystemPath();
    }

    /*
    @Before
    public void onceExecutedBeforeAll() throws IOException {


        getGlobalPaths();
        createEV3DevMocksPath();
        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, tempMocksFolder.getAbsolutePath().toString());
        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        batteryMock.createEV3DevMocksBrickPiPlatformPath();
    }
    */

    //OK
    //OK PiStorms

    @Ignore
    @Test
    public void disableBrickmanOnEV3Test() throws Exception {

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        Brickman.disable();
    }

    @Ignore
    @Test
    public void disableBrickmanOnBrickPiTest() throws Exception {
        Brickman.disable();
    }

}
