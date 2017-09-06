package ev3dev.hardware;

import lejos.hardware.port.SensorPort;
import mocks.MockBaseTest;
import mocks.ev3dev.sensors.BatteryMock;
import org.junit.Before;
import org.slf4j.Logger;

import java.io.IOException;

public class EV3DevSensorDeviceTest extends MockBaseTest {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EV3DevSensorDeviceTest.class);

    @Before
    public void onceExecutedBeforeAll() throws IOException {
        getGlobalPaths();
        createEV3DevMocksPath();
        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, tempMocksFolder.getAbsolutePath().toString());
    }

    public class EV3DevSensorDeviceChild extends EV3DevSensorDevice {

        public EV3DevSensorDeviceChild(){
            super(SensorPort.S1,"demo");
        }
    }

    //@Test
    public void testEV3DevPlatformOnEV3BrickTest() throws IOException {

        //Inject a MockBattery object
        BatteryMock batteryMock = new BatteryMock(this.tempFolder);
        batteryMock.createEV3DevMocksEV3BrickPlatformPath();

        EV3DevSensorDeviceChild device = new EV3DevSensorDeviceChild();
        LOGGER.debug(device.getROOT_PATH());
        LOGGER.debug(device.getSensorPort(SensorPort.S1));
        device.detect("demo","ui");
        device.getIntegerAttribute("demo");
        device.getPlatform();
        device.getStringAttribute("demo");
        device.setIntegerAttribute("", 0);
        device.setStringAttribute("","");

    }




}