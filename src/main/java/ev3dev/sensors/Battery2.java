package ev3dev.sensors;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.DataChannelRereader;
import ev3dev.utils.DataChannelRereader2;
import java.io.Closeable;
import java.io.IOException;
import lejos.hardware.Power;
import lombok.extern.slf4j.Slf4j;

/**
 * The class Battery interacts with EV3Dev to get information about battery used.
 *
 * @author Juan Antonio Breña Moral
 * @see <a href="https://www.kernel.org/doc/Documentation/power/power_supply_class.txt">https://www.kernel.org/doc/Documentation/power/power_supply_class.txt</a>
 * @see <a href="https://github.com/ev3dev/ev3dev-lang/blob/develop/wrapper-specification.md#direct-attribute-mappings-5">https://github.com/ev3dev/ev3dev-lang/blob/develop/wrapper-specification.md#direct-attribute-mappings-5</a>
 */
@Slf4j
public class Battery2 extends EV3DevDevice implements Power, Closeable {

    private final DataChannelRereader2 voltageRereader;
    private final DataChannelRereader2 currentRereader;

    private static Battery2 instance;

    /**
     * Get a singleton Battery object
     *
     * @return Battery
     */
    public static Battery2 getInstance() {
        //TODO Refactor
        if (instance == null) {
            instance = new Battery2();
        }
        return instance;
    }

    // Prevent duplicate objects
    private Battery2() {

        LOGGER.debug("Init sensor");

        String battery = ev3DevProperties.getProperty("battery");
        String batteryEv3 = ev3DevProperties.getProperty("ev3.battery");
        String batteryPistorms = ev3DevProperties.getProperty("pistorms.battery");
        String batteryBrickpi = ev3DevProperties.getProperty("brickpi.battery");
        String batteryBrickpi3 = ev3DevProperties.getProperty("brickpi3.battery");

        //TODO Create separator variable for the whole project
        String batteryPath = EV3DevFileSystem.getRootPath() + "/" + battery;
        String batteryPathLocal = "";
        if (CURRENT_PLATFORM.equals(EV3DevPlatform.EV3BRICK)) {
            batteryPathLocal += batteryPath + "/" + batteryEv3;
        } else if (CURRENT_PLATFORM.equals(EV3DevPlatform.PISTORMS)) {
            batteryPathLocal += batteryPath + "/" + batteryPistorms;
        } else if (CURRENT_PLATFORM.equals(EV3DevPlatform.BRICKPI)) {
            batteryPathLocal += batteryPath + "/" + batteryBrickpi;
        } else if (CURRENT_PLATFORM.equals(EV3DevPlatform.BRICKPI3)) {
            batteryPathLocal += batteryPath + "/" + batteryBrickpi3;
        }
        String voltage = "voltage_now";
        voltageRereader = new DataChannelRereader2(batteryPathLocal + "/" + voltage);
        String current = "current_now";
        currentRereader = new DataChannelRereader2(batteryPath + "/" + batteryEv3 + "/" + current);
    }

    public int getVoltageMicroVolts() {
        return Integer.parseInt(voltageRereader.readString());
    }

    @Override
    public int getVoltageMilliVolt() {
        return getVoltageMicroVolts() / 1000;
    }

    /**
     * Returns voltage of the battery in microvolts.
     *
     * @return voltage
     */
    public float getVoltage() {
        return getVoltageMicroVolts() / 1000000f;
    }

    //TODO Review output
    //TODO Review units

    /**
     * Returns the current of the battery in amps.
     *
     * @return current
     */
    public float getBatteryCurrent() {
        if (CURRENT_PLATFORM.equals(EV3DevPlatform.EV3BRICK)) {
            return Float.parseFloat(currentRereader.readString());
        } else {
            LOGGER.warn("This method is not available for {} & {}", EV3DevPlatform.PISTORMS, EV3DevPlatform.BRICKPI);
            return -1f;
        }
    }

    //TODO Review this method in the future.
    @Override
    public float getMotorCurrent() {
        throw new UnsupportedOperationException("This feature is not implemented");
    }

    @Override
    public void close() throws IOException {
        voltageRereader.close();
        currentRereader.close();
    }
}
