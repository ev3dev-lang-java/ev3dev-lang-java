package ev3dev.utils;

import mocks.MockBaseTest;
import org.junit.Test;
import org.slf4j.Logger;

import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class JarResourceTest extends MockBaseTest{

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(JarResourceTest.class);

    //OK

    @Test
    public void classTest() throws Exception {

        final String JAVA_LOGO = "java_logo.png";

        final String where = JarResource.export(JAVA_LOGO);
        final boolean result = Sysfs.existFile(Paths.get(where));

        assertThat(result, is(true));

        JarResource.delete(JAVA_LOGO);
    }

}
