package lejos.robotics;

public interface Calibrate {
	
	/**
	 * Starts calibration.
	 * Must call stopCalibration() when done.
	 */
	void startCalibration();
	
	/**
	 * Ends calibration sequence.
	 */
	void stopCalibration();
}
