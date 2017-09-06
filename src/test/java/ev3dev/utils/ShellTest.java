package ev3dev.utils;

import ev3dev.hardware.EV3DevFileSystem;
import mocks.MockBaseTest;
import mocks.ev3dev.sensors.BatteryMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ShellTest extends MockBaseTest{

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ShellTest.class);

    @Before
    public void onceExecutedBeforeAll() throws Exception {
        getGlobalPaths();
        createEV3DevMocksPath();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, tempMocksFolder.getAbsolutePath().toString());
    }

    //OK Simple Command
    //OK Complex command
    //KO Simple Command

    @Test
    public void executeSimpleCommandTest() throws Exception {

        //Inject a MockBattery object
        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        batteryMock.createEV3DevMocksEV3BrickPlatformPath();

        final String result = Shell.execute("ls " + batteryMock.getTempBatteryFolder());
        assertThat(result, is("legoev3-battery\n"));
    }

    @Ignore
    @Test
    public void executeComplexCommandTest() {

    }

    @Test
    public void executeSimpleCommandKOTest() throws Exception {
        final String result = Shell.execute("lsrare ");
        assertThat(result, is(Shell.COMMAND_ERROR_MESSAGE));
    }

    @Ignore
    @Test
    public void executeComplexCommandKOTest() {

    }

}
