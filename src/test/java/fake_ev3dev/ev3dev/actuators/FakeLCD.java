package fake_ev3dev.ev3dev.actuators;

import ev3dev.actuators.LCD;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.hardware.EV3DevPlatforms;
import ev3dev.utils.display.ImageUtils;
import ev3dev.utils.display.JavaFramebuffer;
import fake_ev3dev.BaseElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FakeLCD extends BaseElement {
    private int width;
    private int height;
    private static int flushes = 0;

    public FakeLCD(final EV3DevPlatform ev3DevPlatform) throws IOException {
        EV3DevPlatforms conf = new EV3DevPlatforms(ev3DevPlatform);

        Path devPath = Paths.get(EV3DEV_FAKE_SYSTEM_PATH);
        createDirectories(devPath);
        switch(ev3DevPlatform) {
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
        LCD.setInstance(new LCD(new FakeLCDImpl()));
    }

    public class FakeLCDImpl implements JavaFramebuffer {

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
            String filename = "lcdflush" + (flushes++) + ".png";
            Path pth = Paths.get(EV3DEV_FAKE_SYSTEM_PATH, "..", filename);
            try {
                ImageIO.write(compatible, "png", new File(pth.toUri()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
