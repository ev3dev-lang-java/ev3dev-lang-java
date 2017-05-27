package ev3dev.sensors;

import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public @Slf4j class EV3Key implements Key {

    public static final int BUTTON_UP = 103;
    public static final int BUTTON_DOWN = 108;
    public static final int BUTTON_LEFT = 105;
    public static final int BUTTON_RIGHT = 106;
    public static final int BUTTON_ENTER = 28;
    public static final int BUTTON_BACKSPACE = 14;

    public static final int BUTTON_ALL = 0;

    private ButtonType button;

    //TODO Use an ENUM
    public EV3Key(ButtonType button) {
        /*
        if (button != BUTTON_UP &&
                button != BUTTON_DOWN &&
                button != BUTTON_LEFT &&
                button != BUTTON_RIGHT &&
                button != BUTTON_ENTER &&
                button != BUTTON_ENTER &&
                button != BUTTON_BACKSPACE &&
                button != BUTTON_ALL){
            throw new RuntimeException("The button that you specified does not exist. Better use the integer fields like Button.BUTTON_UP");
        }
        */
        this.button = button;
    }

    private ButtonType getButtonPress() {
        try (final FileInputStream is = new FileInputStream(this.file)) {
            // Each button press requires 4 reads -- 2 up and 2 down.
            final ButtonPress press1 = new ButtonPress(is);
            final ButtonPress press2 = new ButtonPress(is);
            final ButtonPress release1 = new ButtonPress(is);
            final ButtonPress release2 = new ButtonPress(is);
            return ButtonType.findByValue(press1.getCode());
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
            //throw new DeviceException(format("IOException: %s", e.getMessage()), e);
        }
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public boolean isDown() {
        log.debug("This feature is not implemented");
        return false;
    }

    @Override
    public boolean isUp() {
        log.debug("This feature is not implemented");
        return false;
    }

    @Override
    public void waitForPress() {
        while(true) {
            ButtonType type = getButtonPress();
            log.debug(type.name());
            log.debug(button.name());
            if(button.name().equals(ButtonType.ALL.name())){
                break;
            }
            if(button.name().equals(type.name())){
                break;
            }
        }
        /*
        while(!isPressed()){
            log.debug("Not pressed the right button");
        }*/


    }

    @Override
    public void waitForPressAndRelease() {
        log.debug("This feature is not implemented");
    }

    @Override
    public void addKeyListener(KeyListener listener) {
        log.debug("This feature is not implemented");
    }

    @Override
    public void simulateEvent(int event) {
        log.debug("This feature is not implemented");
    }

    @Override
    public String getName() {
        log.debug("This feature is not implemented");
        return null;
    }

    private final File file = new File("/dev/input/event0");

    private static class ButtonPress {
        private final short type;
        private final short code;
        private final int   value;

        private ButtonPress(final FileInputStream is)
                throws IOException {
            final byte[] buf = new byte[16];
            final int retval = is.read(buf);
            //if (retval == -1)
                //throw new DeviceException("Invalid file read");

            // The first 8 bytes are the time stamp (2 unsigned 64-bit integers, seconds and microseconds),
            // the next 2 bytes are the type (unsigned 16-bit integer),
            // the next 2 bytes are the code (unsigned 16-bit integer)
            // and the last 4 bytes are the value (unsigned 32-bit integer)

            this.type = ByteBuffer.wrap(Arrays.copyOfRange(buf, 8, 10)).order(ByteOrder.LITTLE_ENDIAN).getShort();
            this.code = ByteBuffer.wrap(Arrays.copyOfRange(buf, 10, 12)).order(ByteOrder.LITTLE_ENDIAN).getShort();
            this.value = ByteBuffer.wrap(Arrays.copyOfRange(buf, 12, 16)).order(ByteOrder.LITTLE_ENDIAN).getInt();
        }

        private short getType() { return this.type; }

        private short getCode() { return this.code; }

        private int getValue() { return this.value; }
    }

    public enum ButtonType {

        UP(103),
        DOWN(108),
        LEFT(105),
        RIGHT(106),
        ENTER(28),
        BACKSPACE(14),
        ALL(0);

        private final int value;

        ButtonType(final int value) {
            this.value = value;
        }

        public int getValue() { return this.value; }

        public static ButtonType findByValue(final int value) {
            for (ButtonType type : ButtonType.values())
                if (type.getValue() == value)
                    return type;

            throw new RuntimeException();
            //throw new IllegalArgumentException(format("Invalid %s value: %d", ButtonType.class.getSimpleName(), value));
        }
    }
}
