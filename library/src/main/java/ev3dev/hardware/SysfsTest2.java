package ev3dev.hardware;

import java.util.ArrayList;

public class SysfsTest2 {

	public static void main(String[] args) {

		String path = "/sys/class/tacho-motor/motor0/duty_cycle_sp";
		String value = "50";
		Sysfs.writeString(path,value);
		path = "/sys/class/tacho-motor/motor0/command";
		value = "run-forever";
		Sysfs.writeString(path,value);
		
		try {Thread.sleep(5000);} catch (InterruptedException e) {}
		
		value = "stop";
		Sysfs.writeString(path,value);
		
		ArrayList files = Sysfs.getElements("/sys/class/tacho-motor/");
		for(int x=0;x<files.size();x++) {
			System.out.println(files.get(x));
		}
	}

}
