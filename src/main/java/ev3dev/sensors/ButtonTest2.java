package ev3dev.sensors;

import lombok.extern.slf4j.Slf4j;

public @Slf4j class ButtonTest2 {

    public static void main(final String[] args){
        log.info("Demo");

        Button.UP.waitForPress();
        log.info("Pressed UP");

        Button.DOWN.waitForPress();
        log.info("Pressed DOWN");

        Button.LEFT.waitForPress();
        log.info("Pressed LEFT");

        Button.RIGHT.waitForPress();
        log.info("Pressed RIGHT");

        Button.ESCAPE.waitForPress();
        log.info("Pressed ESCAPE");

        Button.ENTER.waitForPress();
        log.info("Pressed ENTER");

        Button.waitForAnyPress();
    }
}
