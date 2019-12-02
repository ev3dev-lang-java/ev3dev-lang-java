package ev3dev.actuators.lego.motors;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.actuators.lego.motors.FakeEV3LargeRegulatedMotor;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class EV3LargeRegulatedMotorTest {


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
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
    }

    @Ignore
    @Test
    public void motorBreakModeTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);

        FakeEV3LargeRegulatedMotor.updateState("\n");

        motor.flt();
        motor.flt(true);

        //EV3Dev stop available modes
        motor.coast();
        motor.brake();
        motor.hold();
    }

    @Test
    public void forwardTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
        motor.forward();
        FakeEV3LargeRegulatedMotor.updateState("\n");
        motor.stop();
    }

    @Test
    public void forwardNotRegulatedTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
        motor.suspendRegulation();
        motor.forward();
        FakeEV3LargeRegulatedMotor.updateState("\n");
        motor.stop();
    }

    @Test
    public void backwardTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
        motor.backward();
        FakeEV3LargeRegulatedMotor.updateState("\n");
        motor.stop();
    }

    @Test
    public void backwardNotRegulatedTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
        motor.suspendRegulation();
        motor.backward();
        FakeEV3LargeRegulatedMotor.updateState("\n");
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
        FakeEV3LargeRegulatedMotor.updateState("\n");
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
        FakeEV3LargeRegulatedMotor.updateState("\n");
        motor.stop();
    }

    @Test
    public void rotateTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);

        FakeEV3LargeRegulatedMotor.updateState("\n");

        motor.rotate(90);
        motor.rotate(90, true);
        motor.rotateTo(90);
        FakeEV3LargeRegulatedMotor.updateState("\n");
        motor.stop();
        motor.stop(true);
    }


    @Test
    public void setSpeedTest() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);

        FakeEV3LargeRegulatedMotor.updateState("\n");

        motor.setSpeed(100);
        motor.getSpeed();

        motor.suspendRegulation();
        motor.setSpeed(100);
        motor.getSpeed();
    }

    @Test
    public void addListenerTest() throws Exception{

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
        motor.addListener(new RegulatedMotorListener() {
            @Override
            public void rotationStarted(RegulatedMotor regulatedMotor, int i, boolean b, long l) {

            }

            @Override
            public void rotationStopped(RegulatedMotor regulatedMotor, int i, boolean b, long l) {

            }
        });

        FakeEV3LargeRegulatedMotor.updateState("\n");

        motor.rotate(90);
        motor.rotateTo(90);
        FakeEV3LargeRegulatedMotor.updateState("\n");
        motor.stop();
        FakeEV3LargeRegulatedMotor.updateState("running\n");
        motor.backward();
        motor.forward();
        motor.removeListener();
    }

    @Test
    public void waitCompleteTest() throws Exception{

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);

        FakeEV3LargeRegulatedMotor.updateState("\n");

        motor.rotate(90,true);
        motor.waitComplete();
        FakeEV3LargeRegulatedMotor.updateState("\n");
        motor.stop();
    }

}
