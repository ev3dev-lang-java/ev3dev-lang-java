package ev3dev.sensors;

import lejos.robotics.SampleProvider;


public interface SensorMode extends SampleProvider {

    /**
     * return a string description of this sensors mode
     * @return The description/name of this mode
     */
    String getName();

}
