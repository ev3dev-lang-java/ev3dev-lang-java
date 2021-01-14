package ev3dev.utils;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

/**
 * Some tests of DataChannelRewriter.
 *
 * @author David Walend
 */
public class DataChannelRewriterTest {

    static Path tempDirectory;
    static Path testPath;
    static final String startString = "Original String";
    static final String differentString = "Written String";

    @BeforeClass
    public static void createFiles() throws IOException {
        tempDirectory = Files.createTempDirectory("DataChannelRewriterTest");
        testPath = Files.createFile(Path.of(tempDirectory.toString(),"testFile"));
        Files.write(testPath, startString.getBytes());
    }

    @Before
    public void writeFile() throws IOException {
        Files.write(testPath, startString.getBytes());
    }

    @AfterClass
    public static void cleanupFiles() throws IOException {
        Files.delete(testPath);
        Files.delete(tempDirectory);
    }


    @Test
    public void testOpenClose() throws IOException {
        DataChannelRewriter rewriter = new DataChannelRewriter(testPath,32);
        rewriter.close();

        assertEquals(startString,Files.readString(testPath));
    }

    @Test
    public void testOpenReadClose() throws IOException {
        DataChannelRewriter rewriter = new DataChannelRewriter(testPath,32);
        rewriter.writeString(differentString);
        rewriter.close();

        assertEquals(differentString+"\n",Files.readString(testPath));
    }

    @Test
    public void testClosable() throws IOException {
        try(DataChannelRewriter rewriter = new DataChannelRewriter(testPath,32)){
            rewriter.writeString(differentString);
        }
        assertEquals(differentString+"\n",Files.readString(testPath));
    }

    @Test
    public void testOpenWriteTwoThingsClose() throws IOException {
        DataChannelRewriter rewriter = new DataChannelRewriter(testPath,32);
        rewriter.writeString(differentString);
        assertEquals(differentString+"\n",Files.readString(testPath));

        String anotherString = "Another String";
        rewriter.writeString(anotherString);

        assertEquals(anotherString+"\n",Files.readString(testPath));

        rewriter.close();
    }

    @Test(expected = RuntimeException.class)
    public void testOpenNonexistentFile() throws IOException {
        Path badPath = Path.of("/does/not/exist");

        DataChannelRewriter rewriter = new DataChannelRewriter(badPath,32);
        rewriter.writeString(differentString);
        rewriter.close();
    }
}
