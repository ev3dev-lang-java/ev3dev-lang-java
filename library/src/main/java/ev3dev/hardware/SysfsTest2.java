package ev3dev.hardware;

import java.util.ArrayList;

import ev3dev.hardware.motor.Motor;

public class SysfsTest2 {

	public static void main(String[] args) {

		Device dev = new Device("tacho-motor", "outA"); 
		System.out.println("Connected" + dev.isConnected());
		dev.setAttribute("duty_cycle_sp", "50");
		dev.setAttribute("command", "run-forever");
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
		dev.setAttribute("command", "stop");
		
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
		
		Motor mA = new Motor("outA");
		mA.setSpeed(50);
		mA.forward();
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
		mA.stop();
		
		
		/*
		TODO: Refactor this part
		Motor.A.setSpeed(50);
		Motor.A.forward();
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
		Motor.A.stop();
		*/
	}

}
