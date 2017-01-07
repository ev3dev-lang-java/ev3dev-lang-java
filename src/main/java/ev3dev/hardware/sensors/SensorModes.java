package ev3dev.hardware.sensors;

import java.util.ArrayList;

/**
 * Provide access to the modes supported by a sensors
 * @author andy
 *
 */
interface SensorModes extends SensorMode
{
    /**
     * Return a list of string descriptions for the sensors available modes.
     * @return list of string descriptions
     */
    ArrayList<String> getAvailableModes();
    
    /**
     * Return the sample provider interface for the requested mode
     * @param mode the mode number
     * @return the sample provider for this mode
     */
    SensorMode getMode(int mode);

    /**
     * Return the sample provider for the request mode
     * @param modeName the name/description of the mode
     * @return the sample provider for the requested mode.
     */
    SensorMode getMode(String modeName);
    
    /**
     * Sets the current mode for fetching samples
     * @param mode the index number of the mode. Index number corresponds with the item order of the list from getAvailableModes().
     */
    void setCurrentMode(int mode);
    
    /**
     * Sets the current mode for fetching samples
     * @param modeName the name of the mode. name corresponds with the item value of the list from getAvailableModes().
     */
    void setCurrentMode(String modeName);
    
    /** Gets the index number of the current mode. 
     * @return the index number of the mode. Index number corresponds with the item order of the list from getAvailableModes().
     */
    int getCurrentMode();
    
    /** Gets the number of supported modes
     * @return the number of supported modes
     */
    int getModeCount();

}
