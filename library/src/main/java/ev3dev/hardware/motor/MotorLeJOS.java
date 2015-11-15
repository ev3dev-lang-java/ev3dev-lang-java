package ev3dev.hardware.motor;

import ev3dev.hardware.LocalDevice;

public class MotorLeJOS{
    /**
     * Motor A.
     */
    public static final NXTRegulatedMotor A = new NXTRegulatedMotor(LocalDevice.getMotorPort("outA"));
    /**
     * Motor B.
     */
    public static final NXTRegulatedMotor B = new NXTRegulatedMotor(LocalDevice.getMotorPort("outB"));
    /**
     * Motor C.
     */
    public static final NXTRegulatedMotor C = new NXTRegulatedMotor(LocalDevice.getMotorPort("outC"));
    
    /**
     * Motor D.
     */
    public static final NXTRegulatedMotor D = new NXTRegulatedMotor(LocalDevice.getMotorPort("outD"));
    
    private MotorLeJOS() {
    	// Motor class cannot be instantiated
    }
    
}
