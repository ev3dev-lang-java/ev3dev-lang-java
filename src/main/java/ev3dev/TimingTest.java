package ev3dev;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.sensors.ev3.EV3GyroSensor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;

public class TimingTest {

    public float program() {
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
        System.out.println("motors created");

        EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S2);
        SampleProvider gyroSampleProvider = gyroSensor.getAngleMode();
        float[] gyroArray = new float[gyroSensor.sampleSize()];
        System.out.println("gyro created");

        warmUp(gyroSampleProvider,leftMotor,rightMotor);

        long[] timingResults = new long[10];
        for(int i = 0 ; i < timingResults.length ; i++) {
            long startTime = System.currentTimeMillis();

            //readHeading
            gyroSampleProvider.fetchSample(gyroArray, 0);
            float heading = gyroArray[0];
            //read each motor's odometer
            int leftOdometer = leftMotor.getTachoCount();
            int rightOdometer = rightMotor.getTachoCount();
            //set each motor's speed
            leftMotor.setSpeed(0);
            rightMotor.setSpeed(0);

            long endTime = System.currentTimeMillis();
            timingResults[i] = endTime - startTime;
            //todo set the array value

        }
        long total = 0 ;
        for(long r : timingResults ) {
            System.out.println(r);
            total = total + r;
        }
        float average = total/timingResults.length;
        System.out.println("average is " + average);
        return average;
    }

    private void warmUp(SampleProvider gyroSampleProvider,EV3LargeRegulatedMotor leftMotor,EV3LargeRegulatedMotor rightMotor) {
        long startTime = System.currentTimeMillis();

        //readHeading
        float[] gyroArray = new float[1];
        gyroSampleProvider.fetchSample(gyroArray, 0);
        //read each motor's odometer
        int leftOdometer = leftMotor.getTachoCount();
        int rightOdometer = rightMotor.getTachoCount();
        //set each motor's speed
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);

        long endTime = System.currentTimeMillis();
    }

    //equivalent loop on just ev3 classroom is .3.6 / 100 = 0.0036 per iteration = 3.6 milliseconds - so it's a factor of about 100x
    public static void main(String[] args) {
        TimingTest tm = new TimingTest();
        tm.program();
    }
}
