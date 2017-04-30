package ev3dev.hardware;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

//import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class TemporaryFolderTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /*
    @Test
    public void testWrite() throws IOException {
        // Create a temporary file.
        final File tempFile = tempFolder.newFile("tempFile.txt");

        // Write something to it.
        FileUtils.writeStringToFile(tempFile, "hello world");

        System.out.println(FileUtils.listFiles(tempFile, null, false).size());

        // Read it from temp file
        final String s = FileUtils.readFileToString(tempFile);

        // Verify the content
        Assert.assertEquals("hello world", s);

        //Note: File is guaranteed to be deleted after the test finishes.
    }
    */
}

