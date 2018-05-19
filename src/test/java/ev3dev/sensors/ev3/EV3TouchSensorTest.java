package ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import fake_ev3dev.ev3dev.sensors.ev3.FakeEV3TouchSensor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EV3TouchSensorTest {

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

    }

    @Ignore
    @Test
    public void getSensorNameTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3TouchSensor fakeEV3TouchSensor = new FakeEV3TouchSensor(EV3DevPlatform.EV3BRICK);

        EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);

        assertThat(touchSensor.getName(), Matchers.is("Touch"));
    }

    @Ignore
    @Test
    public void getAvailableModes() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3TouchSensor fakeEV3TouchSensor = new FakeEV3TouchSensor(EV3DevPlatform.EV3BRICK);

        EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);

        final List<String> expectedModes = Arrays.asList("Touch");
        final List<String> modes  = touchSensor.getAvailableModes();

        assertThat(modes, Matchers.is(expectedModes));
    }

    @Test
    public void getTouchModeTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3TouchSensor fakeEV3TouchSensor = new FakeEV3TouchSensor(EV3DevPlatform.EV3BRICK);

        EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);

        fakeEV3TouchSensor.pressed();

        final SampleProvider sp = touchSensor.getTouchMode();
        assertThat(sp.sampleSize(), Matchers.is(1));

        int pressed = 0;
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        pressed = (int)sample[0];

        assertThat(pressed, is(1));
    }

    @Test
    public void testPressed() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3TouchSensor fakeEV3TouchSensor = new FakeEV3TouchSensor(EV3DevPlatform.EV3BRICK);

        EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);

        fakeEV3TouchSensor.pressed();

        assertThat(touchSensor.isPressed(), is(true));
    }

    @Test
    public void testUnpressed() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3TouchSensor fakeEV3TouchSensor = new FakeEV3TouchSensor(EV3DevPlatform.EV3BRICK);

        EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);

        fakeEV3TouchSensor.unpressed();

        assertThat(touchSensor.isPressed(), is(false));

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
