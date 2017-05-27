package ev3dev.sensors;

import lombok.extern.slf4j.Slf4j;

public @Slf4j class ButtonTest {

    public static void main(final String[] args){
        log.info("Demo");

        Button.waitForAnyPress();
    }
}
