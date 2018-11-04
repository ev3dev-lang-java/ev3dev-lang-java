package fake_ev3dev.ev3dev.actuators;

import ev3dev.hardware.EV3DevPlatform;
import ev3dev.hardware.display.DisplayInterface;
import ev3dev.hardware.display.ImageUtils;
import ev3dev.hardware.display.JavaFramebuffer;
import fake_ev3dev.BaseElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

// TODO unbreak this broken design
public class FakeLCD extends BaseElement {
    private static int flushes = 0;
    private int width;
    private int height;

    public FakeLCD(final EV3DevPlatform ev3DevPlatform) throws IOException {

        //TODO Review this idea.
        //EV3DevPlatforms conf = new EV3DevPlatforms(ev3DevPlatform);

        Path devPath = Paths.get(EV3DEV_FAKE_SYSTEM_PATH);
        createDirectories(devPath);
        switch (ev3DevPlatform) {
            case EV3BRICK:
                width = 178;
                height = 128;
                break;
            case PISTORMS:
                width = 320;
                height = 240;
                break;
            case BRICKPI:
            case BRICKPI3:
            case UNKNOWN:
                throw new RuntimeException("Nope");
        }
    }

    public class FakeLCDImpl implements JavaFramebuffer {
        private boolean flushEnable = true;

        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public int getHeight() {
            return height;
        }

        @Override
        public int getStride() {
            return 4;
        }

        @Override
        public BufferedImage createCompatibleBuffer() {
            return createCompatibleBuffer(getWidth(), getHeight());
        }

        @Override
        public BufferedImage createCompatibleBuffer(int width, int height) {
            return createCompatibleBuffer(width, height, 4 * width);
        }

        @Override
        public BufferedImage createCompatibleBuffer(int width, int height, int stride) {
            return createCompatibleBuffer(width, height, stride, new byte[stride * height]);
        }

        @Override
        public BufferedImage createCompatibleBuffer(int width, int height, int stride, byte[] backed) {
            return ImageUtils.createXRGBImage(width, height, stride, backed);
        }

        @Override
        public void flushScreen(BufferedImage compatible) {
            if (!flushEnable) {
                return;
            }
            String filename = "lcdflush" + (flushes++) + ".png";
            Path pth = Paths.get(EV3DEV_FAKE_SYSTEM_PATH, "..", filename);
            try {
                ImageIO.write(compatible, "png", new File(pth.toUri()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void setFlushEnabled(boolean rly) {
            flushEnable = rly;
        }

        @Override
        public void storeData() {
            // noop
        }

        @Override
        public void restoreData() {
            // noop
        }

        @Override
        public void clear() {
            // noop
        }

        @Override
        public DisplayInterface getDisplay() {
            return null;
        }

        @Override
        public void close() {
        }
    }

}
