package ev3dev.sensors.mindsensors;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import fake_ev3dev.ev3dev.sensors.mindsensors.FakeNXTCamV5Sensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.geometry.RectangleInt32;
import lejos.utility.Delay;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NXTCamV5Test {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
    }

    @Test
    public void getNoAvailableModesTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeNXTCamV5Sensor fakeNXTCamV5Sensor = new FakeNXTCamV5Sensor(EV3DevPlatform.EV3BRICK);

        NXTCamV5 nxtCamV5 = new NXTCamV5(SensorPort.S1);

        final List<String> expectedModes = Arrays.asList();
        final List<String> modes  = nxtCamV5.getAvailableModes();

        assertThat(modes, Matchers.is(expectedModes));
    }

    @Test
    public void getTrackedObjectTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeNXTCamV5Sensor fakeNXTCamV5Sensor = new FakeNXTCamV5Sensor(EV3DevPlatform.EV3BRICK);

        NXTCamV5 nxtCamV5 = new NXTCamV5(SensorPort.S1);
        final int trackedObject = nxtCamV5.getNumberOfObjects();

        assertThat(trackedObject, is(1));
    }

    @Test
    public void createPhotoTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeNXTCamV5Sensor fakeNXTCamV5Sensor = new FakeNXTCamV5Sensor(EV3DevPlatform.EV3BRICK);

        NXTCamV5 nxtCamV5 = new NXTCamV5(SensorPort.S1);
        nxtCamV5.createPhoto();

        assertThat(fakeNXTCamV5Sensor.getCurrentCommand(), is("PICTURE"));
    }

    @Test
    public void createVideoTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeNXTCamV5Sensor fakeNXTCamV5Sensor = new FakeNXTCamV5Sensor(EV3DevPlatform.EV3BRICK);

        NXTCamV5 nxtCamV5 = new NXTCamV5(SensorPort.S1);
        nxtCamV5.createVideo();

        assertThat(fakeNXTCamV5Sensor.getCurrentCommand(), is("MOVIE"));
    }


    @Test
    public void setTrackingLineTrackingModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeNXTCamV5Sensor fakeNXTCamV5Sensor = new FakeNXTCamV5Sensor(EV3DevPlatform.EV3BRICK);

        NXTCamV5 nxtCamV5 = new NXTCamV5(SensorPort.S1);
        nxtCamV5.setTrackingMode(NXTCamV5.LINE_TRACKING);

        assertThat(fakeNXTCamV5Sensor.getCurrentCommand(), is("TRACK-LINE"));
    }

    @Test
    public void setTrackingObjectTrackingModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeNXTCamV5Sensor fakeNXTCamV5Sensor = new FakeNXTCamV5Sensor(EV3DevPlatform.EV3BRICK);

        NXTCamV5 nxtCamV5 = new NXTCamV5(SensorPort.S1);
        nxtCamV5.setTrackingMode(NXTCamV5.OBJECT_TRACKING);

        assertThat(fakeNXTCamV5Sensor.getCurrentCommand(), is("TRACK-OBJ"));
    }

    @Test
    public void setTrackingObjectBadModeTest() throws Exception {

        thrown.expect(RuntimeException.class);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeNXTCamV5Sensor fakeNXTCamV5Sensor = new FakeNXTCamV5Sensor(EV3DevPlatform.EV3BRICK);

        NXTCamV5 nxtCamV5 = new NXTCamV5(SensorPort.S1);
        nxtCamV5.setTrackingMode("BAD_MODE");

    }

    @Test
    public void getColorIdTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeNXTCamV5Sensor fakeNXTCamV5Sensor = new FakeNXTCamV5Sensor(EV3DevPlatform.EV3BRICK);

        NXTCamV5 nxtCamV5 = new NXTCamV5(SensorPort.S1);
        nxtCamV5.setTrackingMode(NXTCamV5.OBJECT_TRACKING);
        Delay.msDelay(2000);

        assertThat(nxtCamV5.getObjectColor(0), is(1));;
    }

    @Test
    public void getRectangleTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeNXTCamV5Sensor fakeNXTCamV5Sensor = new FakeNXTCamV5Sensor(EV3DevPlatform.EV3BRICK);

        NXTCamV5 nxtCamV5 = new NXTCamV5(SensorPort.S1);
        nxtCamV5.setTrackingMode(NXTCamV5.OBJECT_TRACKING);
        Delay.msDelay(2000);

        assertThat(nxtCamV5.getRectangle(0), is(new RectangleInt32(100,100,100,100)));;
    }

}
