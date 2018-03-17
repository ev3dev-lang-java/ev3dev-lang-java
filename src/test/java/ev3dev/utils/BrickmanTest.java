package ev3dev.utils;

import ev3dev.actuators.LCD;
import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.BaseElement;
import fake_ev3dev.ev3dev.actuators.FakeLCD;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lombok.extern.slf4j.Slf4j;
import mocks.MockBaseTest;
import mocks.ev3dev.sensors.BatteryMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;

import java.io.IOException;

@Slf4j
public class BrickmanTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void resetTest() throws IOException {
        FakeLCD.deleteEV3DevFakeSystemPath();
    }

    //OK EV3Brick
    //OK PiStorms

    @Test
    public void disableBrickmanOnEV3Test() throws Exception {

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
        System.setProperty(LCD.EV3DEV_LCD_KEY, FakeLCD.EV3DEV_FAKE_SYSTEM_PATH + "/" + LCD.EV3DEV_EV3_LCD_PATH);

        final EV3DevPlatform platform = EV3DevPlatform.EV3BRICK;
        final FakeBattery fakeBattery = new FakeBattery(platform);
        final FakeLCD fakeLCD = new FakeLCD(platform);

        Brickman.disable();
    }

    @Test
    public void disableBrickmanOnBrickPiTest() throws Exception {

        thrown.expect(RuntimeException.class);

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
        System.setProperty(LCD.EV3DEV_LCD_KEY, FakeLCD.EV3DEV_FAKE_SYSTEM_PATH + "/" + LCD.EV3DEV_EV3_LCD_PATH);

        final EV3DevPlatform platform = EV3DevPlatform.BRICKPI;
        final FakeBattery fakeBattery = new FakeBattery(platform);
        final FakeLCD fakeLCD = new FakeLCD(platform);

        Brickman.disable();
    }

    @Test
    public void disableBrickmanOnBrickPi3Test() throws Exception {

        thrown.expect(RuntimeException.class);

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
        System.setProperty(LCD.EV3DEV_LCD_KEY, FakeLCD.EV3DEV_FAKE_SYSTEM_PATH + "/" + LCD.EV3DEV_EV3_LCD_PATH);

        final EV3DevPlatform platform = EV3DevPlatform.BRICKPI3;
        final FakeBattery fakeBattery = new FakeBattery(platform);
        final FakeLCD fakeLCD = new FakeLCD(platform);

        Brickman.disable();
    }

    @Test
    public void disableBrickmanOnPiStormsTest() throws Exception {

        thrown.expect(RuntimeException.class);

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
        System.setProperty(LCD.EV3DEV_LCD_KEY, FakeLCD.EV3DEV_FAKE_SYSTEM_PATH + "/" + LCD.EV3DEV_EV3_LCD_PATH);

        final EV3DevPlatform platform = EV3DevPlatform.PISTORMS;
        final FakeBattery fakeBattery = new FakeBattery(platform);
        final FakeLCD fakeLCD = new FakeLCD(platform);

        Brickman.disable();
    }

    @Test
    public void disableBrickmanOnUnknownPlatformTest() throws Exception {

        thrown.expect(RuntimeException.class);

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
        System.setProperty(LCD.EV3DEV_LCD_KEY, FakeLCD.EV3DEV_FAKE_SYSTEM_PATH + "/" + LCD.EV3DEV_EV3_LCD_PATH);

        final EV3DevPlatform platform = EV3DevPlatform.UNKNOWN;
        final FakeBattery fakeBattery = new FakeBattery(platform);
        final FakeLCD fakeLCD = new FakeLCD(platform);

        Brickman.disable();
    }

}
