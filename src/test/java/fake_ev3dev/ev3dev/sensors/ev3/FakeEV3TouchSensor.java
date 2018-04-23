package fake_ev3dev.ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;

import java.io.IOException;
import java.util.Arrays;

public class FakeEV3TouchSensor extends FakeLegoSensor {

    public FakeEV3TouchSensor(EV3DevPlatform ev3DevPlatform) throws IOException {
        super(ev3DevPlatform);
    }

    public void pressed() throws IOException {
        populateValues(Arrays.asList(1));
    }

    public void unpressed() throws IOException {
        populateValues(Arrays.asList(0));
    }
}
