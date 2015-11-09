package ev3dev.hardware.motor;

import java.io.File;

import ev3dev.hardware.Sysfs;

public class NXTRegulatedMotor {

	File tachoMotor = null;
	
	public NXTRegulatedMotor(File tachoMotor){
		System.out.println(tachoMotor.toString());
		
		this.tachoMotor = tachoMotor;
	}
	
	public void setSpeed(int speed){
		String path = this.tachoMotor + "/duty_cycle_sp";
		System.out.println(path);
		Sysfs.writeString(path,"" + speed);
	}
	
	public void forward(){
		String path = this.tachoMotor + "/command";
		Sysfs.writeString(path,"run-forever");
	}
	
	public void stop(){
		String path = this.tachoMotor + "/command";
		Sysfs.writeString(path,"stop");
	}
	
}
