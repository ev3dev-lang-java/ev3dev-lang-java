package ev3dev.hardware;

import ev3dev.utils.Sysfs;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EV3DevPlatformTest {

    private static final String EV3DEV_TESTING_KEY = "EV3DEV_TESTING_KEY";
    private static final String EV3DEV_TESTING_VALUE = "/ev3dev/mocks/";

    @BeforeClass
    public static void onceExecutedBeforeAll() {
        System.setProperty(EV3DEV_TESTING_KEY, EV3DEV_TESTING_VALUE);
    }

    public class EV3DevFileSystemChild extends EV3DevFileSystem {

    }

    @Test
    public void testMockInjectionPathTest() throws IOException {
        EV3DevFileSystemChild efc = new EV3DevFileSystemChild();
        assertThat(efc.getROOT_PATH(), is(EV3DEV_TESTING_VALUE));
    }

    /*
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private static File tempFolder;
    private static File normalFolder;
    private static File file;

    public void createDirs() throws IOException {
        tempFolder = folder.newFolder("ev3dev");
        normalFolder = new File(tempFolder, "mocks");
        normalFolder.mkdir();
        file = new File(normalFolder, "file.txt");

        PrintWriter out = new PrintWriter(file);
        out.println("hello world");
        out.flush();
        out.close();
    }

    @Test
    public void testMockInjectionPathTest2() throws IOException {
        createDirs();
        System.out.println("Test folder: " + normalFolder.getAbsolutePath());
        System.out.println(Sysfs.existPath(normalFolder.getAbsolutePath()));

        EV3DevFileSystemChild efc = new EV3DevFileSystemChild();
        assertThat(efc.ROOT_PATH, is(EV3DEV_TESTING_VALUE));
    }
    */

}