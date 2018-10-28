package ev3dev.hardware.display;

import ev3dev.utils.io.NativeFramebuffer;
import fake_ev3dev.ev3dev.utils.io.CountingFile;
import fake_ev3dev.ev3dev.utils.io.EmulatedFramebufferBuilder;
import fake_ev3dev.ev3dev.utils.io.EmulatedLibc;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class RGBFramebufferTest extends BaseFramebufferTest {
    private static final int WIDTH = 178;
    private static final int HEIGHT = 128;
    private static final int BPP = 32;
    private static final int BPP_BAD = 1;
    private static final int STRIDE = (WIDTH * BPP + 7) / 8;
    private static final int STRIDE_BAD = (WIDTH * BPP_BAD + 7) / 8;

    private NativeFramebuffer device;
    private JavaFramebuffer adapter;

    @Before
    public void reset() {
        eLibc = new EmulatedLibc();
        eFb = new EmulatedFramebufferBuilder()
                .setXRGB()
                .setScreenSize(WIDTH, HEIGHT, STRIDE)
                .setNumber(0).addConsoleFramebufferMap(0, 0)
                .build();
        eCtr = new CountingFile(eFb);
        eLibc.install("/dev/fb0", eCtr);
        device = new NativeFramebuffer("/dev/fb0", eLibc);
        adapter = new RGBFramebuffer(device, null);
    }

    @Test
    public void DoTestAdapter() throws IOException {
        super.TestAdapter(adapter, WIDTH, HEIGHT, STRIDE);
    }

    @Test
    public void DoTestDraw() throws IOException {
        super.TestDraw(adapter);
    }

    @Test
    public void DoTestDrawBan() throws IOException {
        super.TestDrawBan(adapter);
    }

    @Test
    public void DoTestDrawDeviceClosed() throws IOException {
        device.close();
        super.TestDraw(adapter);
    }

    @Test(expected = NullPointerException.class)
    public void DoTestDrawAdapterClosed() throws IOException {
        adapter.close();
        super.TestDraw(adapter);
    }

    @Test
    public void DoTestClean() throws IOException {
        super.TestClean(adapter);
    }

    @Test
    public void DoTestRestore() throws IOException {
        super.TestRestore(adapter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void DoTestInvalidType() throws IOException {
        eLibc.remove("/dev/fb0");
        eFb = new EmulatedFramebufferBuilder()
                .setBW(true)
                .setScreenSize(WIDTH, HEIGHT, STRIDE_BAD)
                .setNumber(0).addConsoleFramebufferMap(0, 0)
                .build();
        eCtr = new CountingFile(eFb);
        eLibc.install("/dev/fb0", eCtr);
        device = new NativeFramebuffer("/dev/fb0", eLibc);
        adapter = new RGBFramebuffer(device, null);
        adapter.close();
    }
}
