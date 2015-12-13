package org.lejos.comm.vll;

import josx.platform.rcx.Motor;

/**
 * This class provides the implementation details if the VLL
 * messages are sended using the <i>LEGO Lamp</i> attached to a output.
 *
 * @see josx.platform.rcx.Motor
 *
 * @author <a href="mailto:yago.diaz@wanadoo.es">Yago D&iacute;az</a>
 */
public class VLLMotor extends VLLElement {
   // Definitions of VLL timing 
   private static final int VLL_ON0_OUT  = 5;
   private static final int VLL_OFF0_OUT = 1;
   private static final int VLL_ON1_OUT  = 2;
   private static final int VLL_OFF1_OUT = 4;

   private Motor motor = null;

   /**
    * Use this constructor to create a Sensor capable of sending VLL
    * messages.
    */
   public VLLMotor( Motor motor ) {
      this.motor = motor;
   }

   protected void setVLLOn() {
      motor.forward();
   }

   protected void setVLLOff() {
      motor.stop();
   }

   public int getVLLOn0() {
      return VLL_ON0_OUT;
   }

   public int getVLLOff0() {
      return VLL_OFF0_OUT;
   }

   public int getVLLOn1() {
      return VLL_ON1_OUT;
   }

   public int getVLLOff1() {
      return VLL_OFF1_OUT;
   }
}
