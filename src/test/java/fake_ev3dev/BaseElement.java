package fake_ev3dev;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public abstract class BaseElement {

    private static final String JAVA_IO_TEMPDIR = System.getProperty("java.io.tmpdir");
    public static final String EV3DEV_FAKE_SYSTEM_PATH = JAVA_IO_TEMPDIR + "/" + "ev3dev_fake_system";

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
    protected static final String VALUE0 = "value0";
    protected static final String VALUE1 = "value1";
    protected static final String VALUE2 = "value2";
    protected static final String VALUE3 = "value3";
    protected static final String VALUE4 = "value4";
    protected static final String VALUE5 = "value5";
    protected static final String VALUE6 = "value6";
    protected static final String VALUE7 = "value7";

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

    protected void createDirectories(final Path path) throws IOException {
        Files.createDirectories(path);
        System.out.println(Files.exists(path));
    }

    protected void createFile(final Path path) throws IOException {
        if(!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    protected void createFile(final Path path, final String value) throws IOException {
        this.createFile(path);
        Files.write(path, value.getBytes());
    }

    protected void populateValues(final List<Integer> values) throws IOException {

        if(values.size() >= 1) {
            Path value0 = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LEGO_SENSOR_PATH + "/" +
                            SENSOR1 + "/" +
                            VALUE0);
            createFile(value0, String.valueOf(values.get(0)));
        }

        if(values.size() >= 2) {
            Path value1 = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LEGO_SENSOR_PATH + "/" +
                            SENSOR1 + "/" +
                            VALUE1);
            createFile(value1, String.valueOf(values.get(1)));
        }

        if(values.size() >= 3) {
            Path value2 = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LEGO_SENSOR_PATH + "/" +
                            SENSOR1 + "/" +
                            VALUE2);
            createFile(value2, String.valueOf(values.get(2)));
        }

        if(values.size() >= 4) {
            Path value3 = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LEGO_SENSOR_PATH + "/" +
                            SENSOR1 + "/" +
                            VALUE3);
            createFile(value3, String.valueOf(values.get(3)));
        }

        if(values.size() >= 5) {
            Path value4 = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LEGO_SENSOR_PATH + "/" +
                            SENSOR1 + "/" +
                            VALUE4);
            createFile(value4, String.valueOf(values.get(4)));
        }

        if(values.size() >= 6) {
            Path value5 = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LEGO_SENSOR_PATH + "/" +
                            SENSOR1 + "/" +
                            VALUE5);
            createFile(value5, String.valueOf(values.get(5)));
        }

        if(values.size() >= 7) {
            Path value6 = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LEGO_SENSOR_PATH + "/" +
                            SENSOR1 + "/" +
                            VALUE6);
            createFile(value6, String.valueOf(values.get(6)));
        }

        if(values.size() >= 8) {
            Path value7 = Paths.get(
                    EV3DEV_FAKE_SYSTEM_PATH + "/" +
                            LEGO_SENSOR_PATH + "/" +
                            SENSOR1 + "/" +
                            VALUE7);
            createFile(value7, String.valueOf(values.get(7)));
        }

    }

}
