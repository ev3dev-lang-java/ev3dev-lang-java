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
        UNKNOWN, BITPLANE, XRGB;
    }

    private String path;
    private String sysfs;
    private Mode mode;
    private int width;
    private int height;
    private int stride;

    public EV3DevScreenInfo(String path, String sysfs, int w, int h, int s) {
        this.path = path;
        this.sysfs = sysfs;
        this.mode = Mode.UNKNOWN;
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

    public String getSysfsPath() {
        return sysfs;
    }

    public void setSysfsPath(String path) {
        this.sysfs = path;
    }

    public Mode getKernelMode() {
        return mode;
    }

    public void setKernelMode(Mode m) {
        this.mode = m;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getBitModeStride() {
        return stride;
    }
}
