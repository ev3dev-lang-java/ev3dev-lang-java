package ev3dev.hardware;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class EV3DevPlatformTest {

    @Test
    public void getPlatformByString() {

        String result;

        result = EV3DevPlatform.getPlatformByString("EV3BRICK");
        assertThat(result, is("EV3BRICK"));

        result = EV3DevPlatform.getPlatformByString("BRICKPI");
        assertThat(result, is("BRICKPI"));

        result = EV3DevPlatform.getPlatformByString("BRICKPI3");
        assertThat(result, is("BRICKPI3"));

        result = EV3DevPlatform.getPlatformByString("PISTORMS");
        assertThat(result, is("PISTORMS"));

        result = EV3DevPlatform.getPlatformByString("MYPLATFORM");
        assertThat(result, is("UNKNOWN"));

    }
}