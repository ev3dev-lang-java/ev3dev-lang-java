package ev3dev.hardware;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import ev3dev.utils.Sysfs;
import ev3dev.utils.io.DefaultLibc;
import ev3dev.utils.io.ILibc;
import ev3dev.utils.io.NativeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * This class been designed to discover if the library is used in:
 * <p>
 * - EV3 Brick
 * - Raspberry Pi 1 + PiStorms
 * - Raspberry Pi 1 + BrickPi
 * <p>
 * At the moment, the class extends from Device,
 * but close method doesn´t close any real resource.
 *
 * @author Juan Antonio Breña Moral
 */
public abstract class EV3DevDevice implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(EV3DevDevice.class);

    protected final Properties ev3DevProperties;
    protected final EV3DevPlatform CURRENT_PLATFORM;

    protected static final String LEGO_PORT = "lego-port";
    protected static final String ADDRESS = "address";
    protected static final String LEGO_SENSOR = "lego-sensor";
    protected static final String MODE = "mode";
    protected static final String DEVICE = "set_device";

    protected final int[] fds;
    protected final EV3DevAttributeSpec[] attrs;
    protected final ILibc libc;

    protected File PATH_DEVICE = null;

    /**
     * Constructor
     */
    public EV3DevDevice() {
        this(new EV3DevAttributeSpec[0]);
    }

    public EV3DevDevice(EV3DevAttributeSpec[] attrInfo) {
        this.fds = new int[attrInfo.length];
        Arrays.fill(this.fds, -1);
        this.attrs = attrInfo;
        this.libc = DefaultLibc.get();
        final EV3DevPropertyLoader ev3DevPropertyLoader = new EV3DevPropertyLoader();
        ev3DevProperties = ev3DevPropertyLoader.getEV3DevProperties();
        final EV3DevPlatforms ev3DevPlatforms = EV3DevPlatforms.getInstance();
        CURRENT_PLATFORM = ev3DevPlatforms.getPlatform();
    }

    //TODO Rename method to detect

    /**
     * This method matches a input with the internal position in EV3Dev.
     *
     * @param type     type
     * @param portName port
     */
    protected void detect(final String type, final String portName) {
        final String devicePath = EV3DevFileSystem.getRootPath() + "/" + type;
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Retrieving devices in path: ", devicePath);
        }
        final List<File> deviceAvailables = Sysfs.getElements(devicePath);
        boolean connected = false;
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Checking devices on: {}", devicePath);
        }
        String pathDeviceName;
        int deviceCounter = 1;
        for (File deviceAvailable : deviceAvailables) {
            PATH_DEVICE = deviceAvailable;
            pathDeviceName = PATH_DEVICE + "/" + ADDRESS;
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Device {}:", deviceCounter);
            }
            String result = Sysfs.readString(pathDeviceName);
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Port expected: {}, actual: {}.", portName, result);
            }
            //TODO Review to use equals. It is more strict
            if (result.contains(portName)) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Detected device on path: {}, {}", pathDeviceName, result);
                }
                connected = true;
                break;
            } else {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Skipped device");
                }
            }
            deviceCounter++;
        }

        if (!connected) {
            throw new RuntimeException("The device was not detected in: " + portName);
        }
    }


    @SuppressWarnings("OctalInteger")
    private synchronized int openAttr(int code) throws LastErrorException {
        if (this.fds[code] >= 0)
            return this.fds[code];

        EV3DevAttributeSpec spec = this.attrs[code];
        String path = PATH_DEVICE.toString() + "/" + spec.name;

        int fd;
        if (spec.access == EV3DevAttributeSpec.ACCESS_RO) {
            fd = libc.open(path, NativeConstants.O_RDONLY, 0644);
        } else if (spec.access == EV3DevAttributeSpec.ACCESS_WO) {
            fd = libc.open(path, NativeConstants.O_WRONLY | NativeConstants.O_CREAT, 0644);
        } else if (spec.access == EV3DevAttributeSpec.ACCESS_RW) {
            fd = libc.open(path, NativeConstants.O_RDWR | NativeConstants.O_CREAT, 0644);
        } else {
            throw new IllegalArgumentException("invalid access type");
        }
        if (fd < 0)
            throw new LastErrorException(Native.getLastError());

        this.fds[code] = fd;
        return fd;
    }

    private synchronized void closeAttr(int code) throws LastErrorException {
        if (this.fds[code] < 0)
            return;

        libc.close(this.fds[code]);
        this.fds[code] = -1;
    }

    public String readStringAttr(int code) {
        checkAttr(code);
        Sysfs.IoHelper io = Sysfs.getIoHelper();
        return io.decodeString(readRawAttr(code, io));
    }

    public void writeStringAttr(int code, String value) {
        checkAttr(code);
        Sysfs.IoHelper io = Sysfs.getIoHelper();
        writeRawAttr(code, io.encodeString(value));
    }

    public int readIntAttr(int code) {
        checkAttr(code);
        return Integer.parseInt(readStringAttr(code));
    }

    public void writeIntAttr(int code, int value) {
        checkAttr(code);
        writeStringAttr(code, Integer.toString(value));
    }

    public float readFloatAttr(int code) {
        checkAttr(code);
        return Float.parseFloat(readStringAttr(code));
    }

    public void writeFloatAttr(int code, float value) {
        checkAttr(code);
        writeStringAttr(code, Float.toString(value));
    }

    public ByteBuffer readRawAttr(int code) {
        checkAttr(code);
        Sysfs.IoHelper io = Sysfs.getIoHelper();
        return readRawAttr(code, io);
    }

    public synchronized ByteBuffer readRawAttr(int code, Sysfs.IoHelper io) {
        checkAttr(code);
        ByteBuffer buffer = io.getTemporaryByteBuffer(4096);
        int count = libc.pread(openAttr(code), buffer, buffer.remaining(), 0);
        if (count < 0)
            throw new LastErrorException(Native.getLastError());

        buffer.limit(count);
        return buffer;
    }

    private synchronized void writeRawAttr(int code, ByteBuffer buffer) {
        int count = libc.pwrite(openAttr(code), buffer, buffer.remaining(), 0);
        if (count < 0)
            throw new LastErrorException(Native.getLastError());
        buffer.position(buffer.position() + count);
    }

    private void checkAttr(int code) {
        if (code < 0 || code >= attrs.length)
            throw new IllegalArgumentException("Illegal attribute number: " + code);
    }

    public void close() {
        for (int attr = 0; attr < attrs.length; attr++) {
            closeAttr(attr);
        }
    }
}
