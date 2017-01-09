package ev3dev.sensors;

import ev3dev.hardware.EV3DevSensorDevice;
import lejos.utility.Delay;

import java.util.ArrayList;
import java.util.Objects;


public class BaseSensor extends EV3DevSensorDevice implements SensorModes {

    protected static final int SWITCH_DELAY = 250;

    public BaseSensor(final String sensorPort, final String mode, final String device){
        super(sensorPort, mode, device);
    }

	protected int currentMode = 0;
	protected String currentModeS = "";
    protected SensorMode[] modes;
    ArrayList<String> modeList;

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
            if (modes != null)
                for(SensorMode m : modes) {
                    modeList.add(m.getName());
                }
        }
        return modeList;
    }

    //TODO: Review interface
    public SensorMode getMode(int mode) {
        if (mode < 0)
            throw new IllegalArgumentException("Invalid mode " + mode);
        if (modes == null || mode >= modes.length)
        	throw new IllegalArgumentException("Invalid mode " + mode);            //return null;
        return modes[mode];
    }


    public SensorMode getMode(String modeName) {
        // TODO: I'm sure there is a better way to do this, but it is late!
        int i = 0;
        for(String s : getAvailableModes()) {
        	System.out.println(modeName + " " + s);
            if (s.equals(modeName))
                return modes[i];
            i++;
        }
        throw new IllegalArgumentException("No such mode " + modeName);
    }
    
    private boolean isValid(int mode) {
      if (mode < 0 || modes == null || mode >= modes.length) return false;
      return true;
    }
    
    private int getIndex(String modeName) {
      int i = 0;
      for(String s : getAvailableModes()) {
          if (s.equals(modeName))
              return i;
          i++;
      }
      return -1;
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
      if (!isValid(mode)) {
        throw new IllegalArgumentException("Invalid mode " + mode);
      }
      else {
        currentMode = mode;
      }
    }


    public void setCurrentMode(String modeName) {
        int mode = getIndex(modeName);
        if (mode==-1) {
            throw new IllegalArgumentException("Invalid mode " + modeName);
        } else {
            currentMode = mode;
        }
    }

    public int getCurrentMode() {
      return currentMode;
    }


    public int getModeCount() {
      return modes.length;
    }

    //TODO Remove this method
    /**
     * Switch to the selected mode (if not already in that mode) and delay for the
     * specified period to allow the sensors to settle in the new mode. <br>
     * A mode of -1 resets the sensors.
     * TODO: There really should be a better way to work out when the switch is
     * complete, if you don't wait though you end up reading data from the previous
     * mode.
     * @param newMode The mode to switch to.
     * @param switchDelay Time in mS to delay after the switch.
     */
    protected void switchMode(int newMode, long switchDelay) {
        if (currentMode != newMode) {
        	if (newMode == -1)
                //ports.resetSensor();
            //else if (!ports.setMode(newMode))
                throw new IllegalArgumentException("Invalid sensors mode");
            currentMode = newMode;
            Delay.msDelay(switchDelay);
        }

    }

    protected void switchMode(String newMode, long switchDelay) {
        if (!Objects.equals(currentModeS, newMode)) {
        	this.setStringAttribute(SENSOR_MODE, newMode);
            currentModeS = newMode;
            Delay.msDelay(switchDelay);
        }
    }
    
}
