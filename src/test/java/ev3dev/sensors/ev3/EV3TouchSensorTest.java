package ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.sensors.SensorMode;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import fake_ev3dev.ev3dev.sensors.ev3.FakeEV3TouchSensor;
import lejos.hardware.port.SensorPort;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EV3TouchSensorTest {

    @Before
    public void resetTest() throws IOException {
        FakeBattery.deleteEV3DevFakeSystemPath();
        FakeBattery.createEV3DevFakeSystemPath();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

    }

    @Test
    public void testPressed() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3TouchSensor fakeEV3TouchSensor = new FakeEV3TouchSensor(EV3DevPlatform.EV3BRICK);

        EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
        assertThat(touchSensor.getTouchMode().getName(), is("Touch"));

        fakeEV3TouchSensor.pressed();

        assertThat(touchSensor.isPressed(), is(true));
    }

    @Test
    public void testUnpressed() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3TouchSensor fakeEV3TouchSensor = new FakeEV3TouchSensor(EV3DevPlatform.EV3BRICK);

        EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
        assertThat(touchSensor.getTouchMode().getName(), is("Touch"));

        fakeEV3TouchSensor.unpressed();

        assertThat(touchSensor.isPressed(), is(false));

    }

    @Test
    public void getSampleSizeTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3TouchSensor fakeEV3TouchSensor = new FakeEV3TouchSensor(EV3DevPlatform.EV3BRICK);

        EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
        SensorMode sensorMode = touchSensor.getTouchMode();

        assertThat(sensorMode.sampleSize(), is(1));
    }

    @Test
    public void getSampleTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3TouchSensor fakeEV3TouchSensor = new FakeEV3TouchSensor(EV3DevPlatform.EV3BRICK);

        EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
        SensorMode sensorMode = touchSensor.getTouchMode();

        fakeEV3TouchSensor.pressed();

        float [] sample = new float[sensorMode.sampleSize()];
        sensorMode.fetchSample(sample, 0);
        int touchValue = (int)sample[0];

        assertThat(touchValue, is(1));

        fakeEV3TouchSensor.unpressed();

        float [] sample2 = new float[sensorMode.sampleSize()];
        sensorMode.fetchSample(sample2, 0);
        int touchValue2 = (int)sample2[0];

        assertThat(touchValue2, is(0));
    }

}
