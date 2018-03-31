package examples;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.sensors.Battery;
import ev3dev.sensors.ev3.EV3IRSensor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class IRSensorDemo4 {


	//Configuration
	private static int HALF_SECOND = 100;
	
	public static void main(String[] args) {

		System.out.println("Starting motor on A");
		final EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
		System.out.println("Starting motor on B");
		final EV3LargeRegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.D);

		//To Stop the motor in case of pkill java for example
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				mA.stop();
				mB.stop();
				System.out.println(Battery.getInstance().getVoltage());
			}
		}));

		//mA.brake();
		//mB.brake();

		mA.setSpeed(300);
		mB.setSpeed(300);

		final EV3IRSensor ir1 = new EV3IRSensor(SensorPort.S1);

		final SampleProvider sp = ir1.getRemoteMode();

		int beaconInfo1 = 0;
		int beaconInfo2 = 0;
		int beaconInfo3 = 0;
		int beaconInfo4 = 0;

		//Control loop
		final int iteration_threshold = 5000;
		for(int i = 0; i <= iteration_threshold; i++) {
		
			float [] sample = new float[sp.sampleSize()];
		    sp.fetchSample(sample, 0);

			beaconInfo1 = (int)sample[0];
			beaconInfo2 = (int)sample[1];
			beaconInfo3 = (int)sample[2];
			beaconInfo4 = (int)sample[3];

			System.out.println("Iteration: {}" + i);
			System.out.println("Beacon Channel 1: Remote: " + beaconInfo1);
			System.out.println("Beacon Channel 2: Remote: " + beaconInfo2);
			System.out.println("Beacon Channel 3: Remote: " + beaconInfo3);
			System.out.println("Beacon Channel 4: Remote: " + beaconInfo4);

			if(beaconInfo1 == 0){
				mA.stop();
				mB.stop();
			}else if(beaconInfo1 == 1){
				mA.forward();
			}else if(beaconInfo1 == 2){
				mA.backward();
			}else if(beaconInfo1 == 3){
				mB.forward();
			}else if(beaconInfo1 == 4){
				mB.forward();
			}else if(beaconInfo1 == 5){
				mA.forward();
				mB.forward();
			}else if(beaconInfo1 == 6){
				mA.forward();
				mB.backward();
			}else if(beaconInfo1 == 7){
				mA.backward();
				mB.forward();
			}else if(beaconInfo1 == 8){
				mA.backward();
				mB.backward();
			}

		    Delay.msDelay(HALF_SECOND);
		}
		
	}

}
