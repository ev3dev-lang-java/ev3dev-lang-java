package ev3dev.hardware;

import fake_ev3dev.ev3dev.sensors.FakeBattery;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;
import lejos.hardware.port.SensorPort;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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

    //@Ignore
    @Test
    public void testEV3DevPlatformOnEV3BrickTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeLegoSensor legoSensor = new FakeLegoSensor(EV3DevPlatform.EV3BRICK);

        EV3DevSensorDeviceChild device = new EV3DevSensorDeviceChild();
        assertThat(device.getPlatform(), is(EV3DevPlatform.EV3BRICK));
        assertThat(device.getSensorPort(SensorPort.S1), is("in1"));
        assertThat(device.getStringAttribute("address"), is("in1"));
        device.setStringAttribute("address", "in1");
    }
}