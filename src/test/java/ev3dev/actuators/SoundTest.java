package ev3dev.actuators;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.actuators.FakeSound;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SoundTest {

    @Before
    public void resetTest() throws IOException, NoSuchFieldException, IllegalAccessException {

        //https://stackoverflow.com/questions/8256989/singleton-and-unit-testing
        Field instance = Sound.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);

        FakeBattery.resetEV3DevInfrastructure();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
        System.setProperty(Sound.EV3DEV_SOUND_KEY, FakeSound.EV3DEV_FAKE_SYSTEM_PATH);
    }

    @Test
    public void singletonTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        Sound sound = Sound.getInstance();
        Sound sound2 = Sound.getInstance();

        assertThat(sound, is(sound2));
    }

    @Test
    public void beepTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        Sound sound = Sound.getInstance();
        sound.beep();
    }

    @Test
    public void beepBrickPiTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.BRICKPI);

        Sound sound = Sound.getInstance();
        sound.beep();
    }

    @Test
    public void getVolumeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeSound fakeSound = new FakeSound(EV3DevPlatform.EV3BRICK);

        Sound sound = Sound.getInstance();

        assertThat(sound.getVolume(), is(0));
    }

    @Test
    public void setVolumeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeSound fakeSound = new FakeSound(EV3DevPlatform.EV3BRICK);

        Sound sound = Sound.getInstance();
        sound.setVolume(20);

        assertThat(sound.getVolume(), is(20));
    }

    @Test
    public void playSample() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeSound fakeSound = new FakeSound(EV3DevPlatform.EV3BRICK);

        Sound sound = Sound.getInstance();
        sound.playSample(new File("myFavouriteSong.wav"));
    }

    @Test
    public void playSampleWitVolume() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeSound fakeSound = new FakeSound(EV3DevPlatform.EV3BRICK);

        Sound sound = Sound.getInstance();
        sound.playSample(new File("myFavouriteSong.wav"), 40);

        assertThat(sound.getVolume(), is(40));
    }

    @Test
    public void playTone() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeSound fakeSound = new FakeSound(EV3DevPlatform.EV3BRICK);

        Sound sound = Sound.getInstance();
        sound.playTone(100, 100);
    }

    @Test
    public void playToneWithVolume() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeSound fakeSound = new FakeSound(EV3DevPlatform.EV3BRICK);

        Sound sound = Sound.getInstance();
        sound.playTone(100, 100, 60);

        assertThat(sound.getVolume(), is(60));
    }

}

