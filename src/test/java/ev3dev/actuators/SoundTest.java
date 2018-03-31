package ev3dev.actuators;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.sensors.Battery;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;

public class SoundTest {

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
    public void soundTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        Sound sound = Sound.getInstance();
        sound.beep();

    }

}
