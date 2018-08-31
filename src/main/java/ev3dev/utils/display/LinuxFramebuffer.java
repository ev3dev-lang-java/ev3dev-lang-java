package ev3dev.utils.display;

import com.sun.jna.Pointer;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOError;

/**
 * Linux Java2D framebuffer
 *
 * @since 2.4.7
 */
abstract class LinuxFramebuffer implements JavaFramebuffer, Closeable {
    /**
     * Underlying fixed framebuffer info.
     */
    private NativeFramebuffer.fb_fix_screeninfo fixinfo;
    /**
     * Underlying variable framebuffer info.
     */
    private NativeFramebuffer.fb_var_screeninfo varinfo;
    /**
     * Underlying native Linux device.
     */
    private NativeFramebuffer device;
    /**
     * Memory-mapped memory from Linux framebuffer device.
     */
    private Pointer videomem;

    /**
     * Create and initialize new Linux-based Java2D framebuffer.
     *
     * @param path Path to the framebuffer device (e.g. /dev/fb0)
     */
    public LinuxFramebuffer(String path) throws IOError {
        device = new NativeFramebuffer(path);
        fixinfo = device.getFixedScreenInfo();
        varinfo = device.getVariableScreenInfo();
        varinfo.xres_virtual = varinfo.xres;
        varinfo.yres_virtual = varinfo.yres;
        varinfo.xoffset = 0;
        varinfo.yoffset = 0;
        device.setVariableScreenInfo(varinfo);
        videomem = device.mmap(getBufferSize());
    }

    @Override
    public void close() {
        device.munmap(videomem, getBufferSize());
        device.close();
    }

    @Override
    public int getWidth() {
        return varinfo.xres;
    }

    @Override
    public int getHeight() {
        return varinfo.yres;
    }

    @Override
    public int getStride() {
        return fixinfo.line_length;
    }

    @Override
    public BufferedImage createCompatibleBuffer() {
        return createCompatibleBuffer(getWidth(), getHeight(), getFixedInfo().line_length);
    }

    @Override
    public abstract BufferedImage createCompatibleBuffer(int width, int height);

    @Override
    public BufferedImage createCompatibleBuffer(int width, int height, int stride) {
        return createCompatibleBuffer(width, height, stride, new byte[height * stride]);
    }

    @Override
    public abstract BufferedImage createCompatibleBuffer(int width, int height, int stride, byte[] backed);

    @Override
    public void flushScreen(BufferedImage compatible) {
        videomem.write(0, ImageUtils.getImageBytes(compatible), 0, (int) getBufferSize());
    }

    /**
     * Get Linux framebuffer fixed info.
     *
     * @return Fixed information about the framebuffer.
     */
    public NativeFramebuffer.fb_fix_screeninfo getFixedInfo() {
        return fixinfo;
    }

    /**
     * Get Linux framebuffer variable info.
     *
     * @return Variable information about the framebuffer.
     */
    public NativeFramebuffer.fb_var_screeninfo getVariableInfo() {
        return varinfo;
    }

    /**
     * Get the underlying native device.
     *
     * @return Linux device
     */
    public NativeFramebuffer getDevice() {
        return device;
    }

    /**
     * Get direct access to the video memory.
     *
     * @return JNA pointer to the framebuffer
     * @see LinuxFramebuffer#getBufferSize() for memory size.
     */
    public Pointer getMemory() {
        return videomem;
    }

    /**
     * Get video memory size.
     *
     * @return Size of video memory in bytes.
     * @see LinuxFramebuffer#getMemory() for memory pointer.
     */
    public long getBufferSize() {
        return getHeight() * getStride();
    }
}
