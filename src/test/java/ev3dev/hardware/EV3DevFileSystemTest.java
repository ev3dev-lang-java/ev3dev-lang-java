package ev3dev.hardware;

import fake_ev3dev.ev3dev.sensors.FakeBattery;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EV3DevFileSystemTest {

    public class EV3DevFileSystemChild extends EV3DevFileSystem {

    }

    //OK
    //KO

    @Test
    public void getNormalRootPathTest() throws IOException {

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, EV3DevPlatforms.EV3DEV_ROOT_PATH);

        EV3DevFileSystemChild efc = new EV3DevFileSystemChild();

        assertThat(efc.getROOT_PATH(), is(EV3DevPlatforms.EV3DEV_ROOT_PATH));
    }

    @Test
    public void getMockPathTest() throws IOException {

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

        EV3DevFileSystemChild efc = new EV3DevFileSystemChild();

        assertThat(efc.getROOT_PATH(), is(FakeBattery.EV3DEV_FAKE_SYSTEM_PATH));
    }
}