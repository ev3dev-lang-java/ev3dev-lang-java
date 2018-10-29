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

    public BaseSensor(final Port sensorPort, final String mode, final String device) {
        super(sensorPort, mode, device);
    }

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

    public ArrayList<String> getAvailableModes() {
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

    public SensorMode getMode(int mode) {
        if (modeInvalid(mode))
            throw new IllegalArgumentException("Invalid mode " + mode);
        return modes[mode];
    }


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

    public void fetchSample(float[] sample, int offset) {
        modes[currentMode].fetchSample(sample, offset);
    }

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
     * Switch the sensor to the specified mode, if necessary.
     * @param newMode Identifier of the sensor mode.
     * @param switchDelay Delay until the sensor starts sending new data.
     */
    public void switchMode(String newMode, long switchDelay) {
        String oldMode = this.getStringAttribute(SENSOR_MODE);

        if (!Objects.equals(oldMode, newMode)) {
            this.setStringAttribute(SENSOR_MODE, newMode);
            Delay.msDelay(switchDelay);
        }
    }

}
