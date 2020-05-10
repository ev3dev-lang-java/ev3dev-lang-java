package ev3dev.utils;

import org.slf4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JarResource {
    public static final String JAVA_DUKE_IMAGE_NAME = "java_logo.png";
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Shell.class);

    /**
     * Obtain a stream to a resource embedded into this Jar.
     *
     * @param resourceName "Sound.wav"
     * @return Stream containing the resource.
     */
    public static InputStream stream(final String resourceName) {
        InputStream stream = JarResource.class.getResourceAsStream("/" + resourceName);
        if (stream == null) {
            log.error("Cannot get resource \"" + resourceName + "\" from Jar file.");
            throw new IllegalArgumentException("Cannot get resource \"" + resourceName + "\" from Jar file.");
        }
        return stream;
    }

    /**
     * Obtain an image stored as a resource embedded into this Jar.
     *
     * @param resourceName "logo.png"
     * @return Loaded image.
     */
    public static BufferedImage loadImage(final String resourceName) throws IOException {
        try (InputStream stream = stream(resourceName)) {
            return ImageIO.read(stream);
        }
    }

    /**
     * Read a resource embedded into this Jar.
     *
     * @param resourceName "Sound.wav"
     * @return Byte array containing the resource.
     */
    public static byte[] read(final String resourceName) {
        int readBytes;
        byte[] buffer = new byte[4096];
        try (InputStream is = stream(resourceName);
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            while ((readBytes = is.read(buffer)) > 0) {
                os.write(buffer, 0, readBytes);
            }
            return os.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot load resource", e);
        }
    }

    /**
     * Export a resource embedded into a Jar file to the local file path.
     *
     * @param resourceName "Sound.wav"
     * @return The path to the exported resource
     * @throws Exception When URI parsing fails or IO exception happens
     */
    public static String export(final String resourceName) throws IOException {
        String jarFolder;
        try {
            jarFolder = new File(JarResource.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI().getPath())
                    .getParentFile().getPath().replace('\\', '/');
        } catch (Exception e) {
            throw new RuntimeException("Cannot parse JAR folder", e);
        }
        String filename = jarFolder + "/" + resourceName;
        int readBytes;
        byte[] buffer = new byte[4096];
        try (InputStream is = stream(resourceName);
             OutputStream os = new FileOutputStream(filename)) {
            while ((readBytes = is.read(buffer)) > 0) {
                os.write(buffer, 0, readBytes);
            }
        }

        return filename;
    }

    /**
     * Delete a Jar file
     *
     * @param resourceName Name of Jar
     */
    public static void delete(final String resourceName) {
        try {
            Files.delete(Paths.get(resourceName));
            log.info("File deleted!");
        } catch (IOException e) {
            log.warn("Delete operation is failed. {}", e.getLocalizedMessage());
        }
    }

}

