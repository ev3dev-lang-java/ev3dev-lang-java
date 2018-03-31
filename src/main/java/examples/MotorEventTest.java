package examples;


import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.NXTRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;
import lejos.utility.Delay;

public class MotorEventTest {

    public static void main(String[] args) {

        System.out.println("Testing events with Motors");

        // Listener to stop motors if they get stalled
        final RegulatedMotorListener listener = new RegulatedMotorListener() {

            @Override
            public void rotationStarted(
                    final RegulatedMotor motor,
                    final int tachoCount,
                    final boolean stalled,
                    final long timeStamp) {

                System.out.println("Started");
            }

            @Override
            public void rotationStopped(
                final RegulatedMotor motor,
                final int tachoCount,
                final boolean stalled,
                final long timeStamp) {

                System.out.println("Stopped");
            }
        };

        final RegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
        mA.addListener(listener);
        mA.forward();
        Delay.msDelay(500);
        mA.stop();

    }


}
