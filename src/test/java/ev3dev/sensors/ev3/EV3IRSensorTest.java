package ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import fake_ev3dev.ev3dev.sensors.ev3.FakeEV3IRSensor;
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

public class EV3IRSensorTest {

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
    }

    @Ignore
    @Test
    public void getSensorNameTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3IRSensor fakeEV3IRSensor = new FakeEV3IRSensor(EV3DevPlatform.EV3BRICK);

        EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S1);

        assertThat(irSensor.getName(), Matchers.is("Distance"));
    }

    @Test
    public void getAvailableModes() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3IRSensor fakeEV3IRSensor = new FakeEV3IRSensor(EV3DevPlatform.EV3BRICK);

        EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S1);

        final List<String> expectedModes = Arrays.asList("Distance", "Seek", "Remote");
        final List<String> modes  = irSensor.getAvailableModes();

        assertThat(modes, Matchers.is(expectedModes));
    }

    @Test
    public void getDistanceModeTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3IRSensor fakeEV3IRSensor = new FakeEV3IRSensor(EV3DevPlatform.EV3BRICK);

        EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S1);

        final SampleProvider sp = irSensor.getDistanceMode();
        assertThat(sp.sampleSize(), is(1));

        int distanceValue = 0;
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        distanceValue = (int)sample[0];

        assertThat(distanceValue, allOf(
                greaterThanOrEqualTo(0),
                lessThanOrEqualTo(100)));
    }

    @Test
    public void getRemoteCommandTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3IRSensor fakeEV3IRSensor = new FakeEV3IRSensor(EV3DevPlatform.EV3BRICK);

        EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S1);

        int value1 = irSensor.getRemoteCommand(0);
        assertThat(value1, allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(11)));

        int value2 = irSensor.getRemoteCommand(1);
        assertThat(value2, allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(11)));

        int value3 = irSensor.getRemoteCommand(2);
        assertThat(value3, allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(11)));

        int value4 = irSensor.getRemoteCommand(3);
        assertThat(value4, allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(11)));
    }

    @Test
    public void getRemoteCommandsTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3IRSensor fakeEV3IRSensor = new FakeEV3IRSensor(EV3DevPlatform.EV3BRICK);

        EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S1);

        byte[] commands = new byte[EV3IRSensor.IR_CHANNELS];

        irSensor.getRemoteCommands(commands, 0, commands.length);

        assertThat((int) commands[0], allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(11)));
        assertThat((int) commands[0], allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(11)));
        assertThat((int) commands[0], allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(11)));
        assertThat((int) commands[0], allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(11)));
    }

    @Test
    public void getSeekModeTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3IRSensor fakeEV3IRSensor = new FakeEV3IRSensor(EV3DevPlatform.EV3BRICK);

        EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S1);

        final SampleProvider sp = irSensor.getSeekMode();
        assertThat(sp.sampleSize(), is(8));

        int beaconInfo1H = 0;
        int beaconInfo2H = 0;
        int beaconInfo3H = 0;
        int beaconInfo4H = 0;
        int beaconInfo1D = 0;
        int beaconInfo2D = 0;
        int beaconInfo3D = 0;
        int beaconInfo4D = 0;

        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);

        beaconInfo1H = (int)sample[0];
        beaconInfo1D = (int)sample[1];

        beaconInfo2H = (int)sample[2];
        beaconInfo2D = (int)sample[3];

        beaconInfo3H = (int)sample[4];
        beaconInfo3D = (int)sample[5];

        beaconInfo4H = (int)sample[6];
        beaconInfo4D = (int)sample[7];

        assertThat(beaconInfo1H, allOf(greaterThanOrEqualTo(-25), lessThanOrEqualTo(25)));
        assertThat(beaconInfo1D, allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(100)));
        assertThat(beaconInfo2H, allOf(greaterThanOrEqualTo(-25), lessThanOrEqualTo(25)));
        assertThat(beaconInfo2D, allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(100)));
        assertThat(beaconInfo3H, allOf(greaterThanOrEqualTo(-25), lessThanOrEqualTo(25)));
        assertThat(beaconInfo3D, allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(100)));
        assertThat(beaconInfo4H, allOf(greaterThanOrEqualTo(-25), lessThanOrEqualTo(25)));
        assertThat(beaconInfo4D, allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(100)));

    }

}
