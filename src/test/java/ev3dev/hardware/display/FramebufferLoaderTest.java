package ev3dev.hardware.display;

import org.junit.Test;

public class FramebufferLoaderTest {
    @Test(expected = Framebuffers.NoAvailableImplementationException.class)
    public void loadFailTest() {
        Framebuffers.load("/must/not/exist");
    }
}
