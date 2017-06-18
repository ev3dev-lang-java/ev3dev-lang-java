package mocks.ev3dev.sensors;

import ev3dev.utils.SysfsTest;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class BatteryMock {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SysfsTest.class);

    private final TemporaryFolder tempFolder;

    //Base paths
    public static String JAVA_IO_TEMPDIR;
    public static String JUNIT_PATH;

    //Mock paths
    public static File tempEV3DevFolder;
    public static File tempMocksFolder;
    public static final String EV3DEV_PATH = "ev3dev";
    public static final String MOCKS_PATH = "mocks";

    //Sensor paths
    private static File tempBatteryFolder;
    protected static File tempEV3BatteryFolder;
    protected static final String BATTERY_PATH = "power_supply";
    protected static final String BATTERY_EV3_SUBPATH = "legoev3-battery";
    protected static final String BATTERY_BRICKPI_SUBPATH = "brickpi-battery";
    protected static final String BATTERY_BRICKPI3_SUBPATH = "brickpi3-battery";
    protected static final String BATTERY_PISTORMS_SUBPATH = "pistorms-battery";
    protected static final String BATTERY_FIELD_VOLTAGE = "voltage_now";
    protected static final String BATTERY_FIELD_VOLTAGE_VALUE = "8042133";
    protected static String BATTERY_FIELD_VOLTAGE_SUFFIX;
    protected File batterySensor;

    public BatteryMock(TemporaryFolder tempFolder) {
        this.tempFolder = tempFolder;
        JAVA_IO_TEMPDIR = System.getProperty("java.io.tmpdir");
        JUNIT_PATH = tempFolder.getRoot().getAbsolutePath().replace(JAVA_IO_TEMPDIR,"");
    }

    public String createEV3DevMocksEV3BrickPlatformPath() throws IOException {

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
        /*
        System.out.println(tempFolder.getRoot().getAbsolutePath().toString());
        List<File> list = Sysfs.getElements(tempEV3BatteryFolder.getAbsolutePath().toString());
        for(File item: list){
            System.out.println(item.getAbsolutePath());
        }
        System.out.println(BATTERY_FIELD_VOLTAGE_SUFFIX);
        */
        return BATTERY_FIELD_VOLTAGE_SUFFIX;

    }

    public void createEV3DevMocksBrickPiPlatformPath() throws IOException {
        tempBatteryFolder    = tempFolder.newFolder(EV3DEV_PATH, MOCKS_PATH, BATTERY_PATH);
        tempEV3BatteryFolder = tempFolder.newFolder(EV3DEV_PATH, MOCKS_PATH, BATTERY_PATH, BATTERY_BRICKPI_SUBPATH);

        batterySensor = File.createTempFile(BATTERY_FIELD_VOLTAGE, "", tempEV3BatteryFolder);
        //TODO How to create a file under tempEV3BatteryFolder
        //batterySensor = tempFolder.newFile(BATTERY_FIELD_VOLTAGE);
        PrintWriter out = new PrintWriter(batterySensor);
        out.println(BATTERY_FIELD_VOLTAGE_VALUE);
        out.flush();
        out.close();
        BATTERY_FIELD_VOLTAGE_SUFFIX = batterySensor.getAbsolutePath().replace(JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH + "/" + BATTERY_FIELD_VOLTAGE,"");
    }

    public void createEV3DevMocksBrickPi3PlatformPath() throws IOException {
        tempBatteryFolder    = tempFolder.newFolder(EV3DEV_PATH, MOCKS_PATH, BATTERY_PATH);
        tempEV3BatteryFolder = tempFolder.newFolder(EV3DEV_PATH, MOCKS_PATH, BATTERY_PATH, BATTERY_BRICKPI3_SUBPATH);

        batterySensor = File.createTempFile(BATTERY_FIELD_VOLTAGE, "", tempEV3BatteryFolder);
        //TODO How to create a file under tempEV3BatteryFolder
        //batterySensor = tempFolder.newFile(BATTERY_FIELD_VOLTAGE);
        PrintWriter out = new PrintWriter(batterySensor);
        out.println(BATTERY_FIELD_VOLTAGE_VALUE);
        out.flush();
        out.close();
        BATTERY_FIELD_VOLTAGE_SUFFIX = batterySensor.getAbsolutePath().replace(JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH + "/" + BATTERY_FIELD_VOLTAGE,"");
    }

    public void createEV3DevMocksPiStormsPlatformPath() throws IOException {
        tempBatteryFolder    = tempFolder.newFolder(EV3DEV_PATH, MOCKS_PATH, BATTERY_PATH);
        tempEV3BatteryFolder = tempFolder.newFolder(EV3DEV_PATH, MOCKS_PATH, BATTERY_PATH, BATTERY_PISTORMS_SUBPATH);

        batterySensor = File.createTempFile(BATTERY_FIELD_VOLTAGE, "", tempEV3BatteryFolder);
        //TODO How to create a file under tempEV3BatteryFolder
        //batterySensor = tempFolder.newFile(BATTERY_FIELD_VOLTAGE);
        PrintWriter out = new PrintWriter(batterySensor);
        out.println(BATTERY_FIELD_VOLTAGE_VALUE);
        out.flush();
        out.close();
        BATTERY_FIELD_VOLTAGE_SUFFIX = batterySensor.getAbsolutePath().replace(JAVA_IO_TEMPDIR + JUNIT_PATH + "/"+ EV3DEV_PATH + "/" + MOCKS_PATH + "/" + BATTERY_PATH + "/" + BATTERY_EV3_SUBPATH + "/" + BATTERY_FIELD_VOLTAGE,"");
    }

    public File getTempBatteryFolder() {
        return tempBatteryFolder;
    }
}
