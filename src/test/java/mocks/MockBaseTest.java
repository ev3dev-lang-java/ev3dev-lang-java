package mocks;

import ev3dev.utils.SysfsTest;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public abstract class MockBaseTest {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SysfsTest.class);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    protected final String EV3DEV_TESTING_KEY = "EV3DEV_TESTING_KEY";
    protected final String EV3DEV_TESTING_VALUE = "/ev3dev/mocks";

    //Base paths
    protected static String JAVA_IO_TEMPDIR;
    protected static String JUNIT_PATH;

    //Mock paths
    protected static final String EV3DEV_PATH = "ev3dev";
    protected static final String MOCKS_PATH = "mocks";
    protected static File tempEV3DevFolder;
    protected static File tempMocksFolder;

    public MockBaseTest() {

    }

    public void getGlobalPaths(){
        JAVA_IO_TEMPDIR = System.getProperty("java.io.tmpdir");
        JUNIT_PATH = tempFolder.getRoot().getAbsolutePath().replace(JAVA_IO_TEMPDIR,"");
    }

    public void createEV3DevMocksPath() throws IOException {
        tempEV3DevFolder = tempFolder.newFolder(EV3DEV_PATH);
        tempMocksFolder  = tempFolder.newFolder(EV3DEV_PATH, MOCKS_PATH);
    }

}
