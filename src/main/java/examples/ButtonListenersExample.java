package examples;

import ev3dev.sensors.Button;
import ev3dev.sensors.EV3Key;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ButtonListenersExample {

    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern(
            "HH:mm:ss.SSS");

    private static class MyKeyListener implements KeyListener {
        private final Key registeredForKey;

        public MyKeyListener(final Key registeredForKey) {
            this.registeredForKey = registeredForKey;
        }

        @Override
        public void keyPressed(final Key key) {
            System.out.format("%s - [%s listener] %s pressed%n", LocalTime.now().format(TIMESTAMP_FORMAT),
                    this.registeredForKey.getName(), key.getName());
        }
        @Override
        public void keyReleased(final Key key) {
            System.out.format("%s - [%s listener] %s released%n", LocalTime.now().format(TIMESTAMP_FORMAT),
                    this.registeredForKey.getName(), key.getName());
        }
    }

    private static final List<Key> BUTTONS_FOR_LISTENERS = Arrays.asList(new Key[] {
            Button.UP,
            Button.DOWN,
            new EV3Key(EV3Key.BUTTON_ALL)   // a special key that stands for 'any' key
    });

    public static void main(final String[] args){

        // registering the listeners
        System.out.println("Registering key listeners...");
        BUTTONS_FOR_LISTENERS.forEach((key) -> {
            key.addKeyListener(new MyKeyListener(key));
            System.out.format("Listener for %s key registered%n", key.getName());
        });

        // printing some on-screen help
        final Key exitButton = Button.ESCAPE;
        final Key anyButton = new EV3Key(EV3Key.BUTTON_ALL);
        final List<String> registeredKeyNamesExceptAll = BUTTONS_FOR_LISTENERS.stream()
                .filter((button) -> ! button.equals(anyButton))
                .map(Key::getName).collect(Collectors.toList());
        System.out.format("%nKeep pressing any buttons to see listeners getting key press/release events.%n");
        System.out.format("Notice that if you press or release one of the %s buttons, you'll get two events:%n", registeredKeyNamesExceptAll);
        System.out.format("  1) one for the listener registered on that specific key%n");
        System.out.format("  2) another one for the listener registered on the special %s key%n", anyButton.getName());

        // printing the key events (from the listeners) until the ESCAPE key is pressed (i.e. the call above blocks)
        System.out.format("%nPress the %s key to terminate the program...%n", exitButton.getName());
        exitButton.waitForPress();

        // terminating
        System.out.format("%n%s press detected, terminating the program.%n", exitButton.getName());
    }
}
