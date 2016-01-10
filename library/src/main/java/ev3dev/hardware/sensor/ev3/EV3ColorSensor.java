package ev3dev.hardware.sensor.ev3;

import java.io.File;

import ev3dev.hardware.sensor.BaseSensor;
import ev3dev.hardware.sensor.SensorMode;
import ev3dev.utils.Sysfs;
import lejos.robotics.Color;
import lejos.robotics.ColorIdentifier;
import lejos.robotics.LampController;
import lejos.robotics.SampleProvider;


/**
 * <b>EV3 color sensor</b><br>
 * The digital EV3 Color Sensor distinguishes between eight different colors. It also serves as a light sensor by detecting light intensities. 
 * 
 * 
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
 * <td>Color ID</td>
 * <td>Measures the color ID of a surface</td>
 * <td>Color ID</td>
 * <td> {@link #getColorIDMode() }</td>
 * </tr>
 * <tr>
 * <td>Red</td>
 * <td>Measures the intensity of a reflected red light </td>
 * <td>N/A, Normalized to (0-1) </td>
 * <td> {@link #getRedMode() }</td>
 * </tr>
 * <tr>
 * <td>RGB</td>
 * <td>Measures the RGB color of a surface</td>
 * <td>N/A, Normalized to (0-1)</td>
 * <td> {@link #getRGBMode() }</td>
 * </tr>
 * <tr>
 * <td>Ambient</td>
 * <td>Measures the ambient light level</td>
 * <td>N/A, Normalized to (0-1)</td>
 * <td> {@link #getAmbientMode() }</td>
 * </tr>
 * </table>
 * 
 * 
 * <p>
 * <b>Sensor configuration</b><br>
 * The flood light of the sensor can be put on or off using the setFloodlight methods.
 * 
 * <p>
 * 
 * See <a href="http://www.ev-3.net/en/archives/847"> Sensor Product page </a>
 * See <a href="http://sourceforge.net/p/lejos/wiki/Sensor%20Framework/"> The
 *      leJOS sensor framework</a>
 * See {@link lejos.robotics.SampleProvider leJOS conventions for
 *      SampleProviders}
 * 
 *      <p>
 * 
 * 
 * @author Andy
 * @author Juan Antonio Breña Moral
 * 
 */
public class EV3ColorSensor extends BaseSensor implements LampController, ColorIdentifier
{
    // TODO: decide what to do to the LampController and ColorIdentifier interfaces
    protected static int[] colorMap =
    {
        Color.NONE, Color.BLACK, Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED, Color.WHITE, Color.BROWN
    };
    protected static final int SWITCH_DELAY = 250;

    protected static final String COL_RESET = "RESET";//-1//??
    protected static final String COL_REFLECT = "COL-REFLECT";//0
    protected static final String COL_AMBIENT = "COL-AMBIENT";//1
    protected static final String COL_COLOR = "COL-COLOR";//2
    protected static final String COL_REFRAW = "REF-RAW";//3
    protected static final String COL_RGBRAW = "RGB-RAW";
    protected static final String COL_CAL = "COL-CAL";
    // following maps operating mode to lamp color
    // NONE, BLACK, BLUE, GREEN, YELLOW, RED, WHITE, BROWN
    protected static final int []lightColor = {Color.NONE, Color.RED, Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE};
    protected short[]raw = new short[3];
    
    private class ModeProvider implements SensorMode
    {
        final String mode;
        final int sampleSize;
        final String name;
        
        ModeProvider(String name, String mode, int sz)
        {
            this.name = name;
            this.mode = mode;
            sampleSize = sz;
        }

        @Override
        public int sampleSize()
        {
            return sampleSize;
        }
        
        @Override
        public void fetchSample(float[] sample, int offset)
        {
            switchMode(mode, SWITCH_DELAY);
            if (sampleSize == 1){
        		String attribute = "value0";
        		//String rawValue = Sysfs.readString(this.pathDevice + "/" +  attribute);
                sample[offset] = (float) 1;//Float.valueOf(rawValue);//(port.getByte() & 0xff)/100.0f;
            } else
            {
                //port.getShorts(raw, 0, raw.length);
                for(int i = 0; i < sampleSize; i++)
                    sample[offset+i] = (float)raw[i]/1020.0f;
            }
        }

        @Override
        public String getName()
        {
            return name;
        }
        
    }

    protected void initModes()
    {
        setModes(
        		new SensorMode[]{
        				new ColorIDMode(this.getPathDevice()), 
        				new RedMode(this.getPathDevice()),
        				new RGBMode(this.getPathDevice()),
        				new AmbientMode(this.getPathDevice())
        		});
    }


	public EV3ColorSensor(String sensorPort) {
		super(sensorPort);
		initModes();
	}


    /** {@inheritDoc}
     */    
    @Override
    public int getColorID()
    {
        setFloodlight(Color.WHITE);
        return 0; //colorMap[port.getByte()];
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
        switch (color)
        {
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

    
    /**
     * <b>EV3 color sensor, Color ID mode</b><br>
     * Measures the color ID of a surface. The sensor can identify 8 unique colors (NONE, BLACK, BLUE, GREEN, YELLOW, RED, WHITE, BROWN).
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
    public SensorMode getColorIDMode()
    {
        return getMode(0);
    }
    
    private class ColorIDMode implements SampleProvider, SensorMode{

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
            sample[offset]= 0;//colorMap[port.getByte()];
			String attribute = "value0";
			String rawValue = Sysfs.readString(this.pathDevice + "/" +  attribute);
			
			float raw = -1;
			try {
				raw = Float.parseFloat(rawValue);
			} catch (NumberFormatException e) {
				raw = -1;			
			}
	      sample[offset] = raw;
        }

        @Override
        public String getName() {
            return "ColorID";
        }
        
    }
    
 
    /**
     * <b>EV3 color sensor, Red mode</b><br>
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
    public SensorMode getRedMode()
    {
        return getMode(1);
    }

	  private class RedMode implements SampleProvider, SensorMode {

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
		      //port.getShorts(raw, 0, raw.length);
				String attribute = "value0";
				String rawValue = Sysfs.readString(this.pathDevice + "/" +  attribute);
				
				float raw = -1;
				try {
					raw = Float.parseFloat(rawValue);
				} catch (NumberFormatException e) {
					raw = -1;			
				}
		      sample[offset] = raw;
		    }

		    @Override
		    public String getName() {
		      return "Red";
		    }

		  }
    

    /**
     * <b>EV3 color sensor, Ambient mode</b><br>
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
    
	  private class AmbientMode implements SampleProvider, SensorMode {

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
		      //port.getShorts(raw, 0, raw.length);
				String attribute = "value0";
				String rawValue = Sysfs.readString(this.pathDevice + "/" +  attribute);
				
				float raw = -1;
				try {
					raw = Float.parseFloat(rawValue);
				} catch (NumberFormatException e) {
					raw = -1;			
				}
		      sample[offset] = raw;
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
     * <b>EV3 color sensor, RGB mode</b><br>
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
    public SensorMode getRGBMode()
    {
        //TODO: Should this return 3 or 4 values, 4 values would require an extra mode switch to get ambient value.    	
    	return getMode(2);
    }
    
	  private class RGBMode implements SampleProvider, SensorMode {

		    private static final float toSI = -1;

	    	private File pathDevice = null;
	    	
	        public RGBMode(File pathDevice) {
	        	this.pathDevice = pathDevice;
			}

			@Override
		    public int sampleSize() {
		      return 3;
		    }

		    @Override
		    public void fetchSample(float[] sample, int offset) {
		      switchMode(COL_RGBRAW, SWITCH_DELAY);
		      //port.getShorts(raw, 0, raw.length);
				String attribute1 = "value0";
				String rawValue1 = Sysfs.readString(this.pathDevice + "/" +  attribute1);
				
				float raw1 = -1;
				try {
					raw1 = Float.parseFloat(rawValue1);
				} catch (NumberFormatException e) {
					raw1 = -1;			
				}
				sample[0] = raw1;
				String attribute2 = "value1";
				String rawValue2 = Sysfs.readString(this.pathDevice + "/" +  attribute2);
				
				float raw2 = -1;
				try {
					raw2 = Float.parseFloat(rawValue2);
				} catch (NumberFormatException e) {
					raw1 = -1;			
				}
				sample[1] = raw2;
				String attribute3 = "value2";
				String rawValue3 = Sysfs.readString(this.pathDevice + "/" +  attribute3);
				
				float raw3 = -1;
				try {
					raw3 = Float.parseFloat(rawValue3);
				} catch (NumberFormatException e) {
					raw3 = -1;			
				}
				sample[2] = raw3;
		    }

		    @Override
		    public String getName() {
		      return "RGB";
		    }

		  }

}
