package ev3dev.hardware;

import ev3dev.hardware.EV3DevDevice;
import ev3dev.hardware.EV3DevPlatform;
import ev3dev.utils.Sysfs;
import lejos.hardware.lcd.GraphicsLCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.EnumSet;

public class EV3DevScreenInfo {
    public enum Mode {
        BITPLANE, XRGB;
    }

    private String path;
    private Mode mode;
    private int width;
    private int height;
    private int stride;

    public EV3DevScreenInfo(String path, Mode mode, int w, int h, int s) {
        this.path = path;
        this.mode = mode;
        this.width = w;
        this.height = h;
        this.stride = s;
    }

    public String getKernelPath() {
        return path;
    }

    public void setKernelPath(String path) {
        this.path = path;
    }

    public Mode getKernelMode() {
        return mode;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStride() {
        return stride;
    }
}
