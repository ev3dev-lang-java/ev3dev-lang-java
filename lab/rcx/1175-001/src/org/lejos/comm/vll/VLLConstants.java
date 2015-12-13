package org.lejos.comm.vll;

/**
 * This interface has all the constants needed for
 * the VLL messages using the MicroScout.
 *
 * See also the Scout SDK available at <a href="http://mindstorms.lego.com/scoutsdk/default.asp">http://mindstorms.lego.com/scoutsdk/default.asp</a>
 *
 * @author <a href="mailto:yago.diaz@wanadoo.es">Yago D&iacute;az</a>
 */
public interface VLLConstants {
   // Definitions for VLL command table of MicroScout
   public static final int  MSVLL_D_FWD         = 0;  // 0 D: Motor Forward
   public static final int  MSVLL_D_RWD         = 1;  // 1 D: Motor Reverse
   public static final int  MSVLL_D_BEEP1       = 4;  // 4 D: Beep 1
   public static final int  MSVLL_D_BEEP2       = 5;  // 5 D: Beep 2
   public static final int  MSVLL_D_BEEP3       = 6;  // 6 D: Beep 3
   public static final int  MSVLL_D_BEEP4       = 7;  // 7 D: Beep 4
   public static final int  MSVLL_D_BEEP5       = 8;  // 8 D: Beep 5
   public static final int  MSVLL_D_STOP        = 10; // 10 D: Motor Stop
   public static final int  MSVLL_S_FWD05       = 16; // 16 S: Motor Forward 0.5
   public static final int  MSVLL_S_FWD1        = 17; // 17 S: Motor Forward 1.0
   public static final int  MSVLL_S_FWD2        = 18; // 18 S: Motor Forward 2.0
   public static final int  MSVLL_S_FWD5        = 19; // 19 S: Motor Forward 5.0
   public static final int  MSVLL_S_RWD05       = 20; // 20 S: Motor Reverse 0.5
   public static final int  MSVLL_S_RWD1        = 21; // 21 S: Motor Reverse 1.0
   public static final int  MSVLL_S_RWD2        = 22; // 22 S: Motor Reverse 2.0
   public static final int  MSVLL_S_RWD5        = 23; // 23 S: Motor Reverse 5.0
   public static final int  MSVLL_S_BEEP1       = 24; // 24 S: Beep 1
   public static final int  MSVLL_S_BEEP2       = 25; // 25 S: Beep 2
   public static final int  MSVLL_S_BEEP3       = 26; // 26 S: Beep 3
   public static final int  MSVLL_S_BEEP4       = 27; // 27 S: Beep 4
   public static final int  MSVLL_S_BEEP5       = 28; // 28 S: Beep 5
   public static final int  MSVLL_S_WAIT4_LIGHT = 29; // 29 S: Wait for Light
   public static final int  MSVLL_S_SEEK_LIGHT  = 30; // 30 S: Seek Light
   public static final int  MSVLL_S_CODE        = 31; // 31 S: Code
   public static final int  MSVLL_S_KEEP_ALIVE  = 32; // 32 S: Keep Alive
   public static final int  MSVLL_D_RUN         = 33; // 33 D: Run
   public static final int  MSVLL_D_DEL_SCRIPT  = 34; // 34 D: Delete Script
   public static final int  MSVLL_D_NEXT        = 70; // 70 D: Next
   public static final int  MSVLL_D_RESET       = 71; // 71 D: Reset
}
