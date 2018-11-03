package ev3dev.hardware.display;

import ev3dev.utils.JarResource;
import fake_ev3dev.BaseElement;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.Assert.assertEquals;

public class ImageUtilsTest {
    private static final int width = 128;
    private static final int height = 128;
    private static final int xrgbStrideBig = width * 5;
    private static final int xrgbStrideNormal = width * 4;
    private static final int xrgbStrideSmall = width * 3;
    private static final int bitStrideBig = width / 4;
    private static final int bitStrideNormal = width / 8;
    private static final int bitStrideSmall = width / 16;
    private static final String imageResource = "java_logo.png";

    @Test
    public void renderXRGBImage() throws IOException {
        // intentional wider stride
        int[] offsets = new int[]{0, 1, 2, 3};
        BufferedImage img = ImageUtils.createXRGBImage(width, height, xrgbStrideBig);
        Graphics2D gfx = img.createGraphics();
        gfx.setColor(Color.WHITE);
        gfx.fillRect(0, 0, width, height);

        BufferedImage logo = JarResource.loadImage(imageResource);
        gfx.drawImage(logo, 0, 0, null);
        gfx.dispose();

        try {
            Path raw = Paths.get(BaseElement.EV3DEV_FAKE_SYSTEM_PATH, "..", "xrgb_render.raw");
            File path = Paths.get(BaseElement.EV3DEV_FAKE_SYSTEM_PATH, "..", "xrgb_render.png").toFile();
            ImageIO.write(img, "png", path);
            Files.write(raw, ImageUtils.getImageBytes(img),
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void renderBWImage() throws IOException {
        // intentional wider stride
        BufferedImage img = ImageUtils.createBWImage(width, height, bitStrideBig, true);
        Graphics2D gfx = img.createGraphics();
        gfx.setColor(Color.WHITE);
        gfx.fillRect(0, 0, width, height);

        BufferedImage logo = JarResource.loadImage(imageResource);
        gfx.drawImage(logo, 0, 0, null);
        gfx.dispose();

        try {
            Path raw = Paths.get(BaseElement.EV3DEV_FAKE_SYSTEM_PATH, "..", "bw_render.raw");
            File path = Paths.get(BaseElement.EV3DEV_FAKE_SYSTEM_PATH, "..", "bw_render.png").toFile();
            ImageIO.write(img, "png", path);
            Files.write(raw, ImageUtils.getImageBytes(img),
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void XRGBBufferLengthTest() {

        BufferedImage img = ImageUtils.createXRGBImage(width, height, xrgbStrideBig);

        assertEquals(ImageUtils.getImageBytes(img).length, xrgbStrideBig * height);
    }

    @Test
    public void BWBufferLengthTest() {

        BufferedImage img = ImageUtils.createBWImage(width, height, bitStrideBig, true);

        assertEquals(ImageUtils.getImageBytes(img).length, bitStrideBig * height);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidRGBStrideTest() {

        ImageUtils.createXRGBImage(width, height, xrgbStrideSmall);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidBWStrideTest() {

        ImageUtils.createBWImage(width, height, bitStrideSmall, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidRGBBufferTest() {

        byte[] data = new byte[0];
        ImageUtils.createXRGBImage(width, height, xrgbStrideNormal, data);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidBWBufferTest() {

        byte[] data = new byte[0];
        ImageUtils.createBWImage(width, height, bitStrideNormal, true, data);
    }
}
