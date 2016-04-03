package ev3dev.examples.hardware;

import ev3dev.hardware.Battery;

//java -cp ev3-lang-java-0.2-SNAPSHOT.jar ev3dev.examples.hardware.BatteryDemo
public class BatteryDemo {

	public static void main(String[] args) {

		System.out.println(Battery.getInstance().getVoltage());
		
	}

}
