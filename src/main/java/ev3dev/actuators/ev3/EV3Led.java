package ev3dev.actuators.ev3;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.DataChannelRewriter;
import lejos.hardware.LED;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;

/**
 * This class provides methods for interacting with the LEDs on the EV3Brick.
 *
 * <p><i>Only EV3Bricks are supported.</i>
 */
@Slf4j
public class EV3Led extends EV3DevDevice implements LED, Closeable {

    /**
     * Directions of the LED.
     */
    public enum Direction {
        LEFT,
        RIGHT
    }

    private static final Direction[] directionArray = {Direction.LEFT,Direction.RIGHT};

    /**
     * @deprecated Use EV3LedDirection.LEFT instead.
     */
    @Deprecated
    public static final int LEFT = 0;
    /**
     * @deprecated Use EV3Led.Direction.RIGHT instead.
     */
    @Deprecated
    public static final int RIGHT = 1;

    private final Direction direction;

    private final DataChannelRewriter redWriter;
    private final DataChannelRewriter greenWriter;

    /**
     * Create an EV3LED object associated with the LED of the specified direction.
     *
     * @param direction the direction of the LED, either {@link Direction#LEFT} or {@link Direction#RIGHT}.
     * @throws RuntimeException if LED feature is not supported on the current platform.
     */
    public EV3Led(final Direction direction) {
        checkPlatform();

        //TODO Refactor
        if (direction == null) {
            LOGGER.error("You are not specifying any button.");
            throw new IllegalArgumentException("You are not specifying any button.");
        }

        this.direction = direction;

        String ledPath = EV3DevFileSystem.getRootPath();
        if (direction == Direction.LEFT) {
            redWriter = new DataChannelRewriter(ledPath + ev3DevProperties.getProperty("ev3.led.left.red"));
            greenWriter = new DataChannelRewriter(ledPath + ev3DevProperties.getProperty("ev3.led.left.green"));
        } else {
            redWriter = new DataChannelRewriter(ledPath + ev3DevProperties.getProperty("ev3.led.right.red"));
            greenWriter = new DataChannelRewriter(ledPath + ev3DevProperties.getProperty("ev3.led.right.green"));
        }
    }

    /**
     * Creates an EV3LED object associated with the LED of the specified direction.
     *
     * @param button the direction of the LED, should be either {@link #LEFT} or {@link #RIGHT}.
     * @throws RuntimeException if LED feature is not supported on the current platform.
     * @deprecated Use {@link #EV3Led(Direction)} instead.
     */
    @Deprecated
    public EV3Led(final int button) {
        this(directionArray[button]);
    }

    /**
     * Checks if the current platform support this feature.
     *
     * @throws RuntimeException if this feature is not supported on the current platform.
     */
    private void checkPlatform() {
        if (!CURRENT_PLATFORM.equals(EV3DevPlatform.EV3BRICK)) {
            LOGGER.error("This actuator is specific of: {}", EV3DevPlatform.EV3BRICK);
            throw new RuntimeException("This actuator is specific of: " + EV3DevPlatform.EV3BRICK);
        }
    }

    //TODO Add Enums for patterns
    /**
     * Sets the pattern of light to be shown with this LED.
     *
     * @param pattern the pattern to show with this LED.
     *
     *                0: Turns off the LED light;
     *                1/2/3: Static green/red/yellow light;
     *                4/5/6: Normal blinking green/red/yellow light, <i>not implemented</i>;
     *                7/8/9: Fast blinking green/red/yellow light, <i>not implemented</i>.
     */
    @Override
    public void setPattern(final int pattern) {

        final String off = Integer.toString(0);
        final String on = Integer.toString(255);

        if (pattern == 0) {
            greenWriter.writeString(off);
            redWriter.writeString(off);
        } else if (pattern == 1) {
            greenWriter.writeString(on);
            redWriter.writeString(off);
        } else if (pattern == 2) {
            greenWriter.writeString(off);
            redWriter.writeString(on);
        } else if (pattern == 3) {
            greenWriter.writeString(on);
            redWriter.writeString(on);
        } else if (pattern > 3) {
            LOGGER.debug("This feature is not implemented");
        }
    }

    /**
     * Returns the direction of the LED associated with this object.
     *
     * @return the direction of the LED, either {@link Direction#LEFT} or {@link Direction#RIGHT}.
     */
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void close() throws IOException {
        greenWriter.close();
        redWriter.close();
    }


}
