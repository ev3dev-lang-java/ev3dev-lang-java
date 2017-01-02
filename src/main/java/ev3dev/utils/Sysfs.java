package ev3dev.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class responsible to interact with Sysfs on EV3Dev
 * 
 * @author Juan Antonio Breña Moral
 *
 */
public @Slf4j class Sysfs {

	/**
	 * Write a value in a file.
	 * 
	 * @param filePath File path
	 * @param value value to write
	 * @return A boolean value if the operation was written or not.
	 */
	public static boolean writeString(final String filePath, final String value) {
		log.debug("echo " + value + " > " + filePath);
		try {
			File mpuFile = new File(filePath);
			if(mpuFile.canWrite()) {
				BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
				bw.write(value);
				bw.close();
			}
		} catch (IOException ex) {
            log.error(ex.getLocalizedMessage());
			return false;
		}
		return true;
	}
	
	public static boolean writeInteger(final String filePath, final int value) {
		return writeString(filePath, "" + value);
	}
	
	/**
	 * Read an Attribute in the Sysfs with containing String values
	 * @param filePath
	 * @return value from attribute
	 */
	public static String readString(final String filePath) {
		log.debug("cat " + filePath);
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
	
	/**
	 * Read an Attribute in the Sysfs with containing Integer values
	 * @param filePath
	 * @return value from attribute
	 */
	public static int readInteger(final String filePath) {
		return Integer.parseInt(readString(filePath));
	}
	
	public static float readFloat(final String filePath) {
		return Float.parseFloat(readString(filePath));
	}
	
	/**
	 * 
	 * @param filePath
	 * @return an List with options from a path
	 */
	public static List<File> getElements(final String filePath){
		log.debug("ls " + filePath);
		final File f = new File(filePath);
		if(f.exists() && f.isDirectory()) {
			List<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
			return files;
		}else {
			throw new RuntimeException("The path doesn't exist: " + filePath);
		}
	}

	/**
	 * This method is used to detect folders in /sys/class/
	 *
	 * @param filePath
	 * @return
	 */
	public static boolean existPath(final String filePath){
		final File f = new File(filePath);
		if (f.exists() && f.isDirectory()) {
			return true;
		}
		return false;
	}

}
