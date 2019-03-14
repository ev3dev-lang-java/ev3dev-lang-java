package ev3dev.utils;

import org.slf4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
	 */
	public static void writeString(final String filePath, final String value) {
		log.trace("echo {} > {}", value, filePath);

		final ByteBuffer data = StandardCharsets.UTF_8.encode(value);
		try (final FileOutputStream fd = new FileOutputStream(filePath, false)) {
			fd.write(data.array());

		} catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            String msg = String.format("Sysfs write failed: '%s' with value '%s'", filePath, value);
			throw new RuntimeException(msg, e);
		}
	}

	public static void writeInteger(final String filePath, final int value) {
		writeString(filePath, Integer.toString(value));
	}

	/**
	 * Read an Attribute in the Sysfs with containing String values
	 * @param filePath path
	 * @return value from attribute
	 */
	public static String readString(final String filePath) {
		log.trace("cat {}", filePath);
		try {
			final String result = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8).get(0);
			log.trace("value: {}", result);
			return result;
		} catch (IOException e) {
			log.error(e.getLocalizedMessage(), e);
			String msg = String.format("Sysfs read failed: '%s'", filePath);
			throw new RuntimeException(msg, e);
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
	public static List<File> getElements(final String filePath) {
		final File f = new File(filePath);
		if (!existPath(filePath)) {
			throw new RuntimeException("Not a directory: " + filePath);
		}
		final File[] list = f.listFiles();
		if (list == null){
			throw new RuntimeException("listFiles() returned null: " + filePath);
		}
		return new ArrayList<>(Arrays.asList(list));
	}

	/**
	 * This method is used to detect folders in /sys/class/
	 *
	 * @param filePath path
	 * @return boolean
	 */
	public static boolean existPath(final String filePath){
		log.trace("ls {}", filePath);
		final File f = new File(filePath);
        return f.exists() && f.isDirectory();
    }

	public static boolean existFile(Path pathToFind) {
		return Files.exists(pathToFind);
	}

	public static void writeBytes(final String path, final byte[] value) {
		try {
			Files.write(Paths.get(path), value, StandardOpenOption.WRITE);
		} catch (IOException e) {
			throw new RuntimeException("Unable to draw the LCD", e);
		}
	}
}
