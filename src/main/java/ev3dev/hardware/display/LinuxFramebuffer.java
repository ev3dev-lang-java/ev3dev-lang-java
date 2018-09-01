package ev3dev.hardware.display;

import com.sun.jna.Pointer;
import ev3dev.utils.io.NativeFramebuffer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;

/**
 * <p>Linux Java2D framebuffer.</p>
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
     * Whether to enable display output.
     */
    private boolean flushEnabled;
    /**
     * Framebuffer backup for VT switches.
     */
    private byte[] backup;
    /**
     * Cache blank image.
     */
    private BufferedImage blank;

    /**
     * Create and initialize new Linux-based Java2D framebuffer.
     *
     * @param path Path to the framebuffer device (e.g. /dev/fb0)
     */
    public LinuxFramebuffer(String path) throws IOException {
        device = new NativeFramebuffer(path);
        fixinfo = device.getFixedScreenInfo();
        varinfo = device.getVariableScreenInfo();
        varinfo.xres_virtual = varinfo.xres;
        varinfo.yres_virtual = varinfo.yres;
        varinfo.xoffset = 0;
        varinfo.yoffset = 0;
        device.setVariableScreenInfo(varinfo);
        videomem = device.mmap(getBufferSize());
        backup = new byte[(int) getBufferSize()];
        blank = null;
        flushEnabled = false;
    }

    @Override
    public void close() throws IOException {
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
        if (flushEnabled) {
            videomem.write(0, ImageUtils.getImageBytes(compatible), 0, (int) getBufferSize());
        }
    }

    @Override
    public void setFlushEnabled(boolean rly) {
        this.flushEnabled = rly;
    }

    @Override
    public void storeData() {
        videomem.read(0, backup, 0, (int) getBufferSize());
    }

    @Override
    public void restoreData() {
        videomem.write(0, backup, 0, (int) getBufferSize());
    }

    @Override
    public void clear() {
        if (blank == null) {
            blank = createCompatibleBuffer();
            Graphics2D gfx = blank.createGraphics();
            gfx.setColor(Color.WHITE);
            gfx.fillRect(0, 0, getWidth(), getHeight());
            gfx.dispose();
        }
        flushScreen(blank);
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
