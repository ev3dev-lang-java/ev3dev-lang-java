package ev3dev.hardware;

import fake_ev3dev.ev3dev.sensors.FakeBattery;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
public class EV3DevPlatformsTest {

    public class EV3DevPlatformChild extends EV3DevPlatforms {

    }

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
    }

    @Test
    public void testEV3DevPlatformOnEV3BrickTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        EV3DevPlatformChild epc = new EV3DevPlatformChild();

        assertThat(epc.getPlatform(), is(EV3DevPlatform.EV3BRICK));
    }

    @Test
    public void testEV3DevPlatformOnPiStormsTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.PISTORMS);

        EV3DevPlatformChild epc = new EV3DevPlatformChild();

        assertThat(epc.getPlatform(), is(EV3DevPlatform.PISTORMS));
    }

    @Test
    public void testEV3DevPlatformOnBrickPiTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.BRICKPI);

        EV3DevPlatformChild epc = new EV3DevPlatformChild();

        assertThat(epc.getPlatform(), is(EV3DevPlatform.BRICKPI));
    }

    @Test
    public void testEV3DevPlatformOnBrickPi3Test() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.BRICKPI3);

        EV3DevPlatformChild epc = new EV3DevPlatformChild();

        assertThat(epc.getPlatform(), is(EV3DevPlatform.BRICKPI3));
    }

    @Test
    public void testEV3BrickSensorPortsTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        EV3DevPlatformChild epc = new EV3DevPlatformChild();

        assertThat(epc.getSensorPort(SensorPort.S1), is("in1"));
        assertThat(epc.getSensorPort(SensorPort.S2), is("in2"));
        assertThat(epc.getSensorPort(SensorPort.S3), is("in3"));
        assertThat(epc.getSensorPort(SensorPort.S4), is("in4"));
    }


    @Test
    public void testBrickPiSensorPortsTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.BRICKPI);

        EV3DevPlatformChild epc = new EV3DevPlatformChild();

        assertThat(epc.getSensorPort(SensorPort.S1), is("ttyAMA0:S1"));
        assertThat(epc.getSensorPort(SensorPort.S2), is("ttyAMA0:S2"));
        assertThat(epc.getSensorPort(SensorPort.S3), is("ttyAMA0:S3"));
        assertThat(epc.getSensorPort(SensorPort.S4), is("ttyAMA0:S4"));
    }

    @Test
    public void testBrickPi3SensorPortsTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.BRICKPI3);

        EV3DevPlatformChild epc = new EV3DevPlatformChild();

        assertThat(epc.getSensorPort(SensorPort.S1), is("spi0.1:S1"));
        assertThat(epc.getSensorPort(SensorPort.S2), is("spi0.1:S2"));
        assertThat(epc.getSensorPort(SensorPort.S3), is("spi0.1:S3"));
        assertThat(epc.getSensorPort(SensorPort.S4), is("spi0.1:S4"));
    }

    @Test
    public void testPiStormsSensorPortsTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.PISTORMS);

        EV3DevPlatformChild epc = new EV3DevPlatformChild();

        assertThat(epc.getSensorPort(SensorPort.S1), is("pistorms:BBS2"));
        assertThat(epc.getSensorPort(SensorPort.S2), is("pistorms:BBS1"));
        assertThat(epc.getSensorPort(SensorPort.S3), is("pistorms:BAS1"));
        assertThat(epc.getSensorPort(SensorPort.S4), is("pistorms:BAS2"));
    }

    //Motor Ports

    @Test
    public void testEV3BrickMotorPortsTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        EV3DevPlatformChild epc = new EV3DevPlatformChild();

        assertThat(epc.getMotorPort(MotorPort.A), is("outA"));
        assertThat(epc.getMotorPort(MotorPort.B), is("outB"));
        assertThat(epc.getMotorPort(MotorPort.C), is("outC"));
        assertThat(epc.getMotorPort(MotorPort.D), is("outD"));
    }


    @Test
    public void testBrickPiMotorPortsTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.BRICKPI);

        EV3DevPlatformChild epc = new EV3DevPlatformChild();

        assertThat(epc.getMotorPort(MotorPort.A), is("ttyAMA0:MA"));
        assertThat(epc.getMotorPort(MotorPort.B), is("ttyAMA0:MB"));
        assertThat(epc.getMotorPort(MotorPort.C), is("ttyAMA0:MC"));
        assertThat(epc.getMotorPort(MotorPort.D), is("ttyAMA0:MD"));
    }

    @Test
    public void testBrickPi3MotorPortsTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.BRICKPI3);

        EV3DevPlatformChild epc = new EV3DevPlatformChild();

        assertThat(epc.getMotorPort(MotorPort.A), is("spi0.1:MA"));
        assertThat(epc.getMotorPort(MotorPort.B), is("spi0.1:MB"));
        assertThat(epc.getMotorPort(MotorPort.C), is("spi0.1:MC"));
        assertThat(epc.getMotorPort(MotorPort.D), is("spi0.1:MD"));
    }

    @Test
    public void testPiStormsMotorPortsTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.PISTORMS);

        EV3DevPlatformChild epc = new EV3DevPlatformChild();

        assertThat(epc.getMotorPort(MotorPort.A), is("pistorms:BBM1"));
        assertThat(epc.getMotorPort(MotorPort.B), is("pistorms:BBM2"));
        assertThat(epc.getMotorPort(MotorPort.C), is("pistorms:BAM2"));
        assertThat(epc.getMotorPort(MotorPort.D), is("pistorms:BAM1"));
    }


}