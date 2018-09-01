package ev3dev.hardware.display;

import ev3dev.hardware.display.spi.FramebufferProvider;
import ev3dev.utils.io.ErrnoException;
import ev3dev.utils.io.NativeConstants;
import ev3dev.utils.io.NativeFramebuffer;
import ev3dev.utils.io.NativeTTY;
import sun.misc.Signal;

import java.io.IOException;

import static ev3dev.utils.io.NativeConstants.*;

/**
 * <p>System console manager.</p>
 *
 * <p>It manages the output mode of the display. It is possible
 * to switch between graphics mode and text mode. Graphics mode reserves
 * the display for drawing operation and hides text output. On the
 * other hand, text mode suspends drawing operations and shows the text
 * on the Linux console.
 * This class also manages VT (= Virtual Terminal = console) switches
 * in the case of a VT switch occuring when graphics mode is set.</p>
 *
 * <p>Implementation of this class is based on the GRX3 linuxfb plugin.</p>
 *
 * @author Jakub Vaněk
 * @since 2.4.7
 */
public class SystemDisplay implements DisplayInterface {
    private static DisplayInterface instance = null;
    private String fbPath = null;
    private JavaFramebuffer fbInstance = null;
    private NativeTTY ttyfd = null;
    private boolean gfx_active = false;
    private int old_kbmode;

    /**
     * <p>Initialize the display, register event handlers and switch to text mode.</p>
     *
     * @throws RuntimeException when initialization or mode switch fails.
     */
    private SystemDisplay() {
        try {
            initialize();
            Signal.handle(new Signal("USR2"), this::console_switch_handler);
            Runtime.getRuntime().addShutdownHook(new Thread(this::deinitialize, "console restore"));
            switchToTextMode();
        } catch (IOException e) {
            throw new RuntimeException("Error initializing system console", e);
        }
    }

    /**
     * <p>Get the system display.</p>
     * <p>The console number the system display will be on is
     * the same as the one this program is started on.</p>
     *
     * @return Reference to the display this program was opened on.
     */
    public static synchronized DisplayInterface getInstance() {
        if (instance == null) {
            try {
                instance = new SystemDisplay();
            } catch (RuntimeException e) {
                if (e.getCause() instanceof ErrnoException &&
                        ((ErrnoException) e.getCause()).getErrno() == NativeConstants.ENOTTY) {
                    // we do not run from Brickman
                    instance = new FakeDisplay();
                } else {
                    throw e;
                }
            }
        }
        return instance;
    }

    /**
     * <p>Initialize the display state.</p>
     *
     * <p>Opens the current VT, saves keyboard mode and
     * identifies the appropriate framebuffer device.</p>
     *
     * @throws IOException When the initialization fails.
     */
    private void initialize() throws IOException {
        NativeFramebuffer fbfd = null;
        boolean success = false;
        try {
            ttyfd = new NativeTTY("/dev/tty", O_RDWR);
            int activeVT = ttyfd.getVTstate().v_active;
            old_kbmode = ttyfd.getKeyboardMode();

            fbfd = new NativeFramebuffer("/dev/fb0");
            int fbn = fbfd.mapConsoleToFramebuffer(activeVT);

            if (fbn < 0) {
                throw new IOException("No framebuffer device for the current VT");
            }
            fbPath = "/dev/fb" + fbn;
            if (fbn != 0) {
                fbfd.close();
                fbfd = new NativeFramebuffer(fbPath);
            }

            success = true;
        } finally {
            if (fbfd != null) {
                fbfd.close();
            }
            if (!success) {
                ttyfd.close();
            }
        }
    }

    /**
     * <p>Put the display to a state where it is ready for returning.</p>
     *
     * <p>Keyboard mode is restored, text mode is set and VT autoswitch is enabled.
     * Then, console file descriptor is closed.</p>
     */
    private void deinitialize() {
        try {
            ttyfd.setKeyboardMode(old_kbmode);
            ttyfd.setConsoleMode(KD_TEXT);

            NativeTTY.vt_mode vtm = new NativeTTY.vt_mode();
            vtm.mode = VT_AUTO;
            vtm.relsig = 0;
            vtm.acqsig = 0;
            ttyfd.setVTmode(vtm);

            ttyfd.close();

        } catch (IOException e) {
            System.err.println("Error occured during console shutdown: " + e.getMessage());
            e.printStackTrace();
        }
        gfx_active = false;
    }

    /**
     * <p>Switch the display to a graphics mode.</p>
     *
     * <p>It switches VT to graphics mode with keyboard turned off.
     * Then, it tells kernel to notify Java when VT switch occurs.
     * Also, framebuffer contents are restored and write access is enabled.</p>
     *
     * @throws RuntimeException when the switch fails
     */
    public void switchToGraphicsMode() {
        try {
            ttyfd.setKeyboardMode(K_OFF);
            ttyfd.setConsoleMode(KD_GRAPHICS);

            NativeTTY.vt_mode vtm = new NativeTTY.vt_mode();
            vtm.mode = VT_PROCESS;
            vtm.relsig = SIGUSR2;
            vtm.acqsig = SIGUSR2;
            ttyfd.setVTmode(vtm);
        } catch (IOException e) {
            throw new RuntimeException("Switch to graphics mode failed", e);
        }

        gfx_active = true;
        if (fbInstance != null) {
            fbInstance.restoreData();
            fbInstance.setFlushEnabled(true);
        }
    }

    /**
     * <p>Switch the display to a text mode.</p>
     *
     * <p>It stores framebuffer data and disables write access. Then,
     * it switches VT to text mode and allows kernel to auto-switch it.</p>
     *
     * @throws RuntimeException when the switch fails
     */
    public void switchToTextMode() {
        if (fbInstance != null) {
            fbInstance.setFlushEnabled(false);
            fbInstance.storeData();
        }
        try {
            ttyfd.setConsoleMode(KD_TEXT);

            NativeTTY.vt_mode vtm = new NativeTTY.vt_mode();
            vtm.mode = VT_AUTO;
            vtm.relsig = 0;
            vtm.acqsig = 0;
            ttyfd.setVTmode(vtm);
        } catch (IOException e) {
            throw new RuntimeException("Switch to text mode failed", e);
        }

        gfx_active = false;
    }

    /**
     * <p>Release ownership of our VT.</p>
     * <p>Stores framebuffer contents and disables write access.
     * Then, it allows kernel to switch the VT.</p>
     */
    private void vt_release() {
        if (fbInstance != null) {
            fbInstance.setFlushEnabled(false);
            fbInstance.storeData();
        }
        try {
            ttyfd.signalSwitch(1);
        } catch (IOException e) {
            System.err.println("Error occured during VT switch: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * <p>Take ownership of our VT.</p>
     * <p>Acknowledges VT transition, enables graphics mode and disables keyboard.
     * Then, it restores framebuffer contents and enables write access.</p>
     */
    private void vt_acquire() {
        try {
            ttyfd.signalSwitch(VT_ACKACQ);
            ttyfd.setKeyboardMode(K_OFF);
            ttyfd.setConsoleMode(KD_GRAPHICS);
        } catch (IOException e) {
            System.err.println("Error occured during VT switch: " + e.getMessage());
            e.printStackTrace();
        }
        if (fbInstance != null) {
            fbInstance.restoreData();
            fbInstance.setFlushEnabled(true);
        }
    }

    /**
     * <p>Handle VT switch signal.</p>
     *
     * @param sig unused argument for SignalHandler compatibility
     */
    private void console_switch_handler(Signal sig) {
        if (gfx_active) {
            vt_release();
            gfx_active = false;
        } else {
            vt_acquire();
            gfx_active = true;
        }
    }

    /**
     * <p>Get the framebuffer for the system display.</p>
     *
     * <p>The framebuffer is initialized only once, later calls
     * return references to the same instance.</p>
     *
     * @return Java framebuffer compatible with the system display.
     * @throws RuntimeException when switch to graphics mode or the framebuffer initialization fails.
     */
    public synchronized JavaFramebuffer openFramebuffer() {
        if (fbInstance == null) {
            switchToGraphicsMode();
            try {
                fbInstance = FramebufferProvider.load(fbPath);
            } catch (FramebufferProvider.UnknownFramebufferException e) {
                throw new RuntimeException("System framebuffer opening failed", e);
            }
            fbInstance.setFlushEnabled(gfx_active);
            fbInstance.clear();
            fbInstance.storeData();
        }
        return fbInstance;
    }

    /**
     * Class to allow running programs over SSH
     *
     * @author Jakub Vaněk
     * @since 2.4.7
     */
    private static class FakeDisplay implements DisplayInterface {
        private JavaFramebuffer fbInstance = null;

        /**
         * noop
         */
        private FakeDisplay() {
        }

        /**
         * noop, graphics goes to the display
         */
        @Override
        public void switchToGraphicsMode() {
        }

        /**
         * noop, text goes to SSH host
         */
        @Override
        public void switchToTextMode() {
        }

        @Override
        public synchronized JavaFramebuffer openFramebuffer() {
            if (fbInstance == null) {
                Brickman.disable();
                try {
                    fbInstance = FramebufferProvider.load("/dev/fb0");
                } catch (FramebufferProvider.UnknownFramebufferException e) {
                    throw new RuntimeException("System framebuffer opening failed", e);
                }
                fbInstance.setFlushEnabled(true);
                fbInstance.clear();
                fbInstance.storeData();
            }
            return fbInstance;
        }
    }
}
