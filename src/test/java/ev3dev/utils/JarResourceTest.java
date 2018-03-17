package ev3dev.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
public class JarResourceTest {

    //OK

    @Test
    public void exportSuccessTest() throws Exception {

        final String JAVA_LOGO = Brickman.JAVA_DUKE_IMAGE_NAME;

        final String JAVA_LOGO_EXPORTED_PATH = JarResource.export(JAVA_LOGO);
        LOGGER.info(JAVA_LOGO_EXPORTED_PATH);

        final boolean result = Sysfs.existFile(Paths.get(JAVA_LOGO_EXPORTED_PATH));
        assertThat(result, is(true));
    }

    @Test
    public void deleteSuccessTest() throws Exception {

        final String JAVA_LOGO = Brickman.JAVA_DUKE_IMAGE_NAME;

        final String JAVA_LOGO_EXPORTED_PATH = JarResource.export(JAVA_LOGO);
        LOGGER.info(JAVA_LOGO_EXPORTED_PATH);

        final boolean result = Sysfs.existFile(Paths.get(JAVA_LOGO_EXPORTED_PATH));
        assertThat(result, is(true));

        JarResource.delete(JAVA_LOGO_EXPORTED_PATH);

        final boolean result2 = Sysfs.existFile(Paths.get(JAVA_LOGO_EXPORTED_PATH));
        assertThat(result2, is(false));
    }

}
