package ev3dev.hardware;

import java.util.ArrayList;

import ev3dev.hardware.motor.Motor;

public class SysfsTest2 {

	public static void main(String[] args) {
		
		Motor mA = new Motor("outA");//TODO Hide the ev3dev details about outA, outB, outC & outD
		mA.setSpeed(50);
		mA.forward();
		Motor mB = new Motor("outB");
		mB.setSpeed(50);
		mB.forward();
		//Motor mC = new Motor("outC");
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
		mA.stop();
		mB.stop();

		/*
		TODO: Refactor this part
		Motor.A.setSpeed(50);
		Motor.A.forward();
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
		Motor.A.stop();
		*/
	}

}
