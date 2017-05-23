package ev3dev.actuators;

import lejos.utility.Delay;

public class LEDExample {

    /**
     * @param args
     */
    public static void main(String[] args) {
        //GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
        //final int SW = g.getWidth();
        //final int SH = g.getHeight();
        //Button.LEDPattern(4);
        //Sound.beepSequenceUp();
        //g.setFont(Font.getDefaultFont());
        //g.drawString("Lejos EV3 Gradle", SW/2, SH/2, GraphicsLCD.BASELINE|GraphicsLCD.HCENTER);
        //Button.LEDPattern(3);
        Delay.msDelay(4000);
        //Button.LEDPattern(5);
        //g.clear();
        //g.refresh();
        //Sound.beepSequence();
        Delay.msDelay(500);
        //Button.LEDPattern(0);
    }

}