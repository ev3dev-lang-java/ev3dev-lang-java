package ev3dev.actuators.lego.motors;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.actuators.lego.motors.FakeLegoUnregulatedMotor;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lejos.hardware.port.MotorPort;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class UnregulatedMotorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

    }

    @Test
    public void constructorTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeLegoUnregulatedMotor fakeMotor = new FakeLegoUnregulatedMotor(EV3DevPlatform.EV3BRICK);

        UnregulatedMotor motor = new UnregulatedMotor(MotorPort.A);
    }

    @Ignore("Review how to reset a Static classic in JUnit")
    @Test
    public void constructorNotEV3BrickTest() throws Exception {

        thrown.expect(RuntimeException.class);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.BRICKPI);
        final FakeLegoUnregulatedMotor fakeMotor = new FakeLegoUnregulatedMotor(EV3DevPlatform.EV3BRICK);

        UnregulatedMotor motor = new UnregulatedMotor(MotorPort.A);
    }

    @Test
    public void forwardTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeLegoUnregulatedMotor fakeMotor = new FakeLegoUnregulatedMotor(EV3DevPlatform.EV3BRICK);

        UnregulatedMotor motor = new UnregulatedMotor(MotorPort.A);
        motor.forward();
        motor.stop();
    }

    @Test
    public void backwardTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeLegoUnregulatedMotor fakeMotor = new FakeLegoUnregulatedMotor(EV3DevPlatform.EV3BRICK);

        UnregulatedMotor motor = new UnregulatedMotor(MotorPort.A);
        motor.backward();
        motor.stop();
    }

    @Test
    public void setPowerTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeLegoUnregulatedMotor fakeMotor = new FakeLegoUnregulatedMotor(EV3DevPlatform.EV3BRICK);

        UnregulatedMotor motor = new UnregulatedMotor(MotorPort.A);

        motor.setPower(100);
        motor.getPower();
        motor.forward();
        motor.isMoving();
        motor.flt();
        motor.stop();
    }

}
