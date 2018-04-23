package ev3dev.utils;

import org.slf4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JarResource {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Shell.class);

    /**
     * Export a resource embedded into a Jar file to the local file path.
     *
     * @param resourceName "Sound.wav"
     * @return The path to the exported resource
     * @throws Exception
     */
    public static String export(final String resourceName) throws IOException {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        try {
            stream = JarResource.class.getResourceAsStream("/" + resourceName);
            if(stream == null) {
                log.error("Cannot get resource \"" + resourceName + "\" from Jar file.");
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(JarResource.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(jarFolder + "/" + resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw new IOException(ex);
        } finally {
            stream.close();
            resStreamOut.close();
        }

        return jarFolder + "/" + resourceName;
    }

    public static void delete(final String resourceName) {
        try {
            Files.delete(Paths.get(resourceName));
            log.info("File deleted!");
        } catch (IOException e) {
            log.warn("Delete operation is failed. {}", e.getLocalizedMessage());
        }
    }

}

