package ev3dev.sensors.mindsensors;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import fake_ev3dev.ev3dev.sensors.mindsensors.FakeAbsolutIMUSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

public class AbsoluteIMUTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
    }

    @Ignore
    @Test
    public void getAvailableModesTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeAbsolutIMUSensor fakeAbsolutIMUSensor = new FakeAbsolutIMUSensor(EV3DevPlatform.EV3BRICK);

        AbsoluteIMU absoluteIMU = new AbsoluteIMU(SensorPort.S1);

        final List<String> expectedModes = Arrays.asList("Acceleration", "Magnetic", "Gyro", "Compass", "Tilt");
        final List<String> modes  = absoluteIMU.getAvailableModes();

        assertThat(modes, Matchers.is(expectedModes));
    }

    @Ignore
    @Test
    public void getAccelerationModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeAbsolutIMUSensor fakeAbsolutIMUSensor = new FakeAbsolutIMUSensor(EV3DevPlatform.EV3BRICK);

        AbsoluteIMU absoluteIMU = new AbsoluteIMU(SensorPort.S1);

        final SampleProvider sp = absoluteIMU.getAccelerationMode();
        assertThat(sp.sampleSize(), is(3));

        //Acceleration
        //http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/sensor_data.html#ms-absolute-imu
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        float x = sample[0];
        float y = sample[1];
        float z = sample[2];

        assertThat(x, is(notNullValue()));
        assertThat(y, is(notNullValue()));
        assertThat(z, is(notNullValue()));
    }

    @Test
    public void getMagneticModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeAbsolutIMUSensor fakeAbsolutIMUSensor = new FakeAbsolutIMUSensor(EV3DevPlatform.EV3BRICK);

        AbsoluteIMU absoluteIMU = new AbsoluteIMU(SensorPort.S1);

        final SampleProvider sp = absoluteIMU.getMagneticMode();
        assertThat(sp.sampleSize(), is(3));

        //Magnetic
        //http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/sensor_data.html#ms-absolute-imu
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        float x = sample[0];
        float y = sample[1];
        float z = sample[2];

        assertThat(x, is(notNullValue()));
        assertThat(y, is(notNullValue()));
        assertThat(z, is(notNullValue()));
    }

    @Test
    public void getCompassModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeAbsolutIMUSensor fakeAbsolutIMUSensor = new FakeAbsolutIMUSensor(EV3DevPlatform.EV3BRICK);

        AbsoluteIMU absoluteIMU = new AbsoluteIMU(SensorPort.S1);

        final SampleProvider sp = absoluteIMU.getCompassMode();
        assertThat(sp.sampleSize(), is(1));

        //Compass
        //http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/sensor_data.html#ms-absolute-imu
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        int degrees = (int)sample[0];

        assertThat(degrees, allOf(
                greaterThanOrEqualTo(0),
                lessThanOrEqualTo(360)));
    }

    @Test
    public void getGyroModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeAbsolutIMUSensor fakeAbsolutIMUSensor = new FakeAbsolutIMUSensor(EV3DevPlatform.EV3BRICK);

        AbsoluteIMU absoluteIMU = new AbsoluteIMU(SensorPort.S1);

        final SampleProvider sp = absoluteIMU.getGyroMode();
        assertThat(sp.sampleSize(), is(3));

        //Gyro
        //http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/sensor_data.html#ms-absolute-imu
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        float x = sample[0];
        float y = sample[1];
        float z = sample[2];

        assertThat(x, is(notNullValue()));
        assertThat(y, is(notNullValue()));
        assertThat(z, is(notNullValue()));
    }

    @Test
    public void getTiltModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeAbsolutIMUSensor fakeAbsolutIMUSensor = new FakeAbsolutIMUSensor(EV3DevPlatform.EV3BRICK);

        AbsoluteIMU absoluteIMU = new AbsoluteIMU(SensorPort.S1);

        final SampleProvider sp = absoluteIMU.getTiltMode();
        assertThat(sp.sampleSize(), is(3));

        //Tilt
        //http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/sensor_data.html#ms-absolute-imu
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        float x = sample[0];
        float y = sample[1];
        float z = sample[2];

        assertThat(x, is(notNullValue()));
        assertThat(y, is(notNullValue()));
        assertThat(z, is(notNullValue()));
    }

    @Test
    public void setRangeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeAbsolutIMUSensor fakeAbsolutIMUSensor = new FakeAbsolutIMUSensor(EV3DevPlatform.EV3BRICK);

        AbsoluteIMU absoluteIMU = new AbsoluteIMU(SensorPort.S1);
        absoluteIMU.setRange(1);
        absoluteIMU.setRange(2);
        absoluteIMU.setRange(3);
        absoluteIMU.setRange(4);
    }

    @Test
    public void setBadRangeTest() throws Exception {

        thrown.expect(RuntimeException.class);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeAbsolutIMUSensor fakeAbsolutIMUSensor = new FakeAbsolutIMUSensor(EV3DevPlatform.EV3BRICK);

        AbsoluteIMU absoluteIMU = new AbsoluteIMU(SensorPort.S1);
        absoluteIMU.setRange(0);
    }

}
