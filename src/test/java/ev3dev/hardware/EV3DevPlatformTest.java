package ev3dev.hardware;

import ev3dev.utils.Sysfs;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public @Slf4j class EV3DevPlatformTest {

    /*
    private static final String EV3DEV_TESTING_KEY = "EV3DEV_TESTING_KEY";
    private static final String EV3DEV_TESTING_VALUE = "/ev3dev/mocks/";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    public class EV3DevPlatformChild extends EV3DevPlatform {

    }

    @Before
    public void onceExecutedBeforeAll() throws IOException {
        createEV3DevMocksPath();
        log.info("Test folder: " + tempMocksFolder.getAbsolutePath());

        System.setProperty(EV3DEV_TESTING_KEY, tempMocksFolder.getAbsolutePath());
        log.trace(System.getProperty("java.io.tmpdir"));
    }

    private static File tempEV3DevFolder;
    private static File tempMocksFolder;
    private static File powerSupplyFolder;
    private static File legoev3BatteryFolder;
    private static File file;

    public void createEV3DevMocksPath() throws IOException {
        tempEV3DevFolder = testFolder.newFolder("ev3dev");
        tempMocksFolder = new File(tempEV3DevFolder, "mocks");
    }

    @Test
    public void testInTempFolder() throws IOException {
        File tempFile = testFolder.newFile("file.txt");
        File tempFolder = testFolder.newFolder("folder");
        System.out.println("Test folder: " + tempFolder.exists());
        // test...
    }

    public void createEV3DevMocksPlatformPath() throws IOException {
        powerSupplyFolder = new File(tempMocksFolder, "power_supply");
        legoev3BatteryFolder = new File(powerSupplyFolder, "legoev3-battery");
        legoev3BatteryFolder.mkdir();
    }

    @Test
    public void testMockInjectionPathTest2() throws IOException {
        createEV3DevMocksPlatformPath();

        log.trace("Test folder: " + legoev3BatteryFolder.getAbsolutePath());

        final File f = new File(legoev3BatteryFolder.getAbsolutePath());
        log.trace("{}", f.exists());
        EV3DevPlatformChild epc = new EV3DevPlatformChild();
        log.info(epc.getPlatform().toString());
        //assertThat(epc.getROOT_PATH(), is(EV3DEV_TESTING_VALUE));


        file = new File(legoev3BatteryFolder, "voltage_now");

        PrintWriter out = new PrintWriter(file);
        out.println("8042133");
        out.flush();
        out.close();


        //System.out.println(Sysfs.existPath(tempEV3DevFolder.getAbsolutePath()));

    }
    */
}