package ev3dev.hardware.display;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Common image utilities for framebuffer manipulation
 *
 * @since 2.4.7
 */
@Slf4j
public class ImageUtils {

    /**
     * Create new ev3dev-compatible XRGB image.
     *
     * @param w Image width
     * @param h Image height
     * @return Configured BufferedImage
     */
    public static BufferedImage createXRGBImage(int w, int h) {
        return createXRGBImage(w, h, w * 4);
    }

    /**
     * Create new XRGB image.
     *
     * @param w      Image width
     * @param h      Image height
     * @param stride Image scanline stride, i.e. how long the row is in bytes.
     * @return Configured BufferedImage
     */
    public static BufferedImage createXRGBImage(int w, int h, int stride) {
        return createXRGBImage(w, h, stride, new byte[stride * h]);
    }

    /**
     * Create new XRGB image.
     *
     * @param width  Image width
     * @param height Image height
     * @param stride Image scanline stride, i.e. how long the row is in bytes.
     * @param buffer Backing buffer.
     * @return Configured BufferedImage
     */
    public static BufferedImage createXRGBImage(int width, int height, int stride, @NonNull byte[] buffer) {
        return createXRGBImage(width, height, stride, getDefaultComponentOffsets(), buffer);
    }

    /**
     * Create new XRGB image.
     *
     * @param width   Image width
     * @param height  Image height
     * @param stride  Image scanline stride, i.e. how long the row is in bytes.
     * @param offsets Array of size 4 describing the offsets of color bands: { R, G, B, A }
     * @param buffer  Backing buffer.
     * @return Configured BufferedImage
     */
    public static BufferedImage createXRGBImage(
        int width, int height, int stride, int[] offsets, @NonNull byte[] buffer) {

        LOGGER.trace("creating XRGB image {}x{} with stride={}", width, height, stride);
        if (buffer.length < (stride * height)) {
            throw new IllegalArgumentException("Buffer is smaller than height*stride");
        }
        if (stride < width * 4) {
            throw new IllegalArgumentException("Stride is smaller than width * 4");
        }
        DataBuffer db = new DataBufferByte(buffer, buffer.length);

        // initialize buffer <-> samples bridge
        PixelInterleavedSampleModel sm = new PixelInterleavedSampleModel(
                DataBuffer.TYPE_BYTE, width, height,
                4, stride, offsets);

        // initialize color interpreter
        ColorSpace spc = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        ComponentColorModel cm = new ComponentColorModel(spc, true, false,
                Transparency.OPAQUE, DataBuffer.TYPE_BYTE);

        // create raster
        WritableRaster wr = Raster.createWritableRaster(sm, db, null);

        // glue everything together
        return new BufferedImage(cm, wr, false, null);
    }

    /**
     * Get default XRGB component offsets.
     *
     * @return Offsets: { R, G, B, A }
     */
    public static int[] getDefaultComponentOffsets() {
        return new int[]{2, 1, 0, 3};
    }

    /**
     * Create new BW image.
     *
     * @param width     Image width.
     * @param height    Image height.
     * @param zeroBlack Whether black color is represented by the 0 bit value.
     * @return Configured BufferedImage.
     */
    public static BufferedImage createBWImage(int width, int height, boolean zeroBlack) {
        int stride = (width + 7) / 8;
        return createBWImage(width, height, stride, zeroBlack, new byte[stride * height]);
    }

    /**
     * Create new BW image backed by existing data.
     *
     * @param width     Image width.
     * @param height    Image height.
     * @param zeroBlack Whether black color is represented by the 0 bit value.
     * @param backed    Backing byte buffer.
     * @return Configured BufferedImage.
     */
    public static BufferedImage createBWImage(int width, int height, boolean zeroBlack, @NonNull byte[] backed) {
        int stride = (width + 7) / 8;
        return createBWImage(width, height, stride, zeroBlack, backed);
    }


    /**
     * Create new BW image.
     *
     * @param width     Image width.
     * @param height    Image height.
     * @param stride    Image scanline stride, i.e. how long the row is in bytes.
     * @param zeroBlack Whether black color is represented by the 0 bit value.
     * @return Configured BufferedImage.
     */
    public static BufferedImage createBWImage(int width, int height, int stride, boolean zeroBlack) {
        return createBWImage(width, height, stride, zeroBlack, new byte[stride * height]);
    }

    /**
     * Create new BW image backed by existing data.
     *
     * @param width     Image width.
     * @param height    Image height.
     * @param stride    Image scanline stride, i.e. how long the row is in bytes.
     * @param zeroBlack Whether black color is represented by the 0 bit value.
     * @param backed    Backing byte buffer.
     * @return Configured BufferedImage.
     */
    public static BufferedImage createBWImage(
        int width, int height, int stride, boolean zeroBlack, @NonNull byte[] backed) {

        LOGGER.trace("creating BW image {}x{} with stride={}", width, height, stride);
        if (backed.length < (stride * height)) {
            throw new IllegalArgumentException("Buffer is smaller than height*stride");
        }
        if (stride < width / 8) {
            throw new IllegalArgumentException("Stride is smaller than width/8");
        }
        // initialize backing store
        DataBuffer db = new DataBufferByte(backed, backed.length);

        // initialize buffer <-> sample mapping
        MultiPixelPackedSampleModel packing =
                new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE,
                        width, height,
                        1, stride, 0);

        // initialize raster
        WritableRaster wr = Raster.createWritableRaster(packing, db, null);

        // initialize color interpreter
        byte[] mapPixels;
        if (zeroBlack) {
            mapPixels = new byte[]{(byte) 0x00, (byte) 0xFF};
        } else {
            mapPixels = new byte[]{(byte) 0xFF, (byte) 0x00};
        }
        IndexColorModel cm = new IndexColorModel(1, mapPixels.length, mapPixels, mapPixels, mapPixels);

        // glue everything together
        return new BufferedImage(cm, wr, false, null);
    }

    /**
     * Convert image to the underlying byte buffer.
     *
     * @param image Configured BufferedImage.
     * @return Byte array.
     */
    public static byte[] getImageBytes(@NonNull BufferedImage image) {
        WritableRaster rst = image.getRaster();
        DataBuffer buf = rst.getDataBuffer();
        return ((DataBufferByte) buf).getData();
    }
}
