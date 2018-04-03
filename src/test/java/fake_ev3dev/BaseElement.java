package fake_ev3dev;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public abstract class BaseElement {

    private static final String JAVA_IO_TEMPDIR = System.getProperty("java.io.tmpdir");
    public static final String EV3DEV_FAKE_SYSTEM_PATH = JAVA_IO_TEMPDIR + "ev3dev_fake_system";

    /**
     *
     * @throws IOException
     */
    public static  void resetEV3DevInfrastructure() throws IOException {

        LOGGER.info("Reset EV3Dev testing infrastructure");

        //Delete
        FileUtils.deleteDirectory(new File(EV3DEV_FAKE_SYSTEM_PATH));

        //Create
        final Path ev3devFakeSystemPath = Paths.get(EV3DEV_FAKE_SYSTEM_PATH);
        Files.createDirectories(ev3devFakeSystemPath);
        LOGGER.debug("Path created: {}", ev3devFakeSystemPath);
    }

    public static  void createEV3DevFakeSystemPath() throws IOException {

        final Path ev3devFakeSystemPath = Paths.get(EV3DEV_FAKE_SYSTEM_PATH);
        if (!Files.exists(ev3devFakeSystemPath)) {
            Files.createDirectories(ev3devFakeSystemPath);
            LOGGER.trace("Path created: {}", ev3devFakeSystemPath);
        }
    }

    public static void deleteEV3DevFakeSystemPath() throws IOException{

        FileUtils.deleteDirectory(new File(EV3DEV_FAKE_SYSTEM_PATH));
    }

}
