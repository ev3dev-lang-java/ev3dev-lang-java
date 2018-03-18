package ev3dev.hardware;

import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
public class EV3DevPlatformTest {

    public class EV3DevPlatformChild extends EV3DevPlatforms {

    }

    @Before
    public void resetTest() throws IOException {
        FakeBattery.deleteEV3DevFakeSystemPath();
        FakeBattery.createEV3DevFakeSystemPath();
    }

    @Test
    public void testEV3DevPlatformOnEV3BrickTest() throws IOException {

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        EV3DevPlatformChild epc = new EV3DevPlatformChild();

        assertThat(epc.getPlatform(), is(EV3DevPlatform.EV3BRICK));
    }

    @Test
    public void testEV3DevPlatformOnPiStormsTest() throws IOException {

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.PISTORMS);

        EV3DevPlatformChild epc = new EV3DevPlatformChild();

        assertThat(epc.getPlatform(), is(EV3DevPlatform.PISTORMS));
    }

    @Test
    public void testEV3DevPlatformOnBrickPiTest() throws IOException {

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.BRICKPI);

        EV3DevPlatformChild epc = new EV3DevPlatformChild();

        assertThat(epc.getPlatform(), is(EV3DevPlatform.BRICKPI));
    }

    @Test
    public void testEV3DevPlatformOnBrickPi3Test() throws IOException {


        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.BRICKPI3);

        EV3DevPlatformChild epc = new EV3DevPlatformChild();

        assertThat(epc.getPlatform(), is(EV3DevPlatform.BRICKPI3));
    }
}