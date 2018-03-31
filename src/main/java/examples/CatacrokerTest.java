package examples;

import ev3dev.utils.Brickman;
import lejos.utility.Delay;

public class CatacrokerTest {

    public static void main(String[] args){

        Brickman.disable();

        for(int x = 0; x< 10; x++){
            System.out.println("Delay " + x);
            Delay.msDelay(1000);
        }

        throw new RuntimeException("Catacroker");
    }
}
