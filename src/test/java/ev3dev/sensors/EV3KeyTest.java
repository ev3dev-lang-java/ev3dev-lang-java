package ev3dev.sensors;

import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EV3KeyTest {

    // substitute for the org.apache.commons.lang3.tuple.Pair
    private static class Pair<L, R> {
        private L left;
        private R right;

        public static <L, R> Pair<L, R> of(final L left, final R right) {
            final Pair<L, R> pair = new Pair<>();
            pair.left = left;
            pair.right = right;
            return pair;
        }

        public L getLeft() {
            return left;
        }
        public R getRight() {
            return right;
        }

        @Override
        public String toString() {
            return String.format("{%s, %s}", this.left, this.right);
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == null || ! (obj instanceof Pair)) {
                return false;
            } else if (obj == this) {
                return true;
            } else {
                final Pair<?, ?> cmp = (Pair<?, ?>)obj;
                return (Objects.equals(this.left, cmp.left) &&
                        Objects.equals(this.right, cmp.right));
            }
        }
    }

    // kind of a substitute for Guava's ImmutableList.Builder
    private static class ImmutableListBuilder<T> {
        private List<T> list = new ArrayList<>();

        public ImmutableListBuilder<T> add(final T entry) {
            this.list.add(entry);
            return this;
        }

        public List<T> build() {
            return Collections.unmodifiableList(this.list);
        }
    }

    private static enum KeyEvent {
        PRESSED,
        RELEASED
    }

    private static class TestKeyListener implements KeyListener {
        public List<Pair<Key, KeyEvent>> keyEvents = new ArrayList<>();

        @Override
        public void keyPressed(Key key) {
            this.keyEvents.add(Pair.of(key, KeyEvent.PRESSED));
        }
        @Override
        public void keyReleased(Key key) {
            this.keyEvents.add(Pair.of(key, KeyEvent.RELEASED));
        }

        public String toString() {
            final StringJoiner joiner = new StringJoiner(" -> ");
            this.keyEvents.forEach((event) -> joiner.add(String.format("%s:%s", event.getLeft().getName(), event.getRight().name())));
            return joiner.toString();
        }
    }

    private static class TestKey implements Key {
        private int id;

        public TestKey(final int id) {
            this.id = id;
        }

        @Override
        public int getId() {
            return this.id;
        }
        @Override
        public boolean isDown() {
            return false;
        }
        @Override
        public boolean isUp() {
            return false;
        }
        @Override
        public void waitForPress() {
        }
        @Override
        public void waitForPressAndRelease() {
        }
        @Override
        public void addKeyListener(KeyListener keyListener) {
        }
        @Override
        public void simulateEvent(int i) {
        }
        @Override
        public String getName() {
            return null;
        }
    }

    private static class TestKeyEventWaiter extends Thread {
        private final EV3Key key;
        private final boolean pressAndReleaseWait;
        private Boolean conditionMet;

        public TestKeyEventWaiter(final String name, final EV3Key.KeyType keyType, final boolean presAndReleaseWait) {
            super(name);
            this.key = new EV3Key(keyType);
            this.pressAndReleaseWait = presAndReleaseWait;
        }

        @Override
        public void run() {
            // performing the wait
            if (this.pressAndReleaseWait) {
                this.key.waitForPressAndRelease();
            } else {
                this.key.waitForPress();
            }
            // making sure the key is in the expected state
            // NOTE: there is a potential race condition in this check, so the test performs a wait to give this check
            // enough time before simulating another key event
            if (this.pressAndReleaseWait) {
                conditionMet = this.key.isUp();
            } else {
                conditionMet = this.key.isDown();
            }
        }
    }

    private static final Set<EV3Key.KeyType> SPECIFIC_KEY_TYPES = Arrays.stream(EV3Key.KeyType.values())
            .filter((keyType) -> EV3Key.KeyType.ALL != keyType).collect(Collectors.toSet());
    private static final Set<EV3Key> SPECIFIC_KEYS = SPECIFIC_KEY_TYPES.stream()
            .map(EV3Key::new).collect(Collectors.toSet());

    @Test
    public void testButtonBits() {
        final AtomicInteger mask = new AtomicInteger(0);

        // checking that each KeyType's bit mask consists of a single bit each of which occupies a different position
        final SortedSet<Byte> sortedBits = SPECIFIC_KEY_TYPES.stream()
                .map(EV3Key.KeyType::getBitMask).collect(Collectors.toCollection(TreeSet::new));
        final AtomicInteger shift = new AtomicInteger(0);
        sortedBits.forEach((bit) -> Assert.assertEquals((int)bit, 1 << shift.getAndIncrement()));

        // checking that the special "ALL" type covers all the buttons
        SPECIFIC_KEY_TYPES.stream().forEach((keyType) -> Assert.assertEquals(
                keyType.getBitMask(), EV3Key.KeyType.ALL.getBitMask() & keyType.getBitMask()));
    }

    @Test
    public void testEqualsAndHashCode() {
        final EV3Key enterKey = new EV3Key(EV3Key.BUTTON_LEFT);

        // intentionally calling Assert.assertEquals(EV3Key.equals(...)) to demonstrate better what we're testing
        Assert.assertFalse(enterKey.equals(null));
        Assert.assertFalse(enterKey.equals(enterKey.getId()));  // comparison with integer intentionally doesn't work as it's ambiguous (id vs. Key.* constants vs. Keys.ID_* constants)
        Assert.assertTrue(enterKey.equals(enterKey));
        Assert.assertTrue(enterKey.equals(new EV3Key(EV3Key.BUTTON_LEFT)));
        Assert.assertTrue(enterKey.equals(new EV3Key(EV3Key.KeyType.LEFT)));
        Assert.assertFalse(enterKey.equals(new EV3Key(EV3Key.KeyType.RIGHT)));
        Assert.assertTrue(enterKey.equals(new TestKey(EV3Key.BUTTON_LEFT)));
        Assert.assertFalse(enterKey.equals(new TestKey(EV3Key.BUTTON_RIGHT)));

        final Set<Key> uniqueKeys = new HashSet<>();
        uniqueKeys.add(enterKey);
        uniqueKeys.add(new EV3Key(EV3Key.KeyType.LEFT));
        uniqueKeys.add(new EV3Key(EV3Key.BUTTON_LEFT));
        Assert.assertEquals(1, uniqueKeys.size());
    }

    // NOTE: we'll be simulating key presses here and since unit tests may run in parallel, this test may influence other tests in this class
    @Test
    public void testKeyListenersAndWaits() throws Exception {
        final TestKeyListener leftKeyListener = new TestKeyListener();
        final TestKeyListener leftRightKeyListener = new TestKeyListener();
        final TestKeyListener allKeyListener = new TestKeyListener();

        Button.LEFT.addKeyListener(leftKeyListener);
        Button.LEFT.addKeyListener(leftRightKeyListener);
        Button.RIGHT.addKeyListener(leftRightKeyListener);
        new EV3Key(EV3Key.BUTTON_ALL).addKeyListener(allKeyListener);  // the Button.ALL field is private; yet someone may construct the 'ALL' Key like this anyway

        Assert.assertTrue(SPECIFIC_KEYS.stream().allMatch((key) -> key.isUp()));
        Assert.assertFalse(SPECIFIC_KEYS.stream().anyMatch((key) -> key.isDown()));

        final TestKeyEventWaiter leftKeyPressAndReleaseWaiter = new TestKeyEventWaiter("LeftKeyPressAndRelease", EV3Key.KeyType.LEFT, true);
        leftKeyPressAndReleaseWaiter.start();
        final TestKeyEventWaiter rightKeyPressWaiter = new TestKeyEventWaiter("rightKeyPress", EV3Key.KeyType.RIGHT, false);
        rightKeyPressWaiter.start();
        final TestKeyEventWaiter allKeyPressWaiter = new TestKeyEventWaiter("allKeyPress", EV3Key.KeyType.ALL, false);
        allKeyPressWaiter.start();

        Thread.sleep(100L);  // to give the waiter threads enough time to start
        Assert.assertTrue(leftKeyPressAndReleaseWaiter.isAlive());
        Assert.assertTrue(rightKeyPressWaiter.isAlive());
        Assert.assertTrue(allKeyPressWaiter.isAlive());

        EV3Key.processKeyEvent((byte)EV3Key.BUTTON_LEFT, EV3Key.STATE_KEY_DOWN);
        Assert.assertTrue(Button.LEFT.isDown());
        assertWaiters(new ImmutableListBuilder<Pair<TestKeyEventWaiter, Boolean>>()
                .add(Pair.of(leftKeyPressAndReleaseWaiter, true))
                .add(Pair.of(rightKeyPressWaiter, true))
                .add(Pair.of(allKeyPressWaiter, false)).build());   // triggered right after the first (or any) key press

        EV3Key.processKeyEvent((byte)EV3Key.BUTTON_RIGHT, EV3Key.STATE_KEY_DOWN);
        Assert.assertTrue(Button.LEFT.isDown());
        Assert.assertTrue(Button.RIGHT.isDown());
        assertWaiters(new ImmutableListBuilder<Pair<TestKeyEventWaiter, Boolean>>()
                .add(Pair.of(leftKeyPressAndReleaseWaiter, true))
                .add(Pair.of(rightKeyPressWaiter, false))   // OK, right key pressed
                .add(Pair.of(allKeyPressWaiter, false)).build());

        EV3Key.processKeyEvent((byte)EV3Key.BUTTON_RIGHT, EV3Key.STATE_KEY_UP);
        Assert.assertTrue(Button.LEFT.isDown());
        Assert.assertTrue(Button.RIGHT.isUp());
        assertWaiters(new ImmutableListBuilder<Pair<TestKeyEventWaiter, Boolean>>()     // no change
                .add(Pair.of(leftKeyPressAndReleaseWaiter, true))
                .add(Pair.of(rightKeyPressWaiter, false))
                .add(Pair.of(allKeyPressWaiter, false)).build());

        EV3Key.processKeyEvent((byte)EV3Key.BUTTON_LEFT, EV3Key.STATE_KEY_UP);
        Assert.assertTrue(Button.LEFT.isUp());
        Assert.assertTrue(Button.RIGHT.isUp());
        assertWaiters(new ImmutableListBuilder<Pair<TestKeyEventWaiter, Boolean>>()
                .add(Pair.of(leftKeyPressAndReleaseWaiter, false))   // now also the left key is up
                .add(Pair.of(rightKeyPressWaiter, false))
                .add(Pair.of(allKeyPressWaiter, false)).build());

        EV3Key.processKeyEvent((byte)EV3Key.BUTTON_ESCAPE, EV3Key.STATE_KEY_DOWN);
        Assert.assertTrue(Button.ESCAPE.isDown());
        Assert.assertTrue(new EV3Key(EV3Key.BUTTON_BACKSPACE).isDown());  // sanity check

        Assert.assertEquals(new ImmutableListBuilder<Pair<EV3Key, KeyEvent>>()
                .add(Pair.of(new EV3Key(EV3Key.BUTTON_LEFT), KeyEvent.PRESSED))
                .add(Pair.of(new EV3Key(EV3Key.BUTTON_LEFT), KeyEvent.RELEASED))
                .build(), leftKeyListener.keyEvents);
        Assert.assertEquals(new ImmutableListBuilder<Pair<EV3Key, KeyEvent>>()
                .add(Pair.of(new EV3Key(EV3Key.BUTTON_LEFT), KeyEvent.PRESSED))
                .add(Pair.of(new EV3Key(EV3Key.BUTTON_RIGHT), KeyEvent.PRESSED))
                .add(Pair.of(new EV3Key(EV3Key.BUTTON_RIGHT), KeyEvent.RELEASED))
                .add(Pair.of(new EV3Key(EV3Key.BUTTON_LEFT), KeyEvent.RELEASED))
                .build(), leftRightKeyListener.keyEvents);
        Assert.assertEquals(new ImmutableListBuilder<Pair<EV3Key, KeyEvent>>()
                .add(Pair.of(new EV3Key(EV3Key.BUTTON_LEFT), KeyEvent.PRESSED))
                .add(Pair.of(new EV3Key(EV3Key.BUTTON_RIGHT), KeyEvent.PRESSED))
                .add(Pair.of(new EV3Key(EV3Key.BUTTON_RIGHT), KeyEvent.RELEASED))
                .add(Pair.of(new EV3Key(EV3Key.BUTTON_LEFT), KeyEvent.RELEASED))
                .add(Pair.of(new EV3Key(EV3Key.BUTTON_ESCAPE), KeyEvent.PRESSED))
                .build(), allKeyListener.keyEvents);
    }

    private static void assertWaiters(final List<Pair<TestKeyEventWaiter, Boolean>> waiterIsAlivePairs) throws InterruptedException {
        Thread.sleep(100L);
        waiterIsAlivePairs.forEach((pair) -> {
            final TestKeyEventWaiter waiter = pair.getLeft();
            final boolean expIsAlive = pair.getRight();
            if (expIsAlive) {
                Assert.assertTrue(waiter.getName(), waiter.isAlive());
            } else {
                Assert.assertTrue(waiter.getName(), waiter.conditionMet);
            }
        });
    }

}
