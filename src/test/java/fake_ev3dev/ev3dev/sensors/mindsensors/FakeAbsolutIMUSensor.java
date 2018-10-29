package fake_ev3dev.ev3dev.sensors.mindsensors;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;

import java.io.IOException;
import java.util.Arrays;

public class FakeAbsolutIMUSensor extends FakeLegoSensor {

    public FakeAbsolutIMUSensor(EV3DevPlatform ev3DevPlatform) throws IOException {

        super(ev3DevPlatform);

        populateValues(Arrays.asList(2, 100, 200));
    }

}
