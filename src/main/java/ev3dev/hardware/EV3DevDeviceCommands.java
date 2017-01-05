package ev3dev.hardware;

public interface EV3DevDeviceCommands {

    String DEVICE_ROOT_PATH = "/sys/class/";

    //Ports
    String LEGO_PORT = "lego-ports";
    String ADDRESS = "address";

    //Sensors
    String LEGO_SENSOR = "lego-sensor";
    String MODE = "mode";
    String DEVICE = "set_device";

    String BATTERY =  "power_supply";
    String BATTERY_PATH = DEVICE_ROOT_PATH + BATTERY;
    String BATTERY_EV3 =  "legoev3-battery";
    String BATTERY_PISTORMS =  "pistorm-battery";
    String BATTERY_BRICKPI =  "brickpi-battery";


    //Actuators
    String SOUND_PATH = "/sys/devices/platform/snd-legoev3/";
    String CMD_BEEP = "beep";
    String CMD_APLAY ="aplay";
    String VOLUME = "volume";

}
