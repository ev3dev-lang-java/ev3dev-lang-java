package ev3dev.utils;

import ev3dev.hardware.EV3DevPlatform;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public @Slf4j class SysfsTest {

    private static final String EV3DEV_TESTING_KEY = "EV3DEV_TESTING_KEY";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static File tempEV3DevFolder;
    private static File tempMocksFolder;
    private static File powerSupplyFolder;
    private static File legoev3BatteryFolder;
    private static File file;

    public void createEV3DevMocksPath() throws IOException {
        tempEV3DevFolder = testFolder.newFolder("ev3dev");
        tempMocksFolder = new File(tempEV3DevFolder, "mocks");
    }

    @Before
    public void onceExecutedBeforeAll() throws IOException {
        createEV3DevMocksPath();
        log.trace(System.getProperty("java.io.tmpdir"));
        log.info("JUnit EV3Dev path: " + tempMocksFolder.getAbsolutePath());

        System.setProperty(EV3DEV_TESTING_KEY, tempMocksFolder.getAbsolutePath());
    }

    public void createEV3DevMocksPlatformPath() throws IOException {
        powerSupplyFolder = new File(tempMocksFolder, "power_supply");
        legoev3BatteryFolder = new File(powerSupplyFolder, "legoev3-battery");
        legoev3BatteryFolder.mkdir();
    }

    @Test
    public void testInTempFolder() throws IOException {
        File tempFile = testFolder.newFile("file.txt");
        File tempFolder = testFolder.newFolder("folder");
        log.trace("Test folder: " + tempFolder.exists());
        // test...
    }

}