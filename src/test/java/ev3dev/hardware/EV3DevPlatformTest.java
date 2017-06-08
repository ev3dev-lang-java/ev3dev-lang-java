package ev3dev.hardware;

import mocks.MockBaseTest;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EV3DevPlatformTest extends MockBaseTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(EV3DevPlatformTest.class);

    public class EV3DevPlatformChild extends EV3DevPlatforms {

    }

    @Test
    public void testEV3DevPlatformOnEV3BrickTest() throws IOException {

        //Inject a MockBattery object
        this.createEV3DevMocksEV3BrickPlatformPath();

        EV3DevPlatformChild epc = new EV3DevPlatformChild();
        assertThat(epc.getPlatform(), is(EV3DevPlatform.EV3BRICK));
    }

    @Test
    public void testEV3DevPlatformOnPiStormsTest() throws IOException {

        //Inject a MockBattery object
        this.createEV3DevMocksPiStormsPlatformPath();

        EV3DevPlatformChild epc = new EV3DevPlatformChild();
        assertThat(epc.getPlatform(), is(EV3DevPlatform.PISTORMS));
    }

    @Test
    public void testEV3DevPlatformOnBrickPiTest() throws IOException {

        //Inject a MockBattery object
        this.createEV3DevMocksBrickPiPlatformPath();

        EV3DevPlatformChild epc = new EV3DevPlatformChild();
        assertThat(epc.getPlatform(), is(EV3DevPlatform.BRICKPI));
    }
}