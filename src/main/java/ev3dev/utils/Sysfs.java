package ev3dev.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

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
		if(log.isTraceEnabled())
			log.trace("echo " + value + " > " + filePath);
		try {
			final File file = new File(filePath);
			if(file.canWrite()) {
				/*
				final FileOutputStream fileOutputStream = new FileOutputStream(file, true);
				final FileChannel fileChannel = fileOutputStream.getChannel();
				final ByteBuffer byteBuffer = ByteBuffer.wrap(value.getBytes(Charset.forName("UTF-8")));
				fileChannel.write(byteBuffer);
				fileChannel.close();
				*/
				PrintWriter out = new PrintWriter(file);
				out.println(value);
				out.flush();
				out.close();
			//TODO Review
			}else {
				log.error("File: '{}' without write permissions.", filePath);
				return false;
			}
		} catch (IOException ex) {
            log.error(ex.getLocalizedMessage());
			return false;
		}
		return true;
	}
	
	public static boolean writeInteger(final String filePath, final int value) {
		return writeString(filePath, new StringBuilder().append(value).toString());
	}
	
	/**
	 * Read an Attribute in the Sysfs with containing String values
	 * @param filePath path
	 * @return value from attribute
	 */
	public static String readString(final String filePath) {
		if(log.isTraceEnabled())
			log.trace("cat " + filePath);
		try {
			final Path path = Paths.get(filePath);
			if(existFile(path) && Files.isReadable(path)){
				return Files.readAllLines(path, Charset.forName("UTF-8")).get(0);
			}
			throw new IOException("Problem reading path: " + filePath);
		} catch (IOException ex) {
			log.error(ex.getLocalizedMessage());
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Read an Attribute in the Sysfs with containing Integer values
	 * @param filePath path
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
	 * @param filePath path
	 * @return an List with options from a path
	 */
	public static List<File> getElements(final String filePath){
		if(log.isTraceEnabled())
			log.trace("ls " + filePath);
		final File f = new File(filePath);
		if(existPath(filePath) && (f.listFiles().length > 0)) {
            return new ArrayList<>(Arrays.asList(f.listFiles()));
		}else {
			throw new RuntimeException("The path doesn't exist: " + filePath);
		}
	}

	/**
	 * This method is used to detect folders in /sys/class/
	 *
	 * @param filePath path
	 * @return boolean
	 */
	public static boolean existPath(final String filePath){
		final File f = new File(filePath);
        return f.exists() && f.isDirectory();
    }


	public static boolean existFile(Path pathToFind) {
		return Files.exists(pathToFind);
	}

	public static boolean writeBytes(final String path, final byte[] value) {
        final File file = new File(path);
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			out.write(value);
			out.flush();
			out.close();
		} catch (IOException e) {
			throw new RuntimeException("Unable to draw the LCD", e);
		}
		return true;
	}

}
