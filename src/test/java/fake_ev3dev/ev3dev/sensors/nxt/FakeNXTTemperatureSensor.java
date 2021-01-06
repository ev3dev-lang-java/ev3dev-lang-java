package fake_ev3dev.ev3dev.sensors.nxt;

import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;
import java.io.IOException;
import java.util.Arrays;

public class FakeNXTTemperatureSensor extends FakeLegoSensor {

    public FakeNXTTemperatureSensor(EV3DevPlatform ev3DevPlatform) throws IOException {
        super(ev3DevPlatform);

        populateValues(Arrays.asList(20));
    }

    public void setListenMode() throws IOException {
        this.writeFileDirect(String.valueOf(100), SENSOR1_BASE, "value0");
    }
}
