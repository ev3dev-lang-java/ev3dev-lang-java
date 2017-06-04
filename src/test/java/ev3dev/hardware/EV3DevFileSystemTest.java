package ev3dev.hardware;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EV3DevFileSystemTest {

    private static final String EV3DEV_TESTING_VALUE = "/ev3dev/mocks/";

    public class EV3DevFileSystemChild extends EV3DevFileSystem {

    }

    //OK
    //KO

    @Test
    public void getNormalRootPathTest() throws IOException {
        EV3DevFileSystemChild efc = new EV3DevFileSystemChild();
        assertThat(efc.getROOT_PATH(), is(EV3DevPlatforms.EV3DEV_ROOT_PATH));
    }

    @Test
    public void getMockPathTest() throws IOException {
        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, EV3DEV_TESTING_VALUE);
        EV3DevFileSystemChild efc = new EV3DevFileSystemChild();
        assertThat(efc.getROOT_PATH(), is(EV3DEV_TESTING_VALUE));
    }
}