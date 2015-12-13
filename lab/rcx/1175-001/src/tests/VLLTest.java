package tests;

import josx.platform.rcx.*;
import org.lejos.comm.vll.*;

public class VLLTest
{
  public static void main (String[] aArg)
     throws Exception {

     LCD.clear();
     Sound.twoBeeps();

     try {
	     Thread.sleep( 5000 );
     } catch( InterruptedException ex ) {}

     /*
     VLLSensor sensor = new VLLSensor( Sensor.S1 );
     Sensor.S1.activate();
     sensor.sendMessage( VLLConstants.MSVLL_D_FWD );
     */
     VLLMotor motor = new VLLMotor( Motor.A );
     motor.sendMessage( VLLConstants.MSVLL_D_FWD );

     Sound.systemSound( false, 5 );
     try {
	     Thread.sleep( 5000 );
     } catch( InterruptedException ex ) {}
  }
}
