package ev3dev.hardware;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import mocks.MockBaseTest;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;

public class EV3DevSensorDeviceTest extends MockBaseTest {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EV3DevSensorDeviceTest.class);

    public class EV3DevSensorDeviceChild extends EV3DevSensorDevice {

        public EV3DevSensorDeviceChild(){
            super("","");
        }
    }

    //@Test
    public void testEV3DevPlatformOnEV3BrickTest() throws IOException {

        //Inject a MockBattery object
        this.createEV3DevMocksEV3BrickPlatformPath();

        EV3DevSensorDeviceChild device = new EV3DevSensorDeviceChild();
        LOGGER.debug(device.getROOT_PATH());
        LOGGER.debug(device.getMotorPort(MotorPort.A));
        LOGGER.debug(device.getSensorPort(SensorPort.S1));
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