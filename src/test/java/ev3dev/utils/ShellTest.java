package ev3dev.utils;

import ev3dev.hardware.EV3DevPlatform;
import ev3dev.hardware.EV3DevPropertyLoader;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
public class ShellTest {

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();
    }


    @Test
    public void executeSimpleCommandTest() throws Exception {

        FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        final EV3DevPropertyLoader ev3DevPropertyLoader = new EV3DevPropertyLoader();
        final Properties ev3DevProperties = ev3DevPropertyLoader.getEV3DevProperties();

        final String BATTERY = ev3DevProperties.getProperty("battery");

        final String result = Shell.execute("ls " + FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/" + BATTERY);

        assertThat(result, is("lego-ev3-battery\n"));
    }


    @Test
    public void executeSimpleCommandKOTest() throws Exception {
        final String result = Shell.execute("lsrare ");

        assertThat(result, is(Shell.COMMAND_ERROR_MESSAGE));
    }

}
