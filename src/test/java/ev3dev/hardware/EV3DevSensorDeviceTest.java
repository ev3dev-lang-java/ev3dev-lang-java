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

    public class EV3DevSensorDeviceChild extends EV3DevSensorDevice {

        public EV3DevSensorDeviceChild(){
            super(SensorPort.S1,"demo");
        }
    }

    @Before
    public void resetTest() throws IOException {
        FakeBattery.resetEV3DevInfrastructure();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

    }

    @Test
    public void testEV3DevPlatformOnEV3BrickTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeLegoSensor legoSensor = new FakeLegoSensor(EV3DevPlatform.EV3BRICK);

        EV3DevSensorDeviceChild device = new EV3DevSensorDeviceChild();
        //assertThat(device.getPlatform(), is(EV3DevPlatform.EV3BRICK));
        //assertThat(device.getSensorPort(SensorPort.S1), is("ev3-ports:in1"));
        assertThat(device.getStringAttribute("address"), is("ev3-ports:in1"));
        device.setStringAttribute("address", "ev3-ports:in1");
    }

    @Ignore("Review how to reset a Static classic in JUnit")
    @Test
    public void testEV3DevPlatformOnBrickPi3Test() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.BRICKPI3);
        final FakeLegoSensor legoSensor = new FakeLegoSensor(EV3DevPlatform.BRICKPI3);

        EV3DevSensorDeviceChild device = new EV3DevSensorDeviceChild();
        //assertThat(device.getPlatform(), is(EV3DevPlatform.BRICKPI3));
        //assertThat(device.getSensorPort(SensorPort.S1), is("spi0.1:S1"));
        assertThat(device.getStringAttribute("address"), is("spi0.1:S1"));
    }
}
