package ev3dev.hardware.sensors.ev3;

import ev3dev.hardware.sensors.BaseSensor;
import ev3dev.hardware.sensors.SensorMode;
import ev3dev.utils.Sysfs;
import lejos.robotics.Color;
import lejos.robotics.ColorIdentifier;
import lejos.robotics.LampController;

import java.io.File;


/**
 * <b>EV3 color sensors</b><br>
 * The digital EV3 Color Sensor distinguishes between eight different colors. It also serves as a light sensors by detecting light intensities.
 *
 * <p>
 * <b>Sensor configuration</b><br>
 * The flood light of the sensors can be put on or off using the setFloodlight methods.
 * 
 * <p>
 * 
 * See <a href="http://www.ev-3.net/en/archives/847"> Sensor Product page </a>
 * See <a href="http://sourceforge.net/p/lejos/wiki/Sensor%20Framework/"> The
 *      leJOS sensors framework</a>
 * See {@link lejos.robotics.SampleProvider leJOS conventions for
 *      SampleProviders}
 * 
 *      <p>
 * 
 * 
 * @author Andy Shaw
 * @author Juan Antonio Breña Moral
 * 
 */
public class EV3ColorSensor extends BaseSensor implements LampController, ColorIdentifier {
    // TODO: decide what to do to the LampController and ColorIdentifier interfaces
    protected static int[] colorMap = {
        Color.NONE, Color.BLACK, Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED, Color.WHITE, Color.BROWN
    };

    private static final String LEGO_EV3_COLOR_SENSOR = "lego-ev3-color";

    private static final String COL_RESET = "RESET";//-1//??
    private static final String COL_REFLECT = "COL-REFLECT";//0
    private static final String COL_AMBIENT = "COL-AMBIENT";//1
    private static final String COL_COLOR = "COL-COLOR";//2
    protected static final String COL_REFRAW = "REF-RAW";//3
    private static final String COL_RGBRAW = "RGB-RAW";
    protected static final String COL_CAL = "COL-CAL";

    // following maps operating mode to lamp color
    // NONE, BLACK, BLUE, GREEN, YELLOW, RED, WHITE, BROWN
    private static final int []lightColor = {Color.NONE, Color.RED, Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE};
    protected short[]raw = new short[3];

    private void initModes() {
        setModes(
        		new SensorMode[]{
        				new ColorIDMode(this.PATH_DEVICE), 
        				new RedMode(this.PATH_DEVICE),
        				new RGBMode(this.PATH_DEVICE),
        				new AmbientMode(this.PATH_DEVICE)
        		});
    }

	public EV3ColorSensor(String portName) {
        super(portName, LEGO_UART_SENSOR, LEGO_EV3_COLOR_SENSOR);
		initModes();
	}

    /** {@inheritDoc}
     */    
    @Override
    public int getColorID() {
        setFloodlight(Color.WHITE);
        return 0; //colorMap[ports.getByte()];
    }

    /** {@inheritDoc}
     */    
    @Override
    public void setFloodlight(boolean floodlight)
    {
        setFloodlight(floodlight ? Color.RED : Color.NONE);
    }

    /** {@inheritDoc}
     */    
    @Override
    public boolean isFloodlightOn()
    {
        return lightColor[currentMode+1] != Color.NONE;
    }

    /** {@inheritDoc}
     */    
    @Override
    public int getFloodlight()
    {
         return lightColor[currentMode+1];
    }

    /** {@inheritDoc}
     */    
    @Override
    public boolean setFloodlight(int color)
    {
        String mode;
        switch (color) {
            case Color.BLUE:
                mode = COL_AMBIENT;
                break;
            case Color.WHITE:
                mode = COL_COLOR;
                break;
            case Color.RED:
                mode = COL_REFLECT;
                break;
            case Color.NONE:
                mode = COL_RESET;
                break;
            default:
                // TODO: Should we ignore a wrong color or throw an exception?
                throw new IllegalArgumentException("Invalid color specified");
        }
        switchMode(mode, SWITCH_DELAY);
        return true;
    }

    //TODO: Implement in the right way
    /**
     * <b>EV3 color sensors, Color ID mode</b><br>
     * Measures the color ID of a surface. The sensors can identify 8 unique colors (NONE, BLACK, BLUE, GREEN, YELLOW, RED, WHITE, BROWN).
     * 
     * <p>
     * <b>Size and content of the sample</b><br>
     * The sample contains one element containing the ID (0-7) of the detected color.
     * 
     * <p>
     * 
     * @return A sampleProvider
     * See {@link lejos.robotics.SampleProvider leJOS conventions for
     *      SampleProviders}
     */
    public SensorMode getColorIDMode() {
        return getMode(0);
    }
    
    private class ColorIDMode extends EV3DevSensorMode {

    	private File pathDevice = null;
    	
        public ColorIDMode(File pathDevice) {
        	this.pathDevice = pathDevice;
		}
    	
        @Override
        public int sampleSize() {
            return 1;
        }

        @Override
        public void fetchSample(float[] sample, int offset) {
            switchMode(COL_COLOR, SWITCH_DELAY);
            sample[offset]= 0;
            sample[offset]  = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0);
        }

        @Override
        public String getName() {
            return "ColorID";
        }
    }
    

    //TODO: Implement in the right way
    /**
     * <b>EV3 color sensors, Red mode</b><br>
     * Measures the level of reflected light from the sensors RED LED. 
     * 
     * <p>
     * <b>Size and content of the sample</b><br>
     * The sample contains one element containing the intensity level (Normalized between 0 and 1) of reflected light.
     * 
     * <p>
     * 
     * @return A sampleProvider
     * See {@link lejos.robotics.SampleProvider leJOS conventions for
     *      SampleProviders}
     */
    public SensorMode getRedMode() {
        return getMode(1);
    }

    private class RedMode extends EV3DevSensorMode {

        private static final float toSI = -1;

        private File pathDevice = null;

        public RedMode(File pathDevice) {
            this.pathDevice = pathDevice;
        }

        @Override
        public int sampleSize() {
          return 1;
        }

        @Override
        public void fetchSample(float[] sample, int offset) {
            switchMode(COL_REFLECT, SWITCH_DELAY);
            sample[offset]  = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0);
        }

        @Override
        public String getName() {
            return "Red";
        }

    }
    

    /**
     * <b>EV3 color sensors, Ambient mode</b><br>
     * Measures the level of ambient light while the sensors lights are off. 
     * 
     * <p>
     * <b>Size and content of the sample</b><br>
     * The sample contains one element containing the intensity level (Normalized between 0 and 1) of ambient light.
     * 
     * <p>
     * 
     * @return A sampleProvider
     * See {@link lejos.robotics.SampleProvider leJOS conventions for
     *      SampleProviders}
     */
    public SensorMode getAmbientMode()
    {
        return getMode(3);
    }
    
	  private class AmbientMode extends EV3DevSensorMode {

		    private static final float toSI = -1;

	    	private File pathDevice = null;
	    	
	        public AmbientMode(File pathDevice) {
	        	this.pathDevice = pathDevice;
			}

			@Override
		    public int sampleSize() {
		      return 1;
		    }

		    @Override
		    public void fetchSample(float[] sample, int offset) {
		    	switchMode(COL_AMBIENT, SWITCH_DELAY);
                sample[offset] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0);
		    }

		    @Override
		    public String getName() {
		      return "Ambient";
		    }

		  }
    
    /**
     * get a sample provider that returns the light values (RGB) when illuminated by a
     * white light source.
     * @return the sample provider
     */
    /**
     * <b>EV3 color sensors, RGB mode</b><br>
     * Measures the level of red, green and blue light when illuminated by a white light source.. 
     * 
     * <p>
     * <b>Size and content of the sample</b><br>
     * The sample contains 3 elements containing the intensity level (Normalized between 0 and 1) of red, green and blue light respectivily.
     * 
     * <p>
     * 
     * @return A sampleProvider
     * See {@link lejos.robotics.SampleProvider leJOS conventions for
     *      SampleProviders}
     */
    public SensorMode getRGBMode() {
        //TODO: Should this return 3 or 4 values, 4 values would require an extra mode switch to get ambient value.    	
    	return getMode(2);
    }
    
    private class RGBMode extends EV3DevSensorMode {

        private static final float toSI = -1;

        private File pathDevice = null;

        public RGBMode(final File pathDevice) {
            this.pathDevice = pathDevice;
        }

        @Override
        public int sampleSize() {
          return 3;
        }

        @Override
        public void fetchSample(float[] sample, int offset) {
            switchMode(COL_RGBRAW, SWITCH_DELAY);
            sample[0] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE0);
            sample[1] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE1);
            sample[2] = Sysfs.readFloat(this.pathDevice + "/" +  VALUE2);
        }

        @Override
        public String getName() {
          return "RGB";
        }

    }

}
