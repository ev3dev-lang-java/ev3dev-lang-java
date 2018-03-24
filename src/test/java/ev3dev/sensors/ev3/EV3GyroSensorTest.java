package ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import fake_ev3dev.ev3dev.sensors.ev3.FakeEV3GyroSensor;
import lejos.hardware.port.SensorPort;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;

public class EV3GyroSensorTest {

    @Before
    public void resetTest() throws IOException {
        FakeBattery.deleteEV3DevFakeSystemPath();
        FakeBattery.createEV3DevFakeSystemPath();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

    }

    @Test
    public void getSensorNameTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3GyroSensor fakeEV3GyroSensor = new FakeEV3GyroSensor(EV3DevPlatform.EV3BRICK);

        EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S1);

        assertThat(gyroSensor.getName(), Matchers.is("Rate"));
    }

}
