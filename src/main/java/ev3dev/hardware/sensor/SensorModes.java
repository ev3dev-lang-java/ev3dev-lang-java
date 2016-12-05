package ev3dev.hardware.sensor;

import java.util.ArrayList;

/**
 * Provide access to the modes supported by a sensor
 * @author andy
 *
 */
public interface SensorModes extends SensorMode
{
    /**
     * Return a list of string descriptions for the sensors available modes.
     * @return list of string descriptions
     */
    public ArrayList<String> getAvailableModes();
    
    /**
     * Return the sample provider interface for the requested mode
     * @param mode the mode number
     * @return the sample provider for this mode
     */
    public SensorMode getMode(int mode);

    /**
     * Return the sample provider for the request mode
     * @param modeName the name/description of the mode
     * @return the sample provider for the requested mode.
     */
    public SensorMode getMode(String modeName);
    
    /**
     * Sets the current mode for fetching samples
     * @param mode the index number of the mode. Index number corresponds with the item order of the list from getAvailableModes().
     */
    public void setCurrentMode(int mode);
    
    /**
     * Sets the current mode for fetching samples
     * @param modeName the name of the mode. name corresponds with the item value of the list from getAvailableModes().
     */
    public void setCurrentMode(String modeName);
    
    /** Gets the index number of the current mode. 
     * @return the index number of the mode. Index number corresponds with the item order of the list from getAvailableModes().
     */
    public int getCurrentMode();
    
    /** Gets the number of supported modes
     * @return the number of supported modes
     */
    public int getModeCount();
    
    

}
