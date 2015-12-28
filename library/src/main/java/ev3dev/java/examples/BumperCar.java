package ev3dev.java.examples;

import ev3dev.hardware.Battery;
import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.motor.EV3LargeRegulatedMotor;
import ev3dev.hardware.sensor.ev3.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

//java -cp ev3-lang-java-0.2-SNAPSHOT.jar ev3dev.java.examples.BumperCar
public class BumperCar {
	
	//Robot Definition
    private final static EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
    private final static EV3LargeRegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
    private final static EV3IRSensor ir1 = new EV3IRSensor(SensorPort.S2);

    //Configuration
    private final static int motorSpeed = 500;
    private final static int incrementSpeed = 50;
    
    public static void main(String[] args) {
    	
        final SampleProvider sp = ir1.getDistanceMode();
        int distance = 255;

        final int distance_threshold = 35;
        
        //Robot control loop
        final int iteration_threshold = 100;
        for(int i = 0; i <= iteration_threshold; i++) {
        	forward();

            float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            distance = (int)sample[0];
            if(distance <= distance_threshold){
            	backwardWithTurn();
            }

        	System.out.println("Iteration: " + i);
            System.out.println("Battery: " + Battery.getVoltage());
            System.out.println("Distance: " + distance);
            System.out.println();
        }

        mA.stop();
        mB.stop();
        System.exit(0);
    }
    
    private static void forward(){
        mA.setSpeed(motorSpeed);
        mB.setSpeed(motorSpeed);
        mA.forward();
        mB.forward();
    }
    
    private static void backwardWithTurn(){
        mA.backward();
        mB.backward();
        Delay.msDelay(1000);
        mA.stop();
        mB.stop();
        mA.setSpeed(motorSpeed + incrementSpeed);
        mB.setSpeed(motorSpeed + incrementSpeed);
        mA.backward();
        mB.forward();
        Delay.msDelay(1000);
        mA.stop();
        mB.stop();
    }
}
