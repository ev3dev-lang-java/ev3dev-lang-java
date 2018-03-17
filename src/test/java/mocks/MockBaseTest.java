package mocks;

import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

@Slf4j
public abstract class MockBaseTest {

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
