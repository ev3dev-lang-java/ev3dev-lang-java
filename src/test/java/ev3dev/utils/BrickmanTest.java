package ev3dev.utils;

import ev3dev.actuators.LCD;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.hardware.EV3DevPlatforms;
import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.actuators.FakeLCD;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

@Slf4j
public class BrickmanTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void resetTest() throws IOException {
        FakeLCD.resetEV3DevInfrastructure();
    }

    //OK EV3Brick
    //OK PiStorms

    @Test
    public void disableBrickmanOnNewEV3Test() throws Exception {

        final EV3DevPlatform platform = EV3DevPlatform.EV3BRICK;

        EV3DevPlatforms conf = new EV3DevPlatforms(platform);

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
        System.setProperty(LCD.EV3DEV_LCD_KEY, FakeLCD.EV3DEV_FAKE_SYSTEM_PATH + "/" + conf.getFramebufferInfo().getKernelPath());
        System.setProperty(LCD.EV3DEV_LCD_MODE_KEY, "xrgb");

        final FakeBattery fakeBattery = new FakeBattery(platform);
        final FakeLCD fakeLCD = new FakeLCD(platform);

        Brickman.disable();
    }

    @Test
    public void disableBrickmanOnOldEV3Test() throws Exception {

        final EV3DevPlatform platform = EV3DevPlatform.EV3BRICK;

        EV3DevPlatforms conf = new EV3DevPlatforms(platform);

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
        System.setProperty(LCD.EV3DEV_LCD_KEY, FakeLCD.EV3DEV_FAKE_SYSTEM_PATH + "/" + conf.getFramebufferInfo().getKernelPath());
        System.setProperty(LCD.EV3DEV_LCD_MODE_KEY, "bitplane");

        final FakeBattery fakeBattery = new FakeBattery(platform);
        final FakeLCD fakeLCD = new FakeLCD(platform);

        Brickman.disable();
    }

    @Test
    public void disableBrickmanOnBrickPiTest() throws Exception {

        thrown.expect(RuntimeException.class);

        final EV3DevPlatform platform = EV3DevPlatform.BRICKPI;

        EV3DevPlatforms conf = new EV3DevPlatforms(platform);

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
        System.setProperty(LCD.EV3DEV_LCD_KEY, FakeLCD.EV3DEV_FAKE_SYSTEM_PATH + "/" + conf.getFramebufferInfo().getKernelPath());
        System.setProperty(LCD.EV3DEV_LCD_MODE_KEY, "xrgb");

        final FakeBattery fakeBattery = new FakeBattery(platform);
        final FakeLCD fakeLCD = new FakeLCD(platform);

        Brickman.disable();
    }

    @Test
    public void disableBrickmanOnBrickPi3Test() throws Exception {

        thrown.expect(RuntimeException.class);

        final EV3DevPlatform platform = EV3DevPlatform.BRICKPI3;

        EV3DevPlatforms conf = new EV3DevPlatforms(platform);

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
        System.setProperty(LCD.EV3DEV_LCD_KEY, FakeLCD.EV3DEV_FAKE_SYSTEM_PATH + "/" + conf.getFramebufferInfo().getKernelPath());
        System.setProperty(LCD.EV3DEV_LCD_MODE_KEY, "xrgb");

        final FakeBattery fakeBattery = new FakeBattery(platform);
        final FakeLCD fakeLCD = new FakeLCD(platform);

        Brickman.disable();
    }

    @Ignore("Review how to reset a Static classic in JUnit")
    @Test
    public void disableBrickmanOnPiStormsTest() throws Exception {

        thrown.expect(RuntimeException.class);

        final EV3DevPlatform platform = EV3DevPlatform.PISTORMS;

        EV3DevPlatforms conf = new EV3DevPlatforms(platform);

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
        System.setProperty(LCD.EV3DEV_LCD_KEY, FakeLCD.EV3DEV_FAKE_SYSTEM_PATH + "/" + conf.getFramebufferInfo().getKernelPath());
        System.setProperty(LCD.EV3DEV_LCD_MODE_KEY, "xrgb");

        final FakeBattery fakeBattery = new FakeBattery(platform);
        final FakeLCD fakeLCD = new FakeLCD(platform);

        Brickman.disable();
    }

    @Test
    public void disableBrickmanOnUnknownPlatformTest() throws Exception {

        thrown.expect(RuntimeException.class);

        final EV3DevPlatform platform = EV3DevPlatform.UNKNOWN;

        EV3DevPlatforms conf = new EV3DevPlatforms(platform);

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
        System.setProperty(LCD.EV3DEV_LCD_KEY, FakeLCD.EV3DEV_FAKE_SYSTEM_PATH + "/" + conf.getFramebufferInfo().getKernelPath());
        System.setProperty(LCD.EV3DEV_LCD_MODE_KEY, "xrgb");

        final FakeBattery fakeBattery = new FakeBattery(platform);
        final FakeLCD fakeLCD = new FakeLCD(platform);

        Brickman.disable();
    }

}
