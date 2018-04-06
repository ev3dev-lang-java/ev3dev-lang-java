package ev3dev.utils;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class InterpolationTest {

    @Test
    public void interpolateTest() {

        final float y0 = 1.0f;
        final float y1 = 0.0f;

        final float x = 40;
        final float x0 = 100.0f;
        final float x1 = 0.0f;

        float result = Interpolation.interpolate(x, x0, x1, y0, y1);

        assertThat(result, is(0.40000004F));
    }
}