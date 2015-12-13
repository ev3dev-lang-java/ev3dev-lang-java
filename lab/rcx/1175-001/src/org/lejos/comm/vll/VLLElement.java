package org.lejos.comm.vll;

/**
 * This class provides the implementation of the VLL message sending
 * mechanism. It provides the interface for sending messages to the 
 * MicroScout device.
 *
 * @see org.lejos.comm.vll.VLLConstants
 * @author <a href="mailto:yago.diaz@wanadoo.es">Yago D&iacute;az</a>
 */
public abstract class VLLElement
   implements VLLConstants {

   private final static int[] VALUES = { 0, 1, 4, 5, 6, 7, 8, 10, 16, 17, 18, 19, 20, 21, 22, 23,
      24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 70, 71 };

   private int vllBuffer;

   protected abstract void setVLLOn();
   protected abstract void setVLLOff();
   protected abstract int getVLLOn0();
   protected abstract int getVLLOff0();
   protected abstract int getVLLOn1();
   protected abstract int getVLLOff1();


   /**
    * This method allows you to send VLL messages to the MicroScout.
    * <p>
    * The messages that are available are the ones defined in VLLConstants
    * <p>
    * <code>
    VLLSensor sensor = new VLLSensor( Sensor.S1 );
    sensor.sendMessage( VLLConstants.MSVLL_D_DEL_SCRIPT );
    sensor.sendMessage( VLLConstants.MSVLL_S_FWD1 );
    sensor.sendMessage( VLLConstants.MSVLL_S_RWD2 );
    sensor.sendMessage( VLLConstants.MSVLL_D_RUN );
    </code>
    */
   public boolean sendMessage( int message ) {
      boolean found = false;

      for( int i = 0; i < VALUES.length && !found; i++ ) {
         found = (VALUES[i] == message );
      }

      if( found ) {
         setVLLMessage( message );
         sendVLLMessage();
      }

      return found;
   }

   private void sendVLLMessage() {
      int vllMask, vllTemp;

      vllTemp = this.vllBuffer / 4;
      vllMask = this.vllBuffer / 16 + vllTemp + this.vllBuffer;
      vllTemp = (((7 - vllMask) & 7) * 128 + this.vllBuffer) * 2 + 1;

      this.vllBuffer = vllTemp;

      vllMask = 2048;

      // Send start
      setVLLOn();
      try {
         Thread.currentThread().sleep( 16*10 );
      } catch( InterruptedException ex ) { }
      // send data
      while( vllMask != 0 ) {
         vllTemp = this.vllBuffer & vllMask;
         if( vllTemp == 0 ) {
            setVLLOn();
            try {
               Thread.currentThread().sleep( 10*getVLLOn0() );
            } catch( InterruptedException ex ) { }
            setVLLOff();
            try {
               Thread.currentThread().sleep( 10*getVLLOff0() );
            } catch( InterruptedException ex ) { }
         } else {
            setVLLOn();
            try {
               Thread.currentThread().sleep( 10*getVLLOn1()  );
            } catch( InterruptedException ex ) { }
            setVLLOff();
            try {
               Thread.currentThread().sleep( 10*getVLLOff1() );
            } catch( InterruptedException ex ) { }
         }
   
         vllMask /= 2;
      }

      // send stop
      try {
         Thread.currentThread().sleep( 10*4 );
      } catch( InterruptedException ex ) { }
      setVLLOn();
      try {
         Thread.currentThread().sleep( 10*30 );
      } catch( InterruptedException ex ) { }
      setVLLOff();
   }

   private void setVLLMessage( int x ) {
      this.vllBuffer = x;
   }
}
