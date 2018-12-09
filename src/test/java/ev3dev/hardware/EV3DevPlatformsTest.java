package ev3dev.hardware;


import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import fake_ev3dev.ev3dev.actuators.lego.motors.FakeEV3LargeRegulatedMotor;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lejos.hardware.port.MotorPort;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

@Slf4j
public class EV3DevPlatformsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

    }

    @Test
    public void testPlatformEV3() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);

    }

    @Test
    public void testPlatformUnknownPlatform() {

        thrown.expect(RuntimeException.class);

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
    }

}
