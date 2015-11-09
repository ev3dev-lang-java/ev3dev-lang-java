package ev3dev.hardware;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Sysfs {

	public static boolean write(SysfsObj obj) {
		try {
			File mpuFile = new File(obj.getFile());
			if(mpuFile.canWrite()) {
				BufferedWriter bw = new BufferedWriter(new FileWriter(obj.getFile()));
				bw.write(obj.getValue());
				bw.close();
			} else {
				Process p = Runtime.getRuntime().exec("su");
				
				DataOutputStream dos = new DataOutputStream(p.getOutputStream());
				dos.writeBytes("echo " + obj.getValue() + " > " + obj.getFile() + "\n");
				dos.writeBytes("exit");
				dos.flush();
				dos.close();
				
				if(p.waitFor() != 0)
					System.out.println("Could not write to " + obj.getFile());
			}
		} catch (IOException ex) {
			return false;
		} catch (InterruptedException e) {
			System.out.println("Error writing with root permission");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static String read(SysfsObj obj) {
		String value;
		try {
			BufferedReader br = new BufferedReader(new FileReader(obj.getFile()));
			value = br.readLine();
			br.close();
		} catch (IOException ex) {
			return "-1";
		}
		return value;
	}
	
}
