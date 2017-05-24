package ev3dev.sensors;

import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jabrena on 24/5/17.
 */
public @Slf4j class ButtonTest {

    public static void main(final String[] args){
        EV3Key button = new EV3Key(EV3Key.BUTTON_LEFT);

        for(int x = 0; x < 10; x++){
            log.info("{} {}",x, button.isPressed());
            Delay.msDelay(500);
        }
    }
}
