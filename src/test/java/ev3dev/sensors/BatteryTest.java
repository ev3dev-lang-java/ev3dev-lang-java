package ev3dev.sensors;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.utils.BrickmanTest;
import ev3dev.utils.Sysfs;
import mocks.MockBaseTest;
import mocks.ev3dev.sensors.BatteryMock;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by jabrena on 19/6/17.
 */
public class BatteryTest extends MockBaseTest{

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(BrickmanTest.class);

    @Before
    public void onceExecutedBeforeAll() throws IOException {
        getGlobalPaths();
        createEV3DevMocksPath();
    }

    //@Ignore
    @Test
    public void getEV3BatteryVoltageTest() throws Exception{
        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, tempMocksFolder.getAbsolutePath().toString());

        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        batteryMock.createEV3DevMocksEV3BrickPlatformPath();

        Battery battery = Battery.getInstance();
        LOGGER.info("{}", battery.getVoltage());
    }

    final String BATTERY_PATH = "power_supply";
    final String BATTERY_EV3_SUBPATH = "legoev3-battery";
    final String BATTERY_FIELD_VOLTAGE = "voltage_now";
    final String BATTERY_FIELD_VOLTAGE_VALUE = "8042133";

    @Test
    public void existPathSuccessTest2() throws IOException {
        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        String BATTERY_FIELD_VOLTAGE_SUFFIX = batteryMock.createEV3DevMocksEV3BrickPlatformPath();
        final String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH;
        //assertThat(tempEV3BatteryFolder.getAbsolutePath(), is(pathToAssert));
        assertThat(Sysfs.existPath(pathToAssert), is(true));

        final String voltage = pathToAssert + "/" + BATTERY_FIELD_VOLTAGE + BATTERY_FIELD_VOLTAGE_SUFFIX;
        assertThat(Sysfs.getElements(pathToAssert).get(0).toString(), is(voltage));
    }


}
