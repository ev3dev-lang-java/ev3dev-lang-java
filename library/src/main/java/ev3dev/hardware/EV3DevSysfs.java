package ev3dev.hardware;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The class responsible to interact with Sysfs on EV3Dev
 * 
 * @author jabrena
 *
 */
public class EV3DevSysfs extends Device {

	/**
	 * Write a value in a file.
	 * 
	 * @param filePath File path
	 * @param value value to write
	 * @return
	 */
	public boolean writeString(final String filePath, final String value) {
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
	
	/**
	 * 
	 * @param filePath
	 * @param value
	 * @return

	public static boolean staticWriteString(final String filePath, final String value) {
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
		 */

	
	public String readString(final String filePath) {
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

	/*
	public String staticReadString(final String filePath) {
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
	*/
	
	public int readInteger(final String filePath) {
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

	/*
	protected static int staticReadInteger(final String filePath) {
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
	*/
	
	public ArrayList<File> getElements(final String path){
		File f = new File(path);
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		return files;	
	}

	
}
