package ev3dev.hardware.sensor.ev3;

import ev3dev.hardware.sensor.BaseSensor;
import ev3dev.hardware.sensor.SensorMode;

/*
import lejos.hardware.port.AnalogPort;
import lejos.hardware.port.Port;
*/
/**
 * Basic sensor driver for the Lego EV3 Touch sensor
 * @author andy
 *
 */
/**
 * <b>Lego EV3 Touch sensor</b><br>
 * The analog EV3 Touch Sensor is a simple but exceptionally precise tool that detects when its front button is pressed or released.
 * 
 *  * 
 * <p>
 * <table border=1>
 * <tr>
 * <th colspan=4>Supported modes</th>
 * </tr>
 * <tr>
 * <th>Mode name</th>
 * <th>Description</th>
 * <th>unit(s)</th>
 * <th>Getter</th>
 * </tr>
 * <tr>
 * <td>Touch</td>
 * <td>Detects when its front button is pressed</td>
 * <td>Binary</td>
 * <td> {@link #getTouchMode() }</td>
 * </tr>
 * </table>
 * 
 * 
 * 
 * 
 * See <a href="http://www.ev-3.net/en/archives/846"> Sensor Product page </a>
 * See <a href="http://sourceforge.net/p/lejos/wiki/Sensor%20Framework/"> The
 *      leJOS sensor framework</a>
 * See {@link lejos.robotics.SampleProvider leJOS conventions for
 *      SampleProviders}
 * 
 *      <p>
 * 
 * 
 * @author Your name
 * 
 */
public class EV3TouchSensor extends BaseSensor 
{
    
    public EV3TouchSensor(String port)
    {
        //super(port);
        init();
    }

    /*
    public EV3TouchSensor(Port port)
    {
        super(port);
        init();
    }
    */
    
    protected void init() {
      setModes(new SensorMode[]{ new TouchMode() }); 
    }

    
   
    
    /**
     * <b>Lego EV3 Touch sensor, Touch mode</b><br>
     * Detects when its front button is pressed
     * 
     * <p>
     * <b>Size and content of the sample</b><br>
     * The sample contains one element, a value of 0 indicates that the button is not presse, a value of 1 indicates the button is pressed.
     * 
     * <p>
     * 
     * @return A sampleProvider
     * See {@link lejos.robotics.SampleProvider leJOS conventions for
     *      SampleProviders}
     */
    public SensorMode getTouchMode()
    {
        return getMode(0);
    }

    
    private class TouchMode implements SensorMode {
    	
        public int sampleSize()
        {
            return 1;
        }

        public void fetchSample(float[] sample, int offset)
        {
            //sample[offset] = (port.getPin6() > EV3SensorConstants.ADC_REF/2f ? 1.0f : 0.0f);
        }

        public String getName()
        {
           return "Touch";
        }
     
    }

}
