package ev3dev.utils;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.actuators.lego.motors.FakeEV3LargeRegulatedMotor;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lejos.robotics.RegulatedMotor;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class PilotProsTest {

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

    }

    @Test
    public void existKeyTest() throws Exception {

        PilotProps pilotProps = new PilotProps();
        pilotProps.loadPersistentValues();

        assertThat(pilotProps.getProperty("wheelDiameter"), is("8.2"));
    }

    @Test
    public void notExistKeyTest() throws Exception {

        PilotProps pilotProps = new PilotProps();
        pilotProps.loadPersistentValues();

        assertThat(pilotProps.getProperty("wheelDiameter2"), is(nullValue()));
    }

    @Test
    public void storeTest() throws Exception {

        PilotProps pilotProps = new PilotProps();
        pilotProps.storePersistentValues();

        Files.delete( Paths.get(PilotProps.PERSISTENT_FILENAME));
    }

    //TODO Point to refactor the API.
    @Test
    public void getMotorTest() throws Exception{

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        assertThat(PilotProps.getMotor("A"), instanceOf(RegulatedMotor.class));
        assertThat(PilotProps.getMotor("B"), instanceOf(RegulatedMotor.class));
        assertThat(PilotProps.getMotor("C"), instanceOf(RegulatedMotor.class));
        assertThat(PilotProps.getMotor("D"), instanceOf(RegulatedMotor.class));
    }

    @Test
    public void getNulllMotorTest() throws Exception{

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);
        final FakeEV3LargeRegulatedMotor fakeMotor = new FakeEV3LargeRegulatedMotor(EV3DevPlatform.EV3BRICK);

        assertThat(PilotProps.getMotor(""), is(nullValue()));
    }

}
