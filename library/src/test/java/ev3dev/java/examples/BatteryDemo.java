package ev3dev.java.examples;

import ev3dev.hardware.BatteryFactory;

//java -cp ev3-lang-java-0.2-SNAPSHOT.jar ev3dev.java.examples.BatteryDemo
public class BatteryDemo {

	public static void main(String[] args) {

		System.out.println(BatteryFactory.getBattery("Default").getVoltage());
		System.out.println(BatteryFactory.getBattery("OTHER").getVoltage());
		
	}

}
