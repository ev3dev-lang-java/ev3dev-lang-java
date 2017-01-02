package ev3dev.hardware.sensors;

import lejos.robotics.SampleProvider;


public interface SensorMode extends SampleProvider
{
    /**
     * return a string description of this sensors mode
     * @return The description/name of this mode
     */
    public String getName();
    
    // TODO: Return additional mode information

}
