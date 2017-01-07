package lejos.robotics;

/** Abstraction for classes that fetch samples from a sensors and classes that are able to process samples.<br>
 * A sample is a measurement taken by a sensors at a single moment in time.
 * A sample can have one or more elements. The number of elements in a sample depends on the sensors (and sensors mode).
 * <br>
 * 
 * Sample providers comply with standards.
 * <UL>
 * <li>
 * Sample providers use standard units.<br>
 * <ul>
 * <li>Length in meters</li>
 * <li>Angle in degrees</li>
 * <li>Temperature in degrees celcius</li>
 * <li>Pressure in Pascal</li>
 * <li>Speed in m/s</li>
 * <li>Acceleration in m/s^2</li>
 * <li>etc...</li>
 * </ul>
 * </li>
 * <li>
 * When there is no clear unit a sample provider use normalized values in the range between 0 and 1.
 * </li>
 * <li>
 * Sample providers that measure spatial data use a right handed cartesian coordinate system with the X-axis pointing forwards, 
 * the Y-axis pointing to the left and the Z-axis pointing up. 
 * (The plug of a sensors is always on its back.)
 * </li>
 * <li>
 * A positive rotation of a mobile robot is a counterclockwise rotation. 
 * </li>
 * <li>
 * If a sample provider measures spatial data over more than one axis, the order of the elements in a sample corresponds with the X,Y and Z axis.
 * </li>
 * </UL>
 * @author Aswin Bouwmeester
 *
 */
public interface SampleProvider {
	
	/** Returns the number of elements in a sample.<br>
	 * The number of elements does not change during runtime.
	 * @return
	 * the number of elements in a sample
	 */
	int sampleSize();
	
	/** Fetches a sample from a sensors or filter.
	 * @param sample
	 * The array to store the sample in. 
	 * @param offset
	 * The elements of the sample are stored in the array starting at the offset position.
	 */
	void fetchSample(float[] sample, int offset);
}
