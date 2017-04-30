package mocks;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class MockBaseTest {

    private static final String EV3DEV_TESTING_KEY = "EV3DEV_TESTING_KEY";

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    //Base paths
    protected static String JAVA_IO_TEMPDIR;
    protected static String JUNIT_PATH;

    //Mock paths
    protected static File tempEV3DevFolder;
    protected static File tempMocksFolder;
    protected static final String EV3DEV_PATH = "ev3dev";
    protected static final String MOCKS_PATH = "mocks";

    //Sensor paths
    private static File tempBatteryFolder;
    protected static File tempEV3BatteryFolder;
    protected static final String BATTERY_PATH = "power_supply";
    protected static final String BATTERY_EV3_SUBPATH = "legoev3-battery";
    protected static final String BATTERY_FIELD_VOLTAGE = "voltage_now";
    protected static final String BATTERY_FIELD_VOLTAGE_VALUE = "8042133";
    protected static String BATTERY_FIELD_VOLTAGE_SUFFIX;
    protected File batterySensor;

    public void getGlobalPaths(){
        JAVA_IO_TEMPDIR = System.getProperty("java.io.tmpdir");
        JUNIT_PATH = tempFolder.getRoot().getAbsolutePath().replace(JAVA_IO_TEMPDIR,"");
    }

    public void createEV3DevMocksPath() throws IOException {
        tempEV3DevFolder = tempFolder.newFolder(EV3DEV_PATH);
        tempMocksFolder  = tempFolder.newFolder(EV3DEV_PATH, MOCKS_PATH);
    }

    public void createEV3DevMocksPlatformPath() throws IOException {
        tempBatteryFolder    = tempFolder.newFolder(EV3DEV_PATH, MOCKS_PATH, BATTERY_PATH);
        tempEV3BatteryFolder = tempFolder.newFolder(EV3DEV_PATH, MOCKS_PATH, BATTERY_PATH, BATTERY_EV3_SUBPATH);

        batterySensor = File.createTempFile(BATTERY_FIELD_VOLTAGE, "", tempEV3BatteryFolder);
        //TODO How to create a file under tempEV3BatteryFolder
        //batterySensor = tempFolder.newFile(BATTERY_FIELD_VOLTAGE);
        PrintWriter out = new PrintWriter(batterySensor);
        out.println(BATTERY_FIELD_VOLTAGE_VALUE);
        out.flush();
        out.close();
        BATTERY_FIELD_VOLTAGE_SUFFIX = batterySensor.getAbsolutePath().replace(JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH + "/" + BATTERY_FIELD_VOLTAGE,"");
    }

    @Before
    public void onceExecutedBeforeAll() throws IOException {
        getGlobalPaths();
        createEV3DevMocksPath();
        createEV3DevMocksPlatformPath();
        System.setProperty(EV3DEV_TESTING_KEY, tempMocksFolder.getAbsolutePath());
    }

}
