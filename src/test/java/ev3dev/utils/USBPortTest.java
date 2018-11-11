package ev3dev.utils;

import ev3dev.hardware.EV3DevFileSystem;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class USBPortTest {

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);
    }

    @Test
    public void disablePort() {

        USBPort usbPort = new USBPort();
        usbPort.disable("1-1");
    }

    @Test
    public void enablePort() {

        USBPort usbPort = new USBPort();
        usbPort.enable("1-1");
    }

}