package ev3dev.sensors;

import lombok.extern.slf4j.Slf4j;

public @Slf4j class ButtonTest2 {

    public static void main(final String[] args){

        Button.LEFT.waitForPress();
        log.info("Pressed LEFT");

        Button.waitForAnyPress();
        log.info("Any button was pressed");
        //Button.waitForAnyEvent();

        Button.ENTER.waitForPress();
        log.info("Pressed LEFT");
    }
}
