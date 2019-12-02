package ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import fake_ev3dev.ev3dev.sensors.ev3.FakeEV3GyroSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

public class EV3GyroSensorTest {

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

    }

    @Ignore
    @Test
    public void getSensorNameTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3GyroSensor fakeEV3GyroSensor = new FakeEV3GyroSensor(EV3DevPlatform.EV3BRICK);

        EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S1);

        assertThat(gyroSensor.getName(), Matchers.is("Rate"));
    }

    @Ignore
    @Test
    public void getAvailableModes() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3GyroSensor fakeEV3GyroSensor = new FakeEV3GyroSensor(EV3DevPlatform.EV3BRICK);

        EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S1);

        final List<String> expectedModes = Arrays.asList("Rate", "Angle", "Angle and Rate");
        final List<String> modes  = gyroSensor.getAvailableModes();

        assertThat(modes, Matchers.is(expectedModes));
    }

    @Test
    public void getRateModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3GyroSensor fakeEV3GyroSensor = new FakeEV3GyroSensor(EV3DevPlatform.EV3BRICK);

        EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S1);

        final SampleProvider sp = gyroSensor.getRateMode();
        assertThat(sp.sampleSize(), is(1));

        //Rotational Speed
        //http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/sensor_data.html#lego-ev3-gyro
        int rate = 0;
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        rate = (int)sample[0];

        assertThat(rate, allOf(
                greaterThanOrEqualTo(-440),
                lessThanOrEqualTo(440)));
    }

    @Test
    public void getAngleModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3GyroSensor fakeEV3GyroSensor = new FakeEV3GyroSensor(EV3DevPlatform.EV3BRICK);

        EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S1);

        final SampleProvider sp = gyroSensor.getAngleMode();
        assertThat(sp.sampleSize(), is(1));

        //Angle
        //http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/sensor_data.html#lego-ev3-gyro
        int angle = 0;
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        angle = (int)sample[0];

        assertThat(angle, allOf(
                greaterThanOrEqualTo(-32768),
                lessThanOrEqualTo(32767)));
    }


    @Test
    public void getAngleAndRateModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3GyroSensor fakeEV3GyroSensor = new FakeEV3GyroSensor(EV3DevPlatform.EV3BRICK);

        EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S1);

        final SampleProvider sp = gyroSensor.getAngleAndRateMode();
        assertThat(sp.sampleSize(), is(2));

        //Angle and Rotational Speed
        //http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/sensor_data.html#lego-ev3-gyro
        int angle = 0;
        int rate = 0;
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        angle = (int)sample[0];
        rate = (int)sample[1];

        assertThat(angle, allOf(
                greaterThanOrEqualTo(-32768),
                lessThanOrEqualTo(32767)));
        assertThat(rate, allOf(
                greaterThanOrEqualTo(-440),
                lessThanOrEqualTo(440)));
    }

    @Test
    public void resetModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3GyroSensor fakeEV3GyroSensor = new FakeEV3GyroSensor(EV3DevPlatform.EV3BRICK);

        EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S1);

        gyroSensor.reset();

        assertThat(fakeEV3GyroSensor.getCurrentMode(), is("GYRO-G&A"));
    }


    @Test
    public void negativeAngleTest() throws Exception {
        // Tests fix for https://github.com/ev3dev-lang-java/ev3dev-lang-java/issues/693
        // Before: negative values get truncated to zero
        // After:  negative values get passed through

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3GyroSensor fakeEV3GyroSensor = new FakeEV3GyroSensor(EV3DevPlatform.EV3BRICK);

        EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S1);
        final SampleProvider sp = gyroSensor.getAngleMode();

        final float[] sample = new float[sp.sampleSize()];
        final int realAngle = -180;

        fakeEV3GyroSensor.setValue(0, String.valueOf(realAngle));
        sp.fetchSample(sample, 0);

        int measuredAngle = Math.round(sample[0]);

        assertThat(measuredAngle, is(realAngle));
    }
}
