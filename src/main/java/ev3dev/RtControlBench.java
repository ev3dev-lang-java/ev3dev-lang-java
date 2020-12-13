package ev3dev;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.sensors.ev3.EV3GyroSensor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;

import java.io.FileWriter;
import java.io.IOException;

public class RtControlBench {
    private static final int SAMPLES = 2000;
    private static final float TARGET = 45.0f;
    private static final float KP = -2.0f;

    public static void main(String[] args) {
        EV3GyroSensor gyro = new EV3GyroSensor(SensorPort.S2);
        SampleProvider prov = gyro.getRateMode();
        float[] samples = new float[prov.sampleSize()];
        EV3LargeRegulatedMotor motorL = new EV3LargeRegulatedMotor(MotorPort.B);
        EV3LargeRegulatedMotor motorR = new EV3LargeRegulatedMotor(MotorPort.C);

        long[] timingSamples = new long[SAMPLES];

        for (int i = 0; i < SAMPLES; i++) {
            timingSamples[i] = System.nanoTime();

            prov.fetchSample(samples, 0);
            float errorSignal = TARGET - samples[0];
            float fixSignal = KP * errorSignal;
            int ltacho = motorL.getTachoCount();
            int rtacho = motorR.getTachoCount();
            fixSignal = fixSignal + (rtacho + ltacho) / 1000.0f;
            motorL.setSpeed((int) (-fixSignal));
            motorR.setSpeed((int) (+fixSignal));
            motorL.forward();
            motorR.forward();
        }

        motorL.stop();
        motorR.stop();

        try (FileWriter csv = new FileWriter("/home/robot/rtcontrolbench.csv", false)) {
            for (int i = 0; i < SAMPLES; i++) {
                csv.write(Integer.toString(i));
                csv.write(',');
                csv.write(Long.toString(timingSamples[i]));
                csv.write('\n');
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
