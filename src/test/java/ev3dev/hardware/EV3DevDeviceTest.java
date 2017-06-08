package ev3dev.hardware;

import mocks.MockBaseTest;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EV3DevDeviceTest extends MockBaseTest {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EV3DevDeviceTest.class);

    public class EV3DevDeviceChild extends EV3DevDevice {

    }

    //@Test
    public void testEV3DevPlatformOnEV3BrickTest() throws IOException {

        //Inject a MockBattery object
        this.createEV3DevMocksEV3BrickPlatformPath();

        EV3DevDeviceChild device = new EV3DevDeviceChild();
        LOGGER.debug(device.getROOT_PATH());
        LOGGER.debug(device.getMotorPort("adsf"));
        LOGGER.debug(device.getSensorPort("asdf"));
        device.detect("demo","ui");
        device.getIntegerAttribute("demo");
        device.getPlatform();
        device.getStringAttribute("demo");
        device.setIntegerAttribute("", 0);
        device.setStringAttribute("","");

        //EV3DevPlatformChild epc = new EV3DevPlatformChild();
        //assertThat(epc.getPlatform(), is(EV3DevPlatform.EV3BRICK));
    }




}