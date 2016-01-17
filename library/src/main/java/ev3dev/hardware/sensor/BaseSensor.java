package ev3dev.hardware.sensor;

import java.util.ArrayList;

import lejos.utility.Delay;
import ev3dev.hardware.DeviceException;
import ev3dev.hardware.EV3DevDevice;


public class BaseSensor extends EV3DevDevice implements SensorModes {

    private static final String SYSTEM_CLASS_NAME = "lego-sensor";
    private static final String SENSOR_MODES = "modes";
    private static final String SENSOR_MODE = "mode";
    
	public BaseSensor(String sensorPort){
		super(SYSTEM_CLASS_NAME, sensorPort);
	}

	protected int currentMode = 0;
	protected String currentModeS = "";
    protected SensorMode[] modes;
    ArrayList<String> modeList;

    /**
     * Define the set of modes to be made available for this sensor.
     * @param m An array containing a list of modes
     */
    protected void setModes(SensorMode[] m)
    {
        modes = m;
        // force the list to be rebuilt
        modeList = null;
        currentMode = 0;
    }


    public ArrayList<String> getAvailableModes()
    {
        if (modeList == null)
        {
            modeList = new ArrayList<String>(modes.length);
            if (modes != null)
                for(SensorMode m : modes) {
                    modeList.add(m.getName());
                }
        }
        return modeList;
    }

    //TODO: Review interface
    public SensorMode getMode(int mode)
    {
        if (mode < 0)
            throw new IllegalArgumentException("Invalid mode " + mode);
        if (modes == null || mode >= modes.length)
        	throw new IllegalArgumentException("Invalid mode " + mode);            //return null;
        return modes[mode];
    }


    public SensorMode getMode(String modeName)
    {
        // TODO: I'm sure there is a better way to do this, but it is late!
        int i = 0;
        for(String s : getAvailableModes())
        {
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
      for(String s : getAvailableModes())
      {
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
      }
      else {
        currentMode = mode;
      }
    }

    public int getCurrentMode() {
      return currentMode;
    }


    public int getModeCount() {
      return modes.length;
    }
    
    /**
     * Switch to the selected mode (if not already in that mode) and delay for the
     * specified period to allow the sensor to settle in the new mode. <br>
     * A mode of -1 resets the sensor.
     * TODO: There really should be a better way to work out when the switch is
     * complete, if you don't wait though you end up reading data from the previous
     * mode.
     * @param newMode The mode to switch to.
     * @param switchDelay Time in mS to delay after the switch.
     */
    protected void switchMode(int newMode, long switchDelay) {
        if (currentMode != newMode) {
        	if (newMode == -1)
                //port.resetSensor();
            //else if (!port.setMode(newMode))
                throw new IllegalArgumentException("Invalid sensor mode");
            currentMode = newMode;
            Delay.msDelay(switchDelay);
        }
        
    }

    protected void switchMode(String newMode, long switchDelay) {
        if (currentModeS != newMode) {
        	/*
        	if (newMode == "-1")
                //port.resetSensor();
            else if (!port.setMode(newMode))
                throw new IllegalArgumentException("Invalid sensor mode");
            */
        	this.setStringAttribute(SENSOR_MODE, newMode);
            currentModeS = newMode;
            Delay.msDelay(switchDelay);
        }
        
    }
    
}
