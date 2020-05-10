package ev3dev.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static ev3dev.hardware.EV3DevFileSystem.EV3DEV_TESTING_KEY;

/**
 * To check the possible options to use the methods enable/disable
 * list the content of the path: /sys/bus/usb/devices
 */
@Slf4j
public class USBPort {

    private String USBPath;
    private final String defaultUSBPath = "/sys/bus/usb/drivers/usb/";

    /**
     * Constructor
     */
    public USBPort() {

        if (Objects.nonNull(System.getProperty(EV3DEV_TESTING_KEY))) {
            final String NEW_ROOT_PATH = System.getProperty(EV3DEV_TESTING_KEY);
            LOGGER.debug("USB Path modified: {}", NEW_ROOT_PATH);
            USBPath = NEW_ROOT_PATH + "/usb";
        } else {
            LOGGER.debug("Root Path: {}", defaultUSBPath);
            USBPath = defaultUSBPath;
        }

    }

    public void enable(final String usbDevice) {
        Shell.execute("echo '" + usbDevice + "' | sudo tee " + USBPath + "/bind");
    }

    public void disable(final String usbDevice) {
        Shell.execute("echo '" + usbDevice + "' | sudo tee " + USBPath + "/unbind");
    }

}
