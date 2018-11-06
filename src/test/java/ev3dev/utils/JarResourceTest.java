package ev3dev.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@Slf4j
public class JarResourceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //OK

    @Test
    public void exportSuccessTest() throws Exception {

        final String JAVA_LOGO = JarResource.JAVA_DUKE_IMAGE_NAME;

        final String JAVA_LOGO_EXPORTED_PATH = JarResource.export(JAVA_LOGO);
        LOGGER.info(JAVA_LOGO_EXPORTED_PATH);

        final boolean result = Sysfs.existFile(Paths.get(JAVA_LOGO_EXPORTED_PATH));
        assertThat(result, is(true));
    }

    @Test
    public void exportKoTest() throws Exception {

        thrown.expect(IllegalArgumentException.class);

        JarResource.export("BadPath.png");
    }

    @Test
    public void readSuccessTest() throws Exception {

        final String JAVA_LOGO = JarResource.JAVA_DUKE_IMAGE_NAME;

        byte[] data = JarResource.read(JAVA_LOGO);

        assertThat(data.length, greaterThan(0));
    }

    @Test
    public void readKoTest() throws Exception {

        thrown.expect(IllegalArgumentException.class);

        JarResource.read("BadPath.png");
    }

    @Test
    public void deleteSuccessTest() throws Exception {

        final String JAVA_LOGO = JarResource.JAVA_DUKE_IMAGE_NAME;

        final String JAVA_LOGO_EXPORTED_PATH = JarResource.export(JAVA_LOGO);
        LOGGER.info(JAVA_LOGO_EXPORTED_PATH);

        final boolean result = Sysfs.existFile(Paths.get(JAVA_LOGO_EXPORTED_PATH));
        assertThat(result, is(true));

        JarResource.delete(JAVA_LOGO_EXPORTED_PATH);

        final boolean result2 = Sysfs.existFile(Paths.get(JAVA_LOGO_EXPORTED_PATH));
        assertThat(result2, is(false));
    }

    //TODO Review this method
    @Test
    public void deleteSuccessTest2() throws Exception {

        JarResource.delete("BadPath.png");
    }

}
