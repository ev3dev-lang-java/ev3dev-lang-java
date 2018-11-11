package ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import fake_ev3dev.ev3dev.sensors.ev3.FakeEV3UltrasonicSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

public class EV3UltrasonicSensorTest {

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

    }

    @Ignore
    @Test
    public void getSensorNameTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3UltrasonicSensor fakeEV3UltrasonicSensor = new FakeEV3UltrasonicSensor(EV3DevPlatform.EV3BRICK);

        EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S1);

        assertThat(us1.getName(), is("Distance"));
    }

    @Ignore
    @Test
    public void getAvailableModes() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3UltrasonicSensor fakeEV3UltrasonicSensor = new FakeEV3UltrasonicSensor(EV3DevPlatform.EV3BRICK);

        EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S1);

        final List<String> expectedModes = Arrays.asList("Distance", "Listen");
        final List<String> modes  = us1.getAvailableModes();

        assertThat(modes, is(expectedModes));
    }

    @Test
    public void getDistanceModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3UltrasonicSensor fakeEV3UltrasonicSensor = new FakeEV3UltrasonicSensor(EV3DevPlatform.EV3BRICK);

        EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S1);

        final SampleProvider sp = us1.getDistanceMode();
        assertThat(sp.sampleSize(), is(1));

        int distanceValue = 0;
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        distanceValue = (int)sample[0];

        assertThat(distanceValue, allOf(
                greaterThanOrEqualTo(0),
                lessThanOrEqualTo(255)));
    }

    @Test
    public void getListenModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3UltrasonicSensor fakeEV3UltrasonicSensor = new FakeEV3UltrasonicSensor(EV3DevPlatform.EV3BRICK);
        fakeEV3UltrasonicSensor.setListenMode();

        EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S1);
        final SampleProvider sp = us1.getListenMode();

        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        int value = (int) sample[0];

        assertThat(value, allOf(
                greaterThanOrEqualTo(0),
                lessThanOrEqualTo(1)));
    }

    @Test
    public void enableTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3UltrasonicSensor fakeEV3UltrasonicSensor = new FakeEV3UltrasonicSensor(EV3DevPlatform.EV3BRICK);
        fakeEV3UltrasonicSensor.setListenMode();

        EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S1);

        us1.enable();
        assertThat(fakeEV3UltrasonicSensor.getCurrentMode(), is("US-DIST-CM"));

        assertThat(us1.isEnabled(), is(true));
    }

    @Test
    public void disableTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3UltrasonicSensor fakeEV3UltrasonicSensor = new FakeEV3UltrasonicSensor(EV3DevPlatform.EV3BRICK);
        fakeEV3UltrasonicSensor.setListenMode();

        EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S1);

        us1.disable();
        assertThat(fakeEV3UltrasonicSensor.getCurrentMode(), is("US-SI-CM"));

        assertThat(us1.isEnabled(), is(false));
    }

}
