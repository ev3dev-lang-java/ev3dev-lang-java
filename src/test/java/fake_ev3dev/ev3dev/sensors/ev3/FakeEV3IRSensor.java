package fake_ev3dev.ev3dev.sensors.ev3;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;

import java.io.IOException;
import java.util.Arrays;

public class FakeEV3IRSensor extends FakeLegoSensor {

    public FakeEV3IRSensor(EV3DevPlatform ev3DevPlatform) throws IOException {
        super(ev3DevPlatform);

        populateValues(Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10));
    }
}
