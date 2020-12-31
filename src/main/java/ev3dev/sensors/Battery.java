package ev3dev.sensors;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.DataChannelRereader;
import lejos.hardware.Power;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;

/**
 * The class Battery interacts with EV3Dev to get information about battery used.
 *
 * @author Juan Antonio Breña Moral
 * @see <a href="https://www.kernel.org/doc/Documentation/power/power_supply_class.txt">https://www.kernel.org/doc/Documentation/power/power_supply_class.txt</a>
 * @see <a href="https://github.com/ev3dev/ev3dev-lang/blob/develop/wrapper-specification.md#direct-attribute-mappings-5">https://github.com/ev3dev/ev3dev-lang/blob/develop/wrapper-specification.md#direct-attribute-mappings-5</a>
 */
@Slf4j
public class Battery extends EV3DevDevice implements Power, Closeable {

    private final DataChannelRereader voltageRereader;
    private final DataChannelRereader currentRereader;

    private static Battery instance;

    /**
     * Get a singleton Battery object
     *
     * @return Battery
     */
    public static Battery getInstance() {
        //TODO Refactor
        if (instance == null) {
            instance = new Battery();
        }
        return instance;
    }

    // Prevent duplicate objects
    private Battery() {

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
        voltageRereader = new DataChannelRereader(batteryPathLocal + "/" + voltage);
        String current = "current_now";
        currentRereader = new DataChannelRereader(batteryPath + "/" + batteryEv3 + "/" + current);
    }

    public int getVoltageMicroVolt() {
        return Integer.parseInt(voltageRereader.readString());
    }

    /**
     * @return voltage of the battery in millivolts.
     */
    @Override
    public int getVoltageMilliVolt() {
        return getVoltageMicroVolt() / 1000;
    }

    /**
     * @return voltage of the battery in microvolts.
     */
    public float getVoltage() {
        return getVoltageMicroVolt() / 1000000f;
    }

    //TODO Review output
    //TODO Review units

    /**
     * @return current from the battery in amps, or Float.NaN if run on something other than EV3BRICK
     */
    public float getBatteryCurrent() {
        if (CURRENT_PLATFORM.equals(EV3DevPlatform.EV3BRICK)) {
            return Float.parseFloat(currentRereader.readString());
        } else {
            LOGGER.warn("This method is not available for {} & {}", EV3DevPlatform.PISTORMS, EV3DevPlatform.BRICKPI);
            return Float.NaN;
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
