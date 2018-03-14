package ev3dev.utils;

import mocks.MockBaseTest;
import mocks.ev3dev.sensors.BatteryMock;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SysfsTest extends MockBaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysfsTest.class);

    final String BATTERY_PATH = "power_supply";
    final String BATTERY_EV3_SUBPATH = "legoev3-battery";
    final String BATTERY_FIELD_VOLTAGE = "voltage_now";
    final String BATTERY_FIELD_VOLTAGE_VALUE = "8042133";
    String BATTERY_FIELD_VOLTAGE_SUFFIX;

    @Before
    public void onceExecutedBeforeAll() throws IOException {
        getGlobalPaths();
        createEV3DevMocksPath();
    }

    //OK

    @Test
    public void existPathSuccessTest() throws IOException {
        String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH;
        assertThat(tempEV3DevFolder.getAbsolutePath(), is(pathToAssert));
        assertThat(Sysfs.existPath(pathToAssert), is(true));

        pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/"+ MOCKS_PATH;
        assertThat(tempMocksFolder.getAbsolutePath(), is(pathToAssert));
        assertThat(Sysfs.existPath(pathToAssert), is(true));
    }


    @Test
    public void existPathSuccessTest2() throws IOException {
        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        BATTERY_FIELD_VOLTAGE_SUFFIX = batteryMock.createEV3DevMocksEV3BrickPlatformPath();
        final String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH;
        //assertThat(tempEV3BatteryFolder.getAbsolutePath(), is(pathToAssert));
        assertThat(Sysfs.existPath(pathToAssert), is(true));
    }


    @Test
    public void readString() throws Exception {
        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        BATTERY_FIELD_VOLTAGE_SUFFIX = batteryMock.createEV3DevMocksEV3BrickPlatformPath();
        String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH + "/" + BATTERY_FIELD_VOLTAGE + BATTERY_FIELD_VOLTAGE_SUFFIX;
        assertThat(Sysfs.readString(pathToAssert), is(BATTERY_FIELD_VOLTAGE_VALUE));
    }

    @Test
    public void readInteger() throws Exception {
        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        BATTERY_FIELD_VOLTAGE_SUFFIX = batteryMock.createEV3DevMocksEV3BrickPlatformPath();
        String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH + "/" + BATTERY_FIELD_VOLTAGE + BATTERY_FIELD_VOLTAGE_SUFFIX;
        LOGGER.trace(pathToAssert);
        assertThat(Sysfs.readInteger(pathToAssert), is(Integer.parseInt(BATTERY_FIELD_VOLTAGE_VALUE)));
    }

    @Test
    public void readFloat() throws Exception {
        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        BATTERY_FIELD_VOLTAGE_SUFFIX = batteryMock.createEV3DevMocksEV3BrickPlatformPath();
        String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH + "/" + BATTERY_FIELD_VOLTAGE + BATTERY_FIELD_VOLTAGE_SUFFIX;
        LOGGER.trace(pathToAssert);
        assertThat(Sysfs.readFloat(pathToAssert), is(Float.parseFloat(BATTERY_FIELD_VOLTAGE_VALUE)));
    }

    /*
    @Test
    public void getElements() throws Exception {
        String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH;
        log.trace(pathToAssert);
        final List<File> fileList = new ArrayList<>();
        fileList.add(batterySensor);
        assertThat(Sysfs.getElements(pathToAssert), is(fileList));
    }
    */

    @Test
    public void existFile() throws Exception {
        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        BATTERY_FIELD_VOLTAGE_SUFFIX = batteryMock.createEV3DevMocksEV3BrickPlatformPath();

        LOGGER.debug(BATTERY_FIELD_VOLTAGE_SUFFIX);
        String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH + "/" + BATTERY_FIELD_VOLTAGE + BATTERY_FIELD_VOLTAGE_SUFFIX;
        final Path path = Paths.get(pathToAssert);
        LOGGER.debug(path.toString());
        assertThat(Sysfs.existFile(path), is(true));
    }

    @Test
    public void notExistFile() throws Exception {
        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        BATTERY_FIELD_VOLTAGE_SUFFIX = batteryMock.createEV3DevMocksEV3BrickPlatformPath();

        LOGGER.debug(BATTERY_FIELD_VOLTAGE_SUFFIX);
        String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + "BAD_PATH";
        final Path path = Paths.get(pathToAssert);
        LOGGER.debug(path.toString());
        assertThat(Sysfs.existFile(path), is(false));
    }

    @Test
    public void writeString() throws Exception {
        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        BATTERY_FIELD_VOLTAGE_SUFFIX = batteryMock.createEV3DevMocksEV3BrickPlatformPath();

        String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH + "/" + BATTERY_FIELD_VOLTAGE + BATTERY_FIELD_VOLTAGE_SUFFIX;
        Sysfs.writeString(pathToAssert, "10");
        assertThat(Sysfs.readString(pathToAssert), is("10"));
    }

    @Test
    public void writeInteger() throws Exception {
        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        BATTERY_FIELD_VOLTAGE_SUFFIX = batteryMock.createEV3DevMocksEV3BrickPlatformPath();

        String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH + "/" + BATTERY_FIELD_VOLTAGE + BATTERY_FIELD_VOLTAGE_SUFFIX;
        Sysfs.writeInteger(pathToAssert, 10);
        assertThat(Sysfs.readInteger(pathToAssert), is(10));
    }

    @Test(expected = RuntimeException.class)
    public void writeBytesTest() {

        int BUFFER_SIZE = 0;
        byte[] buf = new byte[BUFFER_SIZE];

        final String FB_PATH = "/dev/fb0";

        Sysfs.writeBytes(FB_PATH, buf);
    }

}