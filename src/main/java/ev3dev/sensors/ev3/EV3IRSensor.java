package ev3dev.sensors.ev3;

import ev3dev.sensors.BaseSensor;
import ev3dev.sensors.GenericMode;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.SensorMode;

/**
 * <b>EV3 Infra Red sensors</b><br>
 * The digital EV3 Infrared Seeking Sensor detects proximity to the robot and reads
 * signals emitted by the EV3 Infrared Beacon. The sensors can alse be used as a receiver
 * for a Lego Ev3 IR remote control.
 *
 * <p><b>EV3 Infra Red sensors</b><br>
 * The sensors can be used as a receiver for up to four Lego Ev3 IR remote controls using the methods.
 *
 * <p>See <a href="http://www.ev-3.net/en/archives/848"> Sensor Product page </a>
 * See <a href="http://sourceforge.net/p/lejos/wiki/Sensor%20Framework/"> The
 * leJOS sensors framework</a>
 * See {@link lejos.robotics.SampleProvider leJOS conventions for SampleProviders}
 *
 * @author Andy Shaw
 * @author Juan Antonio Breña Moral
 */
public class EV3IRSensor extends BaseSensor {

    private static final String LEGO_EV3_IR = "lego-ev3-ir";

    public static float MIN_RANGE = 5f; //cm
    public static float MAX_RANGE = 100f; //cm

    private static final String MODE_DISTANCE = "IR-PROX";
    private static final String MODE_SEEK = "IR-SEEK";
    private static final String MODE_REMOTE = "IR-REMOTE";

    public static final int IR_CHANNELS = 4;

    /**
     * Constructor
     *
     * @param portName portName
     */
    public EV3IRSensor(final Port portName) {
        super(portName, LEGO_UART_SENSOR, LEGO_EV3_IR);

        setModes(new SensorMode[]{
            new GenericMode(this.PATH_DEVICE, 1, "Distance", MIN_RANGE, MAX_RANGE, 1.0f),
            new GenericMode(this.PATH_DEVICE, 8, "Seek"),
            new GenericMode(this.PATH_DEVICE, IR_CHANNELS, "Remote")
        });
    }

    /**
     * <b>EV3 Infra Red sensors, Distance mode</b><br>
     * Measures the distance to an object in front of the sensors.
     *
     * <p><b>Size and content of the sample</b><br>
     * The sample contains one element giving the distance to an object in front of the sensors.
     * The distance provided is very roughly equivalent to meters
     * but needs conversion to give better distance. See product page for details. <br>
     * The effective range of the sensors in Distance mode  is about 5 to 50 centimeters.
     * Outside this range a zero is returned for low values and positive infinity for high values.
     *
     * @return A sampleProvider
     *     See {@link lejos.robotics.SampleProvider leJOS conventions for SampleProviders}
     *     See <a href="http://www.ev-3.net/en/archives/848"> Sensor Product page </a>
     */
    public SensorMode getDistanceMode() {
        switchMode(MODE_DISTANCE, SWITCH_DELAY);
        return getMode(0);
    }

    /**
     * <b>EV3 Infra Red sensor, Seek mode</b><br>
     * In seek mode the sensor locates up to four beacons and provides bearing and distance of each beacon.
     *
     * <p><b>Size and content of the sample</b><br>
     * The sample contains four pairs of elements in a single sample.
     * Each pair gives bearing of  and distance to the beacon.
     * The first pair of elements is associated with a beacon transmitting on channel 0,
     * the second pair with a beacon transmitting on channel 1 etc.<br>
     * The bearing values range from -25 to +25 (with values increasing clockwise
     * when looking from behind the sensor). A bearing of 0 indicates the beacon is
     * directly in front of the sensor. <br>
     * Distance values are not to scale. Al increasing values indicate increasing distance. <br>
     * If no beacon is detected both bearing is set to zero, and distance to positive infinity.
     *
     * @return A sampleProvider
     *     See {@link lejos.robotics.SampleProvider leJOS conventions for SampleProviders}
     *     See <a href="http://www.ev-3.net/en/archives/848"> Sensor Product page </a>
     */
    public SensorMode getSeekMode() {
        switchMode(MODE_SEEK, SWITCH_DELAY);
        return getMode(1);
    }

    /**
     * <b>EV3 Infra Red sensor, Remote mode</b><br>
     * In seek mode the sensor locates up to four beacons and provides bearing and distance of each beacon.
     *
     * <p>Returns the current remote command from the specified channel. Remote commands
     * are a single numeric value  which represents which button on the Lego IR
     * remote is currently pressed (0 means no buttons pressed). Four channels are
     * supported (0-3) which correspond to 1-4 on the remote. The button values are:<br>
     * 1 TOP-LEFT<br>
     * 2 BOTTOM-LEFT<br>
     * 3 TOP-RIGHT<br>
     * 4 BOTTOM-RIGHT<br>
     * 5 TOP-LEFT + TOP-RIGHT<br>
     * 6 TOP-LEFT + BOTTOM-RIGHT<br>
     * 7 BOTTOM-LEFT + TOP-RIGHT<br>
     * 8 BOTTOM-LEFT + BOTTOM-RIGHT<br>
     * 9 CENTRE/BEACON<br>
     * 10 BOTTOM-LEFT + TOP-LEFT<br>
     * 11 TOP-RIGHT + BOTTOM-RIGHT<br>
     *
     * @return A sampleProvider
     *     See {@link lejos.robotics.SampleProvider leJOS conventions for SampleProviders}
     */
    public SensorMode getRemoteMode() {
        switchMode(MODE_REMOTE, SWITCH_DELAY);
        return getMode(2);
    }

    /**
     * Return the current remote command from the specified channel. Remote commands
     * are a single numeric value  which represents which button on the Lego IR
     * remote is currently pressed (0 means no buttons pressed). Four channels are
     * supported (0-3) which correspond to 1-4 on the remote. The button values are:<br>
     * 1 TOP-LEFT<br>
     * 2 BOTTOM-LEFT<br>
     * 3 TOP-RIGHT<br>
     * 4 BOTTOM-RIGHT<br>
     * 5 TOP-LEFT + TOP-RIGHT<br>
     * 6 TOP-LEFT + BOTTOM-RIGHT<br>
     * 7 BOTTOM-LEFT + TOP-RIGHT<br>
     * 8 BOTTOM-LEFT + BOTTOM-RIGHT<br>
     * 9 CENTRE/BEACON<br>
     * 10 BOTTOM-LEFT + TOP-LEFT<br>
     * 11 TOP-RIGHT + BOTTOM-RIGHT<br>
     *
     * @param chan channel to obtain the command for
     * @return the current command
     */
    public int getRemoteCommand(int chan) {
        if (chan < 0 || chan >= IR_CHANNELS) {
            throw new IllegalArgumentException("Bad channel");
        }
        float[] samples = new float[IR_CHANNELS];
        getRemoteMode().fetchSample(samples, 0);
        return (int) samples[chan];
    }

    /**
     * Obtain the commands associated with one or more channels. Each element of
     * the array contains the command for the associated channel (0-3).
     *
     * @param cmds   the array to store the commands
     * @param offset the offset to start storing
     * @param len    the number of commands to store.
     */
    public void getRemoteCommands(byte[] cmds, int offset, int len) {

        // TODO this should read multiple commands, but we probably cannot easily wait for new ones
        float[] samples = new float[IR_CHANNELS];
        getRemoteMode().fetchSample(samples, 0);

        for (int i = 0; i < IR_CHANNELS; i++) {
            cmds[offset + i] = (byte) samples[i];
        }
    }

}
