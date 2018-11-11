package fake_ev3dev.ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;

import java.io.IOException;
import java.util.Arrays;

public class FakeEV3UltrasonicSensor extends FakeLegoSensor {

    public FakeEV3UltrasonicSensor(EV3DevPlatform ev3DevPlatform) throws IOException {
        super(ev3DevPlatform);

        populateValues(Arrays.asList(20));
    }

    public void setListenMode() throws IOException {
        this.writeFileDirect(String.valueOf(0), SENSOR1_BASE, "value0");
    }
}
