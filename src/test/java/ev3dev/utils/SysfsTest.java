package ev3dev.utils;

import mocks.MockBaseTest;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SysfsTest extends MockBaseTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SysfsTest.class);

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
        final String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH;
        assertThat(tempEV3BatteryFolder.getAbsolutePath(), is(pathToAssert));
        assertThat(Sysfs.existPath(pathToAssert), is(true));
    }

    @Test
    public void readString() throws Exception {
        String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH + "/" + BATTERY_FIELD_VOLTAGE + BATTERY_FIELD_VOLTAGE_SUFFIX;
        log.trace(pathToAssert);
        assertThat(Sysfs.readString(pathToAssert), is(BATTERY_FIELD_VOLTAGE_VALUE));
    }

    @Test
    public void readInteger() throws Exception {
        String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH + "/" + BATTERY_FIELD_VOLTAGE + BATTERY_FIELD_VOLTAGE_SUFFIX;
        log.trace(pathToAssert);
        assertThat(Sysfs.readInteger(pathToAssert), is(Integer.parseInt(BATTERY_FIELD_VOLTAGE_VALUE)));
    }

    @Test
    public void readFloat() throws Exception {
        String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH + "/" + BATTERY_FIELD_VOLTAGE + BATTERY_FIELD_VOLTAGE_SUFFIX;
        log.trace(pathToAssert);
        assertThat(Sysfs.readFloat(pathToAssert), is(Float.parseFloat(BATTERY_FIELD_VOLTAGE_VALUE)));
    }

    @Test
    public void getElements() throws Exception {
        String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH;
        log.trace(pathToAssert);
        final List<File> fileList = new ArrayList<>();
        fileList.add(batterySensor);
        assertThat(Sysfs.getElements(pathToAssert), is(fileList));
    }

    @Test
    public void existFile() throws Exception {
        String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH + "/" + BATTERY_FIELD_VOLTAGE + BATTERY_FIELD_VOLTAGE_SUFFIX;
        final Path path = Paths.get(pathToAssert);
        assertThat(Sysfs.existFile(path), is(true));
    }

    @Test
    public void writeString() throws Exception {
        String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH + "/" + BATTERY_FIELD_VOLTAGE + BATTERY_FIELD_VOLTAGE_SUFFIX;
        Sysfs.writeString(pathToAssert, "10");
        assertThat(Sysfs.readString(pathToAssert), is("10"));
    }

    @Test
    public void writeInteger() throws Exception {
        String pathToAssert = JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH + "/" + BATTERY_FIELD_VOLTAGE + BATTERY_FIELD_VOLTAGE_SUFFIX;
        Sysfs.writeInteger(pathToAssert, 10);
        assertThat(Sysfs.readInteger(pathToAssert), is(10));
    }

}