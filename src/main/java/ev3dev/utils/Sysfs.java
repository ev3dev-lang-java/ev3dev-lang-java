package ev3dev.utils;

import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class responsible to interact with Sysfs on EV3Dev
 *
 * @author Juan Antonio Breña Moral
 *
 */
public class Sysfs {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Sysfs.class);

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
				//TODO Review if it possible to improve
				PrintWriter out = new PrintWriter(file);
				out.println(value);
				out.flush();
				out.close();
			//TODO Review
			}else {
				log.error("File: '{}' without write permissions.", filePath);
				return false;
			}
		} catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
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
				final List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));
				final String result = lines.size() == 0 ? "" : lines.get(0);
				if(log.isTraceEnabled())
					log.trace("value: {}", result);
				return result;
			}
			throw new IOException("Problem reading path: " + filePath);
		} catch (IOException e) {
			log.error(e.getLocalizedMessage(), e);
			throw new RuntimeException("Problem reading path: " + filePath, e);
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
	 * Write a sysfs attribute, but only do so if the value is different.
	 * This can be useful in avoiding triggering kernel-side changes.
	 * @param filePath Path to the attribute.
	 * @param value Requested attribute value.
	 */
	public static void writeStringIfDifferent(final String filePath, final String value) {
		if (!value.equals(readString(filePath))) {
			writeString(filePath, value);
		}
	}

	/**
	 *
	 * @param filePath path
	 * @return an List with options from a path
	 */
	public static List<File> getElements(final String filePath) {
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
		if(log.isTraceEnabled())
			log.trace("ls " + filePath);
		final File f = new File(filePath);
        return f.exists() && f.isDirectory();
    }

	public static boolean existFile(Path pathToFind) {
		return Files.exists(pathToFind);
	}

	public static boolean writeBytes(final String path, final byte[] value) {
		try {
			Files.write(Paths.get(path), value, StandardOpenOption.WRITE);
		} catch (IOException e) {
			throw new RuntimeException("Unable to draw the LCD", e);
		}
		return true;
	}

}
