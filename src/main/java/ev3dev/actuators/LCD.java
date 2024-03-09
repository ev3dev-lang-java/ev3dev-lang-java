package ev3dev.actuators;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.EV3DevDistros;
import lejos.hardware.lcd.GraphicsLCD;

public abstract class LCD extends EV3DevDevice implements GraphicsLCD {

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
