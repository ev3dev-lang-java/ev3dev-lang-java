package ev3dev.hardware;

import mocks.MockBaseTest;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;

public class EV3DevMotorDeviceTest extends MockBaseTest {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EV3DevMotorDeviceTest.class);

    public class EV3DevMotorDeviceChild extends EV3DevMotorDevice {

    }

    //@Test
    public void testEV3DevPlatformOnEV3BrickTest() throws IOException {

        //Inject a MockBattery object
        this.createEV3DevMocksEV3BrickPlatformPath();

        EV3DevMotorDeviceChild device = new EV3DevMotorDeviceChild();
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