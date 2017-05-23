package ev3dev.sensors;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EV3Button {

    public static final String SYSTEM_EVENT_PATH = "/dev/input/by-path/platform-gpio-keys.0-event";

    public static final int BUTTON_UP = 103;
    public static final int BUTTON_DOWN = 108;
    public static final int BUTTON_LEFT = 105;
    public static final int BUTTON_RIGHT = 106;
    public static final int BUTTON_ENTER = 28;
    public static final int BUTTON_BACKSPACE = 14;

    private int button;

    public EV3Button(int button) {
        if (button != BUTTON_UP && button != BUTTON_DOWN && button != BUTTON_LEFT &&
                button != BUTTON_RIGHT && button != BUTTON_ENTER && button != BUTTON_ENTER &&
                button != BUTTON_BACKSPACE){
            throw new RuntimeException("The button that you specified does not exist. Better use the integer fields like Button.BUTTON_UP");
        }
        this.button = button;
    }

    /**
     * Returns whether the button is pressed.
     * @return Boolean that the button is pressed.
     */
    public boolean isPressed(){
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(SYSTEM_EVENT_PATH));
            byte[] val = new byte[16];
            in.readFully(val);
            in.close();
            return test_bit(button, val);
        } catch (FileNotFoundException e){
            System.err.println("Error: Are you running this on your EV3? You must run it on your EV3.\n If you still have problems, report a issue to \"mob41/ev3dev-lang-java\".");
            e.printStackTrace();
            System.exit(-1);
            return false;
        } catch (IOException e){
            System.err.println("### ERROR MESSAGE ###\nError: Unexpected error! Report an issue to \"mob41/ev3dev-lang-java\" now, with logs!\n === STACK TRACE ===");
            e.printStackTrace();
            System.err.println("=== END STACK TRACE ===\nError: Unexpected error! Report an issue to \"mob41/ev3dev-lang-java\" now, with logs!\n ### END MESSAGE ###");
            System.exit(-1);
            return false;
        }
    }

    private static boolean test_bit(int bit, byte[] bytes){
        System.out.println("Bit: " + Integer.toHexString((bytes[bit / 8] & (1 << (bit % 8))) ));
        return ((bytes[bit / 8] & (1 << (bit % 8))) != 1);
    }

    private static int EVIOCGKEY(int length){
        return 2 << (14+8+8) | length << (8+8) | ((int) 'E') << 8 | 0x18;
    }
}
