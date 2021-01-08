package fake_ev3dev.ev3dev.sensors.nxt;

import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.Shell;
import fake_ev3dev.ev3dev.sensors.FakeLegoSensor;
import java.io.IOException;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeNXTTemperatureSensor extends FakeLegoSensor {

    public FakeNXTTemperatureSensor(EV3DevPlatform ev3DevPlatform) throws IOException {
        super(ev3DevPlatform);

        populateValues(Arrays.asList(1));

        var result = Shell.execute("tree " + EV3DEV_FAKE_SYSTEM_PATH);
        LOGGER.info(result);
    }

    public void populateSensorData(float temperature) throws IOException {
        this.setValue(0, String.valueOf(temperature));
    }
}
