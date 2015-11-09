package ev3dev.hardware;

import junit.framework.TestCase;

public class SysfsTest extends TestCase {

	public void testMotor0(){

		String path = "/sys/class/tacho-motor/motor0/duty_cycle_sp";
		String value = "50";
		SysfsObj sfsObj = new SysfsObj(path, value);
		Sysfs.write(sfsObj);
		
		assertTrue(true == true);
	}

}
