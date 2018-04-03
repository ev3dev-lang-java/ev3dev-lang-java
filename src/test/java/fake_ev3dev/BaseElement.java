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

    protected static final String LEGO_SENSOR_PATH = "lego-sensor";
    protected static final String LEGO_PORT_PATH = "lego-port";
    protected static final String SENSOR1 = "sensor1";
    protected static final String SENSOR_ADDRESS = "address";

    protected static final String PORT1 = "port1";
    protected static final String PORT1_MODE = "mode";
    protected static final String PORT_ADDRESS = "address";

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

    @Deprecated
    public static  void createEV3DevFakeSystemPath() throws IOException {

        final Path ev3devFakeSystemPath = Paths.get(EV3DEV_FAKE_SYSTEM_PATH);
        if (!Files.exists(ev3devFakeSystemPath)) {
            Files.createDirectories(ev3devFakeSystemPath);
            LOGGER.trace("Path created: {}", ev3devFakeSystemPath);
        }
    }

    @Deprecated
    public static void deleteEV3DevFakeSystemPath() throws IOException{

        FileUtils.deleteDirectory(new File(EV3DEV_FAKE_SYSTEM_PATH));
    }

    protected void createDirectories(final Path path) throws IOException {
        Files.createDirectories(path);
        System.out.println(Files.exists(path));
    }

    protected void createFile(final Path path) throws IOException {
        Files.createFile(path);
    }

    protected void createFile(final Path path, final String value) throws IOException {
        Files.createFile(path);
        Files.write(path, value.getBytes());
    }

}
