package ev3dev.sensors;

import ev3dev.hardware.EV3DevPropertyLoader;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Keys;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link Key} interface for EV3.
 *
 * <p>The easiest way to get an instance of this class is to use one of the static constants of
 * the {@link Button} class.
 *
 * @author Anthony
 * @author Juan Antonio Brenha Moral
 * @author Jaroslav Tupy
 */
@Slf4j
public class EV3Key implements Key {

    public static final int BUTTON_UP = 0x67;
    public static final int BUTTON_DOWN = 0x6c;
    public static final int BUTTON_LEFT = 0x69;
    public static final int BUTTON_RIGHT = 0x6a;
    public static final int BUTTON_ENTER = 0x1c;
    public static final int BUTTON_BACKSPACE = 0x0e;  // == BUTTON_ESCAPE; kept for backward compatibility
    public static final int BUTTON_ESCAPE = 0x0e;
    public static final int BUTTON_ALL = 0xff;

    // VisibleForTesting
    static final byte STATE_KEY_DOWN = 1;
    static final byte STATE_KEY_UP = 0;

    // to look at the raw values, use: $ hexdump -e '16/1 "%02x " "\n"' < /dev/input/by-path/platform-gpio-keys.0-event

    private static final int EVENT_BUFFER_LEN = 16;
    private static final int KEY_ID_INDEX = 10;   // one of the BUTTON_* values
    private static final int KEY_STATE_INDEX = 12;   // 1 for down, 0 for up

    // a bit-wise OR of the MASK_* bits above (except for MASK_ALL) depending on which keys are currently pressed
    private static byte keyBits = 0;
    private static Map<KeyType, Set<KeyListener>> keyListeners = new HashMap<>(KeyType.values().length);

    static {
        Arrays.stream(KeyType.values()).forEach((type) -> keyListeners.put(
            type, new CopyOnWriteArraySet<>()));
        // the CopyOfWriteArraySet will spare us a lot of hussle with thread safety
    }

    // a single ever-running (or waiting) thread keeping track of the currently pressed/released buttons
    private static final Thread keyEventReader;

    static {
        keyEventReader = new Thread(() -> {
            final byte[] event = new byte[EVENT_BUFFER_LEN];

            final EV3DevPropertyLoader ev3DevPropertyLoader = new EV3DevPropertyLoader();
            final Properties ev3DevProperties = ev3DevPropertyLoader.getEV3DevProperties();
            final String SYSTEM_EVENT_PATH = ev3DevProperties.getProperty("ev3.key");

            try (final DataInputStream in = new DataInputStream(new FileInputStream(SYSTEM_EVENT_PATH))) {
                while (true) {
                    // reading the event
                    in.readFully(event);

                    // storing the change
                    final byte keyId = event[KEY_ID_INDEX];
                    final byte keyState = event[KEY_STATE_INDEX];
                    if (keyId != 0) {
                        // for some reason, every event is followed with another one
                        // where everything except the timestamp is zero
                        processKeyEvent(keyId, keyState);
                    }
                }

            } catch (final IOException e) {
                LOGGER.error("Failed to read key press: " + e.getLocalizedMessage());
                // TODO: something like an exponential backoff reading restart in an error case like this
            }
        });
        keyEventReader.setDaemon(true);  // this causes this thread to terminate when the main thread terminates
        keyEventReader.start();     // non-blocking call to start the thread
    }

    // package-private such that it's visible from test
    enum KeyType {
        UP("UP", BUTTON_UP, Keys.ID_UP),   // notice that the Keys.ID_* are single bit masks (verified in unit test)
        DOWN("DOWN", BUTTON_DOWN, Keys.ID_DOWN),
        LEFT("LEFT", BUTTON_LEFT, Keys.ID_LEFT),
        RIGHT("RIGHT", BUTTON_RIGHT, Keys.ID_RIGHT),
        ENTER("ENTER", BUTTON_ENTER, Keys.ID_ENTER),
        ESCAPE("ESCAPE", BUTTON_ESCAPE, Keys.ID_ESCAPE),
        ALL("ALL", BUTTON_ALL, 0xff);
        // the 0xff must cover all bits of individual keys above (verified in unit tests)

        private static final Map<Byte, KeyType> LOOKUP = Arrays.stream(KeyType.values())
            .collect(Collectors.toMap(KeyType::getId, (keyType) -> keyType));

        private final String name;
        private final byte id;
        private final byte bitMask;

        KeyType(final String name, final int id, final int bitMask) {
            this.name = name;
            this.id = (byte) id;
            this.bitMask = (byte) bitMask;
        }

        public static KeyType of(final int id) {
            return Optional.ofNullable(LOOKUP.get((byte) id))
                .orElseThrow(() -> new IllegalArgumentException(
                    String.format("No such keyType (%d), please use one of the EV3Key.BUTTON_* constants", id)));
        }

        public String getName() {
            return name;
        }

        public byte getId() {
            return id;
        }

        public byte getBitMask() {
            return this.bitMask;
        }

        public boolean isPressed() {
            return (KeyType.ALL == this ? (keyBits > 0) : ((keyBits & this.bitMask) > 0));
        }
    }

    private KeyType keyType;

    /**
     * Create an Instance of EV3Key.
     *
     * @param keyType keyType
     */
    public EV3Key(final KeyType keyType) {
        this.keyType = Objects.requireNonNull(keyType);
    }

    /**
     * Create an Instance of EV3Key by the numeric key ID.
     *
     * @param id id
     */
    public EV3Key(final int id) {
        this(KeyType.of(id));
    }

    /**
     * Returns the ID of this key.
     *
     * @return one of the BUTTON_* constants
     */
    @Override
    public int getId() {
        return this.keyType.getId();
    }

    /**
     * A non-blocking check of whether this key is currently pressed.
     *
     * <p>This is
     *
     * @return true for pressed, false otherwise
     */
    @Override
    public boolean isDown() {
        return this.keyType.isPressed();
    }

    /**
     * A non-blocking check of whether this key is currently released.
     *
     * @return true for released, false otherwise
     */
    @Override
    public boolean isUp() {
        return (!this.keyType.isPressed());
    }

    /**
     * A blocking call that will return once this key gets pressed.
     *
     * <p>This method returns immediately if this key is pressed already.
     */
    @Override
    public void waitForPress() {
        synchronized (keyEventReader) {
            while (!this.isDown()) {   // we return immediately if this key is already pressed
                try {
                    keyEventReader.wait();
                } catch (final InterruptedException e) {
                    LOGGER.warn("Interrupted while waiting for {} key press: {}",
                        this.keyType.getName(), e.getLocalizedMessage());
                }
            }
        }
    }

    /**
     * A blocking call that will first wait for this key being pressed and then it waits for this key being released.
     *
     * <p>Unlike {@link #waitForPress()}, this method will always block because this key cannot be already
     * pressed and released at the same time. At the least, this key will be pressed so this method will only
     * wait for it to be released.
     */
    @Override
    public void waitForPressAndRelease() {
        this.waitForPress();
        // this will either return immediately of this key is already pressed or wait until it gets pressed
        synchronized (keyEventReader) {
            while (this.isDown()) {
                try {
                    keyEventReader.wait();
                } catch (final InterruptedException e) {
                    LOGGER.warn("Interrupted while waiting for {} key release: {}",
                        this.keyType.getName(), e.getLocalizedMessage());
                }
            }
        }
    }

    /**
     * Adds a listener for this key's 'pressed' and 'released' events.
     *
     * <p>If this is the Button#ALL key, the listener will be getting notifications for any key events.
     *
     * @param keyListener keyListener
     */
    @Override
    public void addKeyListener(final KeyListener keyListener) {
        keyListeners.get(this.keyType).add(Objects.requireNonNull(keyListener));
    }

    /**
     * Removes the given key event listener.
     *
     * @param keyListener keyListener
     */
    public void removeKeyListener(final KeyListener keyListener) {
        keyListeners.get(this.keyType).remove(Objects.requireNonNull(keyListener));
    }

    @Override
    public void simulateEvent(final int i) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Returns the name of this key.
     *
     * @return e.g. "LEFT" or "ENTER"
     */
    @Override
    public String getName() {
        return this.keyType.getName();
    }

    /**
     * @param obj obj
     * @return true if the {@code obj} is an instance of {@link Key} and if the button IDs are the same.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null || !(obj instanceof Key)) {
            return false;
        } else if (obj == this) {  // pointer comparison
            return true;
        } else {
            return ((int) this.keyType.getId() == ((Key) obj).getId());
        }
    }

    /**
     * @return the button ID based hash code
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(this.keyType.getId());
    }

    @Override
    public String toString() {
        return String.format("%s:%s", this.getClass().getSimpleName(), this.keyType.getName());
    }

    // package-private such that it's VisibleForTesting
    static void processKeyEvent(final byte keyId, final byte keyState) {
        final KeyType keyType = KeyType.of(keyId);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("KeyType {} {}", keyType.name, (keyState == 0 ? "released" : "pressed"));
        }

        if (keyState == STATE_KEY_UP) {
            keyBits ^= keyType.bitMask;   // clearing the key bit
            broadcastToListeners(keyType, KeyListener::keyReleased);
        } else if (keyState == STATE_KEY_DOWN) {
            keyBits |= keyType.bitMask;   // setting the key bit
            broadcastToListeners(keyType, KeyListener::keyPressed);
        } else {
            LOGGER.warn("Unexpected key state: " + keyState);
        }
    }

    // TODO: parhaps notify listeners in a separate Thread to avoid blocking the key event reader thread
    // TODO: ... or not - Threads consume resources and notifying in a separate Thread
    // also means the events may no longer come in correct order
    private static void broadcastToListeners(
        final KeyType keyType, final BiConsumer<KeyListener, Key> notificationMethod) {

        // key-specific listeners
        keyListeners.get(keyType).forEach((listener) -> notificationMethod.accept(listener, new EV3Key(keyType)));
        // all-key listeners
        keyListeners.get(KeyType.ALL).forEach((listener) -> notificationMethod.accept(listener, new EV3Key(keyType)));
        synchronized (keyEventReader) {
            keyEventReader.notifyAll();
        }
    }
}
