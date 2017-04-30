package ev3dev.hardware;

import lombok.extern.slf4j.Slf4j;
import mocks.MockBaseTest;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public @Slf4j class EV3DevPlatformTest extends MockBaseTest {

    public class EV3DevPlatformChild extends EV3DevPlatform {

    }

    @Test
    public void testMockInjectionPathTest2() throws IOException {
        EV3DevPlatformChild epc = new EV3DevPlatformChild();
        assertThat(epc.getPlatform(), is(EV3DevPlatforms.EV3BRICK));
    }
}