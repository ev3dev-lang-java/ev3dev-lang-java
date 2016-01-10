package ev3dev.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Sysfs {

	public static boolean writeString(final String filePath, final String value) {
		//System.out.println("echo " + value + " > " + filePath);
		try {
			File mpuFile = new File(filePath);
			if(mpuFile.canWrite()) {
				BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
				bw.write(value);
				bw.close();
			}
		} catch (IOException ex) {
			return false;
		}
		return true;
	}
	
	public static String readString(final String filePath) {
		String value;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			value = br.readLine();
			br.close();
		} catch (IOException ex) {
			return "-1";
		}
		return value;
	}

	
	public static int readInteger(String filePath) {
		String rawValue;
		int value = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			rawValue = br.readLine();
			br.close();
            if (rawValue != null && rawValue.length() > 0) {
                value = Integer.parseInt(rawValue);
            }
		} catch (IOException ex) {
			value = -1;
		} catch (NumberFormatException e) {
			value = -1;			
		}
		
		return value;
	}
	
	public static ArrayList<File> getElements(String path){
		File f = new File(path);
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		return files;	
	}

	
}
