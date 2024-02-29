package ev3dev.actuators;

import ev3dev.hardware.EV3DevDistros;
import lejos.hardware.lcd.GraphicsLCD;

public class LCD {

    /**
     * Factory
     *
     * @return GraphicsLCD
     */
    public static GraphicsLCD getInstance() {
        switch (EV3DevDistros.getInstance().getDistro()) {
            case BUSTER:
                return LCDBuster.getInstance();
            case STRETCH:
                return LCDStretch.getInstance();
            default:
                return LCDJessie.getInstance();
        }
    }

}
