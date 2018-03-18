package ev3dev.hardware;

import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lejos.hardware.port.SensorPort;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

@Slf4j
public class EV3DevSensorDeviceTest {

    @Before
    public void resetTest() throws IOException {
        FakeBattery.deleteEV3DevFakeSystemPath();
        FakeBattery.createEV3DevFakeSystemPath();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

    }

    public class EV3DevSensorDeviceChild extends EV3DevSensorDevice {

        public EV3DevSensorDeviceChild(){
            super(SensorPort.S1,"demo");
        }
    }

    @Ignore
    @Test
    public void testEV3DevPlatformOnEV3BrickTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

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