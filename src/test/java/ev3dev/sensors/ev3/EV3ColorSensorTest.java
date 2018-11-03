package ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import fake_ev3dev.ev3dev.sensors.ev3.FakeEV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
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
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

public class EV3ColorSensorTest {

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

    }

    @Ignore
    @Test
    public void getSensorNameTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3ColorSensor fakeEV3ColorSensor = new FakeEV3ColorSensor(EV3DevPlatform.EV3BRICK);

        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);

        assertThat(colorSensor.getName(), Matchers.is("ColorID"));
    }

    @Ignore
    @Test
    public void getAvailableModes() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3ColorSensor fakeEV3ColorSensor = new FakeEV3ColorSensor(EV3DevPlatform.EV3BRICK);

        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);

        final List<String> expectedModes = Arrays.asList("ColorID", "Red", "RGB", "Ambient");
        final List<String> modes  = colorSensor.getAvailableModes();

        assertThat(modes, Matchers.is(expectedModes));
    }

    @Test
    public void getColorIDModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3ColorSensor fakeEV3ColorSensor = new FakeEV3ColorSensor(EV3DevPlatform.EV3BRICK);

        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);

        final SampleProvider sp = colorSensor.getColorIDMode();
        assertThat(sp.sampleSize(), is(1));

        //Color
        //http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/sensor_data.html#lego-ev3-color
        int color = 0;
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        color = (int)sample[0];

        assertThat(color, allOf(
                greaterThan(0),
                lessThan(7)));
        assertThat(color, is(Color.BLUE));

        /*
        Value	Color
        0	none
        1	black
        2	blue
        3	green
        4	yellow
        5	red
        6	white
        7	brown
        */
    }

    @Test
    public void getColorIDTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3ColorSensor fakeEV3ColorSensor = new FakeEV3ColorSensor(EV3DevPlatform.EV3BRICK);

        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);

        int result = colorSensor.getColorID();
        assertThat(result, is(Color.BLUE));
    }

    @Test
    public void setFloodlightTrueTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3ColorSensor fakeEV3ColorSensor = new FakeEV3ColorSensor(EV3DevPlatform.EV3BRICK);

        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
        colorSensor.setFloodlight(true);
    }

    @Test
    public void setFloodlightFalseTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3ColorSensor fakeEV3ColorSensor = new FakeEV3ColorSensor(EV3DevPlatform.EV3BRICK);

        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
        colorSensor.setFloodlight(false);
    }

    @Test
    public void getRedModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3ColorSensor fakeEV3ColorSensor = new FakeEV3ColorSensor(EV3DevPlatform.EV3BRICK);

        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);

        final SampleProvider sp = colorSensor.getRedMode();
        assertThat(sp.sampleSize(), is(1));

        //Red
        //http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/sensor_data.html#lego-ev3-color
        int color = 0;
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        color = (int)sample[0];

        assertThat(color, allOf(
                greaterThanOrEqualTo(0),
                lessThanOrEqualTo(100)));
    }

    @Test
    public void getRGBModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3ColorSensor fakeEV3ColorSensor = new FakeEV3ColorSensor(EV3DevPlatform.EV3BRICK);

        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);

        final SampleProvider sp = colorSensor.getRGBMode();
        assertThat(sp.sampleSize(), is(3));

        //RGB
        //http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/sensor_data.html#lego-ev3-color
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        int red = (int)sample[0];
        int green = (int)sample[1];
        int blue = (int)sample[2];


        assertThat(red, allOf(
                greaterThanOrEqualTo(0),
                lessThanOrEqualTo(255)));
        assertThat(green, allOf(
                greaterThanOrEqualTo(0),
                lessThanOrEqualTo(255)));
        assertThat(blue, allOf(
                greaterThanOrEqualTo(0),
                lessThanOrEqualTo(255)));
    }

    @Test
    public void getAmbientModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3ColorSensor fakeEV3ColorSensor = new FakeEV3ColorSensor(EV3DevPlatform.EV3BRICK);

        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);

        final SampleProvider sp = colorSensor.getAmbientMode();
        assertThat(sp.sampleSize(), is(1));

        //Ambient
        //http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/sensor_data.html#lego-ev3-color
        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        int value = (int)sample[0];

        assertThat(value, allOf(
                greaterThan(0),
                lessThanOrEqualTo(100)));
    }

}
