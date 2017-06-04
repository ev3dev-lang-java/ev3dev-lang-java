package examples;

import ev3dev.utils.BrickmanUtils;
import lejos.utility.Delay;

/**
 * Created by jabrena on 4/6/17.
 */
public class CatacrokerTest {

    public static void main(String[] args){

        BrickmanUtils.disable();

        for(int x = 0; x< 20; x++){
            System.out.println("Delay" + x);
            Delay.msDelay(1000);
        }

        //throw new RuntimeException("Catacroker");
        System.exit(0);

    }
}
