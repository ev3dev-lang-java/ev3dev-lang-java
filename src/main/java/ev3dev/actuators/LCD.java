package ev3dev.actuators;

import ev3dev.hardware.EV3DevDistro;
import ev3dev.hardware.EV3DevDistros;
import lejos.hardware.lcd.GraphicsLCD;

public class LCD {

    /**
     * Factory
     *
     * @return GraphicsLCD
     */
    public static GraphicsLCD getInstance() {

        if (EV3DevDistros.getInstance().getDistro().equals(EV3DevDistro.STRETCH)) {
            return LCDStretch.getInstance();
        } else {
            return LCDJessie.getInstance();
        }

    }

}
