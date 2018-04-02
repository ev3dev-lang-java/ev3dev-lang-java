package ev3dev.actuators.lego.motors;

import ev3dev.actuators.Sound;
import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.actuators.lego.motors.FakeEV3LargeRegulatedMotor;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lejos.hardware.port.MotorPort;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class EV3LargeRegulatedMotorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void resetTest() throws IOException, NoSuchFieldException, IllegalAccessException {

        FakeBattery.deleteEV3DevFakeSystemPath();
        FakeBattery.createEV3DevFakeSystemPath();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

    }

    @Test
    public void constructorTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
    }

    @Test
    public void motorBreakModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
        motor.coast();
        motor.brake();
        motor.hold();
    }

    @Test
    public void motorFltTest() throws Exception {

        thrown.expect(RuntimeException.class);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
        motor.flt();
    }

    @Test
    public void forwardTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
        motor.forward();
        motor.stop();
    }

    @Test
    public void backwardTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
        motor.backward();
        motor.stop();
    }

    @Test
    public void getPositionTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
        motor.resetTachoCount();
        motor.getTachoCount();
        motor.getPosition();
    }

    @Test
    public void suspendRegulationTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
        motor.suspendRegulation();
    }

    @Test
    public void isMovingTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
        motor.forward();
        motor.isMoving();
        motor.stop();

    }

    @Test
    public void isStalledTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
        motor.forward();
        motor.isMoving();
        motor.isStalled();
        motor.stop();
    }

    @Test
    public void rotateTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);

        FakeEV3LargeRegulatedMotor.updateState(" ");

        motor.rotate(90);
        motor.rotate(90, true);
        motor.rotateTo(90);
        motor.stop();
        motor.stop(true);
    }


    @Test
    public void setSpeedTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);

        FakeEV3LargeRegulatedMotor.updateState(" ");

        motor.setSpeed(100);
        motor.getSpeed();
    }

}
