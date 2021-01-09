package ev3dev.utils;

import java.io.IOException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

/**
 * Some tests of DataChannelRereader.
 *
 * @author David Walend
 */
public class DataChannelRereaderTest {

    static Path tempDirectory;
    static Path testPath;
    static final String testString = "Written String";
    static final String differentTestString = "Different String";

    @BeforeClass
    public static void createFiles() throws IOException {
        tempDirectory = Files.createTempDirectory("DataChannelRereaderTest");
        testPath = Files.createFile(Path.of(tempDirectory.toString(),"testFile"));
        Files.write(testPath, testString.getBytes());
    }

    @Before
    public void writeFile() throws IOException {
        Files.write(testPath, testString.getBytes());
    }

    @AfterClass
    public static void cleanupFiles() throws IOException {
        Files.delete(testPath);
        Files.delete(tempDirectory);
    }


    @Test
    public void testOpenClose() throws IOException {
        DataChannelRereader rereader = new DataChannelRereader(testPath,32);
        rereader.close();
    }

    @Test
    public void testOpenReadClose() throws IOException {
        DataChannelRereader rereader = new DataChannelRereader(testPath,32);
        String readString = rereader.readString();
        rereader.close();

        assertEquals(testString,readString);
    }

    @Test
    public void testClosable() throws IOException {
        try(DataChannelRereader rereader = new DataChannelRereader(testPath,32)){
            String readString = rereader.readString();
            assertEquals(testString,readString);
        }
    }

    @Test
    public void testOpenReadTwoThingsClose() throws IOException {
        DataChannelRereader rereader = new DataChannelRereader(testPath,32);
        String readString = rereader.readString();
        assertEquals(testString,readString);

        Files.write(testPath, differentTestString.getBytes());

        String readString2 = rereader.readString();
        assertEquals(differentTestString,readString2);

        rereader.close();
    }

    @Test(expected = RuntimeException.class)
    public void testOpenNonexistentFile() throws IOException {
        Path badPath = Path.of("/does/not/exist");

        DataChannelRereader rereader = new DataChannelRereader(badPath,32);
        rereader.close();
    }
}
