package ev3dev.utils;

import ev3dev.hardware.EV3DevPlatform;
import ev3dev.sensors.Battery;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class SysfsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();
    }

    //OK

    @Test
    public void existPathSuccessTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        final boolean result = Sysfs.existPath(fakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

        assertThat(result, is(true));
    }

    @Test
    public void readString() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ Battery.BATTERY + "/" + Battery.BATTERY_EV3 + "/" + Battery.VOLTAGE;
        String result = Sysfs.readString(pathToAssert);

        assertThat(result, is(fakeBattery.BATTERY_FIELD_VOLTAGE_VALUE));
    }

    @Test
    public void readInteger() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ Battery.BATTERY + "/" + Battery.BATTERY_EV3 + "/" + Battery.VOLTAGE;
        int result = Sysfs.readInteger(pathToAssert);

        assertThat(result, is(Integer.parseInt(FakeBattery.BATTERY_FIELD_VOLTAGE_VALUE)));
    }

    @Test
    public void readFloat() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ Battery.BATTERY + "/" + Battery.BATTERY_EV3 + "/" + Battery.VOLTAGE;
        float result = Sysfs.readFloat(pathToAssert);

        assertThat(result, is(Float.parseFloat(FakeBattery.BATTERY_FIELD_VOLTAGE_VALUE)));
    }

    @Test
    public void getElements() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ Battery.BATTERY;
        final List<File> fileList = new ArrayList<>();
        fileList.add(new File(pathToAssert + "/" + Battery.BATTERY_EV3));

        assertThat(Sysfs.getElements(pathToAssert), is(fileList));
    }

    @Test
    public void existFile() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ Battery.BATTERY + "/"+ Battery.BATTERY_EV3 + "/" + Battery.VOLTAGE;
        final Path path = Paths.get(pathToAssert);
        boolean result = Sysfs.existFile(path);

        assertThat(result, is(true));
    }

    @Test
    public void notExistFile() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ Battery.BATTERY + "/"+ Battery.BATTERY_EV3 + "/" + Battery.CURRENT + "-ERROR";
        final Path path = Paths.get(pathToAssert);

        assertThat(Sysfs.existFile(path), is(false));
    }

    @Test
    public void writeString() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ Battery.BATTERY + "/"+ Battery.BATTERY_EV3 + "/" + Battery.VOLTAGE;
        final Path path = Paths.get(pathToAssert);
        Sysfs.writeString(pathToAssert, "10");

        assertThat(Sysfs.readString(pathToAssert), is("10"));
    }

    @Test
    public void readStringWithException() throws Exception {

        thrown.expect(RuntimeException.class);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ Battery.BATTERY + "/"+ Battery.BATTERY_EV3 + "/" + Battery.VOLTAGE + "-ERROR";
        final Path path = Paths.get(pathToAssert);
        Sysfs.readString(pathToAssert);
    }

    @Test
    public void writeInteger() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ Battery.BATTERY + "/"+ Battery.BATTERY_EV3 + "/" + Battery.VOLTAGE;
        final Path path = Paths.get(pathToAssert);
        Sysfs.writeInteger(pathToAssert, 10);

        assertThat(Sysfs.readInteger(pathToAssert), is(10));
    }

    @Ignore("Review error in detail for Travis CI")
    @Test(expected = RuntimeException.class)
    public void writeBytesTest() {

        int BUFFER_SIZE = 0;
        byte[] buf = new byte[BUFFER_SIZE];

        final String FB_PATH = "/dev/MY_PERSONAL_PATH";

        Sysfs.writeBytes(FB_PATH, buf);
    }

}