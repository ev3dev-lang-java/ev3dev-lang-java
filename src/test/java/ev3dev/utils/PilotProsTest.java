package ev3dev.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class PilotProsTest {

    @Test
    public void existKeyTest() throws Exception {
        PilotProps pilotProps = new PilotProps();
        pilotProps.loadPersistentValues();
        assertThat(pilotProps.getProperty("wheelDiameter"), is("8.2"));
    }

    @Test
    public void notExistKeyTest() throws Exception {
        PilotProps pilotProps = new PilotProps();
        pilotProps.loadPersistentValues();
        assertThat(pilotProps.getProperty("wheelDiameter2"), is(nullValue()));
    }

}
