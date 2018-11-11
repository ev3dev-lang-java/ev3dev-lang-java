package ev3dev.actuators;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.JarResource;
import fake_ev3dev.ev3dev.actuators.FakeSound;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SoundTest {

    private static final String defaultSound = "nod_low_power.wav";
    private static final String nullSound = "myUnknownSong.wav";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
        System.setProperty(Sound.EV3DEV_SOUND_KEY, FakeSound.EV3DEV_FAKE_SYSTEM_PATH);

    }

    @Before
    public void resetTest() throws IOException, NoSuchFieldException, IllegalAccessException {

        //Review for Java 9
        //https://stackoverflow.com/questions/8256989/singleton-and-unit-testing
        //Field instance = Sound.class.getDeclaredField("instance");
        //instance.setAccessible(true);
        //instance.set(null, null);

        FakeBattery.resetEV3DevInfrastructure();

        //System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
        //System.setProperty(Sound.EV3DEV_SOUND_KEY, FakeSound.EV3DEV_FAKE_SYSTEM_PATH);
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

    @Ignore
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
        sound.setVolume(40);

        assertThat(sound.getVolume(), is(40));
    }

    @Test
    public void setVolumeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeSound fakeSound = new FakeSound(EV3DevPlatform.EV3BRICK);

        Sound sound = Sound.getInstance();
        sound.setVolume(20);

        assertThat(sound.getVolume(), is(20));
    }

    @Ignore("It is not running on Travis CI")
    @Test
    public void playSample() throws Exception {

        String filePath = "nod_low_power.wav";
        String result = JarResource.export(filePath);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        Sound sound = Sound.getInstance();
        sound.playSample(new File(result));
    }

    @Ignore("It is not running on Travis CI")
    @Test
    public void playMultipleSamples() throws Exception {

        String filePath = "nod_low_power.wav";
        String result = JarResource.export(filePath);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        Sound sound = Sound.getInstance();
        sound.setVolume(100);
        sound.playSample(new File(result));
        sound.setVolume(50);
        sound.playSample(new File(result));

        assertThat(sound.getVolume(), is(50));
    }

    @Test
    public void playSampleKO() throws Exception {

        thrown.expect(RuntimeException.class);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        Sound sound = Sound.getInstance();
        sound.playSample(new File(nullSound));
    }

    @Ignore("It is not running on Travis CI")
    @Test
    public void playSampleWitVolume() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        Sound sound = Sound.getInstance();
        sound.playSample(new File(nullSound), 40);

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

