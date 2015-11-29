package ev3dev.hardware.motor;

import ev3dev.hardware.port.TachoMotorPort;
import ev3dev.hardware.sensor.ev3.IRSensor;

public class MotorTest {

	public static void main(String[] args) {

		EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(TachoMotorPort.A);
        EV3LargeRegulatedMotor mB = new EV3LargeRegulatedMotor(TachoMotorPort.B);
        mA.setSpeed(50);
        mB.setSpeed(50);
        IRSensor ir1 = new IRSensor("in2");

        final int distance_threshold = 35;
        final int iteration_threshold = 100;

        for(int i = 0; i <= iteration_threshold; i++) {
            mA.forward();
            mB.forward();

            if(ir1.getDistance() <= distance_threshold){
                mA.stop();
                mB.stop();
                break;
            }else {
                System.out.println(ir1.getDistance());
            }
        }

        mA.stop();
        mB.stop();		
	}

}
