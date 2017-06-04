package ev3dev.sensors;

import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import org.slf4j.Logger;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/***
 * Provide a simple implementation to detect if any button is pressed.
 * Note: It is necessary to review in next iteration how to manage better the buttons.
 *
 * @author Anthony, Juan Antonio Brenha Moral
 *
 */
public class EV3Key implements Key {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(EV3Key.class);

	private static final String SYSTEM_EVENT_PATH = "/dev/input/by-path/platform-gpio-keys.0-event";
	
	public static final int BUTTON_UP = 103;
	public static final int BUTTON_DOWN = 108;
	public static final int BUTTON_LEFT = 105;
	public static final int BUTTON_RIGHT = 106;
	public static final int BUTTON_ENTER = 28;
	public static final int BUTTON_BACKSPACE = 14;
	public static final int BUTTON_ALL = 4000;

	private int button;

	/**
	 * Create an Instance of EV3Key.
	 * @param button
	 */
	public EV3Key(int button) {
		//TODO Use an ENUM
		if (
			button != BUTTON_UP &&
			button != BUTTON_DOWN &&
			button != BUTTON_LEFT &&
			button != BUTTON_RIGHT &&
			button != BUTTON_ENTER &&
			button != BUTTON_ENTER &&
			button != BUTTON_BACKSPACE &&
			button != BUTTON_ALL) {
			throw new RuntimeException("The button that you specified does not exist. Better use the integer fields like EV3Key.BUTTON_UP");
		}
		this.button = button;
	}
	
	/**
	 * Returns whether the button is pressed.
	 * @return Boolean that the button is pressed.
	 */
	private boolean isPressed(){
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(SYSTEM_EVENT_PATH));
			byte[] val = new byte[16];
			in.readFully(val);
			in.close();
			//TODO Improve this logic
			if(button == BUTTON_ALL){
				return true;
			}
			return test_bit(button, val);
		} catch (IOException e){
			log.error(e.getLocalizedMessage());
			return false;
		}
	}
	
	private static boolean test_bit(int bit, byte[] bytes){
		System.out.println("Bit: " + Integer.toHexString((bytes[bit / 8] & (1 << (bit % 8))) ));
	    return ((bytes[bit / 8] & (1 << (bit % 8))) != 1);
	}

	@Override
	public int getId() {
		return this.button;
	}

	@Override
	public boolean isDown() {
		log.debug("Not implemented");
		return false;
	}

	@Override
	public boolean isUp() {
		log.debug("Not implemented");
		return false;
	}

	@Override
	public void waitForPress() {
		isPressed();
	}

	@Override
	public void waitForPressAndRelease() {
		log.debug("Not implemented");
	}

	@Override
	public void addKeyListener(KeyListener keyListener) {
		log.debug("Not implemented");
	}

	@Override
	public void simulateEvent(int i) {
		log.debug("Not implemented");
	}

	@Override
	public String getName() {
		log.debug("Not implemented");
		return null;
	}
}