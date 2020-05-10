package ev3dev.sensors;

import ev3dev.hardware.EV3DevSensorDevice;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.SensorModes;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
public class BaseSensor extends EV3DevSensorDevice implements SensorModes {

    public static final int SWITCH_DELAY = 400;
    private ArrayList<String> modeList;
    protected int currentMode = 0;
    protected SensorMode[] modes;

    /**
     * Constructor
     *
     * @param sensorPort sensorPort
     * @param mode mode
     * @param device device
     */
    public BaseSensor(final Port sensorPort, final String mode, final String device) {
        super(sensorPort, mode, device);
    }

    /**
     * Constructor
     *
     * @param sensorPort sensorPort
     * @param mode mode
     */
    public BaseSensor(final Port sensorPort, final String mode) {
        super(sensorPort, mode);
    }

    /**
     * Define the set of modes to be made available for this sensors.
     * @param m An array containing a list of modes
     */
    protected void setModes(SensorMode[] m) {
        modes = m;
        // force the list to be rebuilt
        modeList = null;
        currentMode = 0;
    }

    /**
     * Returns all modes availables from the sensor.
     *
     * @return List of modes available
     */
    public ArrayList<String> getAvailableModes() {
        //TODO Refactor
        if (modeList == null) {
            modeList = new ArrayList<>(modes.length);
            if (modes != null) {
                for (SensorMode m : modes) {
                    modeList.add(m.getName());
                }
            }
        }
        return modeList;
    }


    /**
     * Get a SensorMode associated with a mode index.
     *
     * <p><b>WARNING:</b> This function <b>does not</b>
     * switch the sensor to the correct mode. Unless the sensor is
     * switched to the correct mode, the reads from this SensorMode
     * will be invalid.
     * See {@link GenericMode#fetchSample(float[], int)}</p>
     */
    public SensorMode getMode(int mode) {
        if (modeInvalid(mode)) {
            throw new IllegalArgumentException("Invalid mode " + mode);
        }
        return modes[mode];
    }


    /**
     * Get a SensorMode associated with a mode name.
     *
     * <p><b>WARNING:</b> This function <b>does not</b>
     * switch the sensor to the correct mode. Unless the sensor is
     * switched to the correct mode, the reads from this SensorMode
     * will be invalid.
     * See {@link GenericMode#fetchSample(float[], int)}</p>
     */
    public SensorMode getMode(String modeName) {
        int index = getIndex(modeName);
        if (index != -1) {
            return modes[index];
        } else {
            throw new IllegalArgumentException("No such mode " + modeName);
        }
    }

    private boolean modeInvalid(int mode) {
        return modes == null || mode < 0 || mode >= modes.length;
    }

    private int getIndex(String modeName) {
        return getAvailableModes().indexOf(modeName);
    }

    public String getName() {
        return modes[currentMode].getName();
    }

    public int sampleSize() {
        return modes[currentMode].sampleSize();
    }

    /**
     * Set the current SensorMode index.
     *
     * <p><b>WARNING:</b> this function works properly only when
     * the sensor is already in the appropriate mode. This means
     * that the returned reading will be valid only when
     * you previously activated the "current mode" via a call
     * to get*Mode() or switchMode().
     * See {@link GenericMode#fetchSample(float[], int)}</p>
     *
     * @param sample The array to store the sample in.
     * @param offset The elements of the sample are stored in the array starting at the offset position.
     */
    public void fetchSample(float[] sample, int offset) {
        modes[currentMode].fetchSample(sample, offset);
    }

    /**
     * Set the current SensorMode index.
     *
     * <p><b>WARNING:</b> This function <b>does not</b>
     * switch the sensor to the correct mode. Unless the sensor is
     * switched to the correct mode, the reads from the"current" SensorMode
     * will be invalid.
     * See {@link GenericMode#fetchSample(float[], int)}</p>
     */
    public void setCurrentMode(int mode) {
        if (modeInvalid(mode)) {
            throw new IllegalArgumentException("Invalid mode " + mode);
        } else {
            currentMode = mode;
        }
    }


    public int getCurrentMode() {
        return currentMode;
    }

    /**
     * Set the current SensorMode name.
     *
     * <p><b>WARNING:</b> This function <b>does not</b>
     * switch the sensor to the correct mode. Unless the sensor is
     * switched to the correct mode, the reads from the"current" SensorMode
     * will be invalid.
     * See {@link GenericMode#fetchSample(float[], int)}</p>
     */
    public void setCurrentMode(String modeName) {
        int mode = getIndex(modeName);
        if (mode == -1) {
            throw new IllegalArgumentException("Invalid mode " + modeName);
        } else {
            currentMode = mode;
        }
    }

    public int getModeCount() {
        return modes.length;
    }

    /**
     * Read current sensor mode from the kernel.
     *
     * @return Sensor mode identifier.
     */
    protected String getSystemMode() {
        return this.getStringAttribute(SENSOR_MODE);
    }

    /**
     * Write requested sensor mode to the kernel.
     *
     * @param mode Sensor mode identifier.
     */
    private void setSystemMode(String mode) {
        this.setStringAttribute(SENSOR_MODE, mode);
    }

    /**
     * Switch the sensor to the specified mode, if necessary.
     *
     * <p>Note: the mode switch will make future reads from
     * SensorModes for other modes invalid. On the other hand, it will
     * make reads valid for the SensorMode associated with the mode the
     * sensor is switching to.
     * See {@link GenericMode#fetchSample(float[], int)}</p>
     *
     * @param newMode Identifier of the sensor mode (not its name).
     * @param switchDelay Delay until the sensor starts sending new data.
     */
    public void switchMode(String newMode, long switchDelay) {
        String oldMode = getSystemMode();

        if (!Objects.equals(oldMode, newMode)) {
            setSystemMode(newMode);
            Delay.msDelay(switchDelay);
        }
    }

}
