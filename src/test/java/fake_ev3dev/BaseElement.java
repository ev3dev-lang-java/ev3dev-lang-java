package fake_ev3dev;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
public abstract class BaseElement {

    private static final String JAVA_IO_TEMPDIR = System.getProperty("java.io.tmpdir");
    public static final String EV3DEV_FAKE_SYSTEM_PATH = Paths.get(JAVA_IO_TEMPDIR, "ev3dev_fake_system").toString();

    protected static final String LEGO_PORT_PATH = "lego-port";
    protected static final String PORT = "port";
    protected static final String PORT1 = "port1";
    protected static final String PORT2 = "port2";
    protected static final String PORT3 = "port3";
    protected static final String PORT4 = "port4";

    protected static final String PORT_MODE = "mode";
    protected static final String PORT_ADDRESS = "address";

    protected static final String LEGO_SENSOR_PATH = "lego-sensor";
    protected static final String SENSOR1 = "sensor1";
    protected static final String SENSOR2 = "sensor2";
    protected static final String SENSOR3 = "sensor3";
    protected static final String SENSOR4 = "sensor4";
    protected static final String SENSOR_ADDRESS = "address";
    protected static final String SENSOR_MODE = "mode";
    protected static final String SENSOR_COMMAND = "command";

    protected static final String SENSOR1_BASE = Paths.get(EV3DEV_FAKE_SYSTEM_PATH, LEGO_SENSOR_PATH, SENSOR1).toString();
    protected static final String PORT1_BASE = Paths.get(EV3DEV_FAKE_SYSTEM_PATH, LEGO_SENSOR_PATH, PORT1).toString();

    /**
     *
     * @throws IOException
     */
    public static  void resetEV3DevInfrastructure() throws IOException {

        LOGGER.info("Switching to EV3Dev testing infrastructure");

        //Delete
        LOGGER.trace("Removing previous fileSystem infrastructure");
        FileUtils.deleteDirectory(new File(EV3DEV_FAKE_SYSTEM_PATH));

        //Create
        LOGGER.debug("Created Fake file system to implement devices for testing");
        final Path ev3devFakeSystemPath = Paths.get(EV3DEV_FAKE_SYSTEM_PATH);
        Files.createDirectories(ev3devFakeSystemPath);
        LOGGER.trace("Path created: {}", ev3devFakeSystemPath);
        LOGGER.info("");
    }

    protected void createDirectories(final Path path) throws IOException {
        LOGGER.trace("Creating path: {}", path);
        Files.createDirectories(path);
        then(Files.exists(path)).isTrue();
    }

    protected void createDirectoriesDirect(final String first, final String... more) throws IOException {
        createDirectories(Paths.get(first, more));
    }

    protected void createFile(final Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    protected void writeFileDirect(final String contents,
                                   final String first,
                                   final String... more) throws IOException {
        createFile(Paths.get(first, more), contents);
    }

    protected void createFile(final Path path, final String value) throws IOException {
        this.createFile(path);
        Files.write(path, value.getBytes());
    }

    protected void populateValues(final List<Integer> values) throws IOException {
        for (int i = 0; i < values.size(); i++) {
            setValue(i, String.valueOf(values.get(i)));
        }
    }

    public void setValue(final int index, final String value) throws IOException {
        Path path = Paths.get(SENSOR1_BASE, "value" + index);
        createFile(path, value);
    }

}
