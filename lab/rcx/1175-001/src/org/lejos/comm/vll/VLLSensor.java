package org.lejos.comm.vll;

import josx.platform.rcx.SensorConstants;
import josx.platform.rcx.Sensor;

/**
 * This class provides the implementation details if the VLL
 * messages are sended using the <i>Light Sensor</i>.
 *
 * @see josx.platform.rcx.Sensor
 *
 * @author <a href="mailto:yago.diaz@wanadoo.es">Yago D&iacute;az</a>
 */
public class VLLSensor extends VLLElement
   implements SensorConstants {

   // Definitions of VLL timing 
   private static final int VLL_ON0_SENSOR  = 4;
   private static final int VLL_OFF0_SENSOR = 1;
   private static final int VLL_ON1_SENSOR  = 2;
   private static final int VLL_OFF1_SENSOR = 3;

   private Sensor sensor = null;

   /**
    * Use this constructor to create a Sensor capable of sending VLL
    * messages.
    */
   public VLLSensor( Sensor sensor ) {
      this.sensor = sensor;
   }

   protected void setVLLOn() {
      sensor.setTypeAndMode( SENSOR_TYPE_LIGHT, SENSOR_MODE_PCT );
   }

   protected void setVLLOff() {
      sensor.setTypeAndMode( SENSOR_TYPE_TOUCH, SENSOR_MODE_PCT );
   }

   protected int getVLLOn0() {
      return VLL_ON0_SENSOR;
   }

   protected int getVLLOff0() {
      return VLL_OFF0_SENSOR;
   }

   protected int getVLLOn1() {
      return VLL_ON1_SENSOR;
   }

   protected int getVLLOff1() {
      return VLL_OFF1_SENSOR;
   }
}
