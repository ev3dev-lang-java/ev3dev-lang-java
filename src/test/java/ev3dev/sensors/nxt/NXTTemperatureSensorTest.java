package ev3dev.sensors.nxt;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import fake_ev3dev.ev3dev.sensors.nxt.FakeNXTTemperatureSensor;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

public class NXTTemperatureSensorTest {

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
    }

    @Test
    public void getSensorNameTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeNXTTemperatureSensor fakeNXTTemperatureSensor = new FakeNXTTemperatureSensor(EV3DevPlatform.EV3BRICK);

        NXTTemperatureSensor temp1 = new NXTTemperatureSensor(SensorPort.S1);

        assertThat(temp1.getName(), is("C"));
    }

    @Test
    public void getAvailableModes() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeNXTTemperatureSensor fakeNXTTemperatureSensor = new FakeNXTTemperatureSensor(EV3DevPlatform.EV3BRICK);

        NXTTemperatureSensor temp1 = new NXTTemperatureSensor(SensorPort.S1);

        final List<String> expectedModes = Arrays.asList("C", "F");
        final List<String> modes  = temp1.getAvailableModes();

        assertThat(modes, is(expectedModes));
    }

    @Test
    public void getCelsiusTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeNXTTemperatureSensor fakeNXTTemperatureSensor = new FakeNXTTemperatureSensor(EV3DevPlatform.EV3BRICK);

        NXTTemperatureSensor temp1 = new NXTTemperatureSensor(SensorPort.S1);

        final SampleProvider sp = temp1.getCelsiusMode();
        assertThat(sp.sampleSize(), is(1));

        int temperatureValue = 0;
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        temperatureValue = (int) sample[0];

        assertThat(temperatureValue, allOf(
            greaterThanOrEqualTo(0),
            lessThanOrEqualTo(255)));
    }

    @Test
    public void getFahrenheitTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeNXTTemperatureSensor fakeNXTTemperatureSensor = new FakeNXTTemperatureSensor(EV3DevPlatform.EV3BRICK);

        NXTTemperatureSensor temp1 = new NXTTemperatureSensor(SensorPort.S1);

        final SampleProvider sp = temp1.getFahrenheitMode();
        assertThat(sp.sampleSize(), is(1));

        int temperatureValue = 0;
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        temperatureValue = (int) sample[0];

        assertThat(temperatureValue, allOf(
            greaterThanOrEqualTo(0),
            lessThanOrEqualTo(255)));
    }

}
