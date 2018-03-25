package ev3dev.actuators.ev3;

import ev3dev.actuators.Sound;
import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lejos.hardware.LED;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;

public class EV3LedTest {

    @Before
    public void resetTest() throws IOException, NoSuchFieldException, IllegalAccessException {

        //https://stackoverflow.com/questions/8256989/singleton-and-unit-testing
        Field instance = Sound.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);

        FakeBattery.deleteEV3DevFakeSystemPath();
        FakeBattery.createEV3DevFakeSystemPath();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

    }

    @Test
    public void ledTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        LED led = new EV3Led(EV3Led.LEFT);
        led.setPattern(0);

    }

}
