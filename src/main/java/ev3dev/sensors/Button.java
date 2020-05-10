package ev3dev.sensors;

import lejos.hardware.Key;
import org.slf4j.Logger;

/**
 * Abstraction for an NXT/EV3 button. Example:
 *
 * <pre>
 * EV3Key.ENTER.waitForPressAndRelease();
 * Sound.playTone(1000, 1);
 * </pre>
 *
 * <b>Notions:</b> The API is designed around two notions: states (up / down)
 * and events (press / release). It is said that a button is pressed (press
 * event), if its state changes from up to down. Similarly, it is said that a
 * button is released (release event), if its states changed from down to up.
 *
 * <b>Thread Safety</b>: All methods that return buttons states can be used
 * safely from multiple threads, even while a call to one of the waitFor*
 * methods active. However, it is not safe to invoke waitFor* methods in
 * parallel from different threads. This includes the waitFor* methods of
 * different buttons. For example EV3Key.ENTER.waitForPress() must not be
 * invoked in parallel to EV3Key.ESCAPE.waitForPress() or the static
 * EV3Key.waitForAnyEvent(). In case this is needed, it is strongly recommended
 * that you write your own Thread, which waits for button events and dispatches
 * the events to anyone who's interested.
 */
public class Button {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Button.class);

    /**
     * The Enter button.
     */
    public static final Key ENTER = new EV3Key(EV3Key.BUTTON_ENTER);
    /**
     * The Left button.
     */
    public static final Key LEFT = new EV3Key(EV3Key.BUTTON_LEFT);
    /**
     * The Right button.
     */
    public static final Key RIGHT = new EV3Key(EV3Key.BUTTON_RIGHT);
    /**
     * The Escape button.
     */
    public static final Key ESCAPE = new EV3Key(EV3Key.BUTTON_BACKSPACE);
    /**
     * The Up button.
     */
    public static final Key UP = new EV3Key(EV3Key.BUTTON_UP);
    /**
     * The Down button.
     */
    public static final Key DOWN = new EV3Key(EV3Key.BUTTON_DOWN);

    private static final Key ALL = new EV3Key(EV3Key.BUTTON_ALL);

    //public static final Keys keys = BrickFinder.getDefault().getKeys();

    /**
     * Waits for some button to be pressed or released. Which buttons have been
     * released or pressed is returned as a bitmask. The lower eight bits (bits
     * 0 to 7) indicate, which buttons have been pressed. Bits 8 to 15 indicate
     * which buttons have been released.
     *
     * @return the bitmask
     */
    public static int waitForAnyEvent() {
        log.debug("Not implemented");
        return 0;//keys.waitForAnyEvent();
    }

    /**
     * Waits for some button to be pressed or released. Which buttons have been
     * released or pressed is returned as a bitmask. The lower eight bits (bits
     * 0 to 7) indicate, which buttons have been pressed. Bits 8 to 15 indicate
     * which buttons have been released.
     *
     * @param timeout The maximum number of milliseconds to wait
     * @return the bitmask
     */
    public static int waitForAnyEvent(int timeout) {
        return 0;//keys.waitForAnyEvent(timeout);
    }

    /**
     * Waits for some button to be pressed. If a button is already pressed, it
     * must be released and pressed again.
     *
     * @param timeout The maximum number of milliseconds to wait
     * @return the ID of the button that has been pressed or in rare cases a
     *     bitmask of button IDs, 0 if the given timeout is reached
     */
    public static int waitForAnyPress(int timeout) {
        ALL.waitForPress();
        return 0;//keys.waitForAnyPress(timeout);
    }

    /**
     * Waits for some button to be pressed. If a button is already pressed, it
     * must be released and pressed again.
     *
     * @return the ID of the button that has been pressed or in rare cases a bitmask of button IDs
     */
    public static int waitForAnyPress() {
        return waitForAnyPress(0);
    }

}
