package ev3dev.utils;

import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Before;
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

    @Before
    @SneakyThrows
    public void createFiles() {
        tempDirectory = Files.createTempDirectory("DataChannelRereaderTest");
        testPath = Files.createFile(Path.of(tempDirectory.toString(),"testFile"));
        Files.write(testPath, testString.getBytes());
    }

    @After
    @SneakyThrows
    public void cleanupFiles() {
        Files.delete(testPath);
        Files.delete(tempDirectory);
    }


    @Test
    @SneakyThrows
    public void testOpenClose() {
        DataChannelRereader rereader = new DataChannelRereader(testPath,32);
        rereader.close();
    }

    @Test
    @SneakyThrows
    public void testOpenReadClose() {
        DataChannelRereader rereader = new DataChannelRereader(testPath,32);
        String readString = rereader.readString();
        rereader.close();

        assertEquals(testString,readString);
    }

    @Test
    @SneakyThrows
    public void testClosable() {
        try(DataChannelRereader rereader = new DataChannelRereader(testPath,32)){
            String readString = rereader.readString();
            assertEquals(testString,readString);
        }
    }

    @Test
    @SneakyThrows
    public void testOpenReadTwoThingsClose() {
        DataChannelRereader rereader = new DataChannelRereader(testPath,32);
        String readString = rereader.readString();
        assertEquals(testString,readString);

        Files.write(testPath, differentTestString.getBytes());

        String readString2 = rereader.readString();
        assertEquals(differentTestString,readString2);

        rereader.close();
    }


}
