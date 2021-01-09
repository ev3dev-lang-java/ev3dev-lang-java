package ev3dev.actuators.ev3;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.actuators.ev3.FakeLed;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lejos.hardware.LED;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.assertj.core.api.BDDAssertions.then;

public class EV3LedTest {

    //TODO Refactor exception cases with JUnit 5
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void resetTest() throws IOException {

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

        FakeBattery.resetEV3DevInfrastructure();
        new FakeBattery(EV3DevPlatform.EV3BRICK);
    }

    @Test
    public void given_actuator_when_useConstructorLeft_then_Ok() throws Exception {

        //Given
        FakeLed fakeLed = new FakeLed(EV3DevPlatform.EV3BRICK);

        //When
        @SuppressWarnings("deprecation") LED led = new EV3Led(EV3Led.LEFT);
        LED led2 = new EV3Led(EV3Led.Direction.LEFT);

        //Then
        then(led).isNotNull();
        then(led2).isNotNull();
    }

    @Test
    public void given_actuator_when_useConstructorRight_then_Ok() throws Exception {

        //Given
        FakeLed fakeLed = new FakeLed(EV3DevPlatform.EV3BRICK);

        //When
        @SuppressWarnings("deprecation") LED led = new EV3Led(EV3Led.RIGHT);
        LED led2 = new EV3Led(EV3Led.Direction.RIGHT);

        //Then
        then(led).isNotNull();
        then(led2).isNotNull();
    }

    @Test
    public void given_actuator_when_useConstructorWithBadParameter_then_Ko() throws Exception {

        //Given
        FakeLed fakeLed = new FakeLed(EV3DevPlatform.EV3BRICK);

        //When
        //Then
        thrown.expect(ArrayIndexOutOfBoundsException.class);
        @SuppressWarnings("deprecation") LED led = new EV3Led(4);
    }

    @Test
    public void given_actuator_when_useConstructorWithNull_then_Ko() throws Exception {

        //Given
        FakeLed fakeLed = new FakeLed(EV3DevPlatform.EV3BRICK);

        //When
        //Then
        thrown.expect(IllegalArgumentException.class);
        LED led = new EV3Led(null);
    }

    @Test
    public void given_actuator_when_leftPatterns_then_Ok() throws Exception {

        //Given
        FakeLed fakeLed = new FakeLed(EV3DevPlatform.EV3BRICK);

        //When
        LED led = new EV3Led(EV3Led.Direction.LEFT);
        led.setPattern(1);
        led.setPattern(2);
        led.setPattern(3);
        led.setPattern(4);

        led = new EV3Led(EV3Led.Direction.LEFT);
        led.setPattern(1);
        led.setPattern(2);
        led.setPattern(3);
        led.setPattern(4);

        //Then
        //TODO Currently, it is not possible to execute a verify or something similar
    }

    @Test
    public void given_actuator_when_rightPatterns_then_Ok() throws Exception {

        //Given
        FakeLed fakeLed = new FakeLed(EV3DevPlatform.EV3BRICK);

        //When
        @SuppressWarnings("deprecation") LED led = new EV3Led(EV3Led.RIGHT);
        led.setPattern(1);
        led.setPattern(2);
        led.setPattern(3);
        led.setPattern(4);

        led = new EV3Led(EV3Led.Direction.RIGHT);
        led.setPattern(1);
        led.setPattern(2);
        led.setPattern(3);
        led.setPattern(4);

        //Then
        //TODO Currently, it is not possible to execute a verify or something similar
    }

    @Test
    public void given_actuator_when_getDirection_then_Ok() throws Exception {

        //Given
        FakeLed fakeLed = new FakeLed(EV3DevPlatform.EV3BRICK);

        //When
        @SuppressWarnings("deprecation") EV3Led led = new EV3Led(EV3Led.RIGHT);
        EV3Led led2 = new EV3Led(EV3Led.Direction.RIGHT);

        //Then
        EV3Led.Direction expectdedDirection = EV3Led.Direction.RIGHT;
        then(led.getDirection()).isEqualTo(expectdedDirection);
        then(led2.getDirection()).isEqualTo(expectdedDirection);
    }

}
