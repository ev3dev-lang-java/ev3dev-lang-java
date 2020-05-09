package ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import com.sun.jna.Pointer;
import lombok.NonNull;

import static ev3dev.utils.io.NativeConstants.MAP_SHARED;
import static ev3dev.utils.io.NativeConstants.O_RDWR;
import static ev3dev.utils.io.NativeConstants.PROT_READ;
import static ev3dev.utils.io.NativeConstants.PROT_WRITE;

/**
 * <p>This class provides access from Java to Linux character devices. It is intended
 * to allow access from Java to the Lego kernel modules which provide access to
 * EV3 hardware features.</p>
 *
 * @author andy, Jakub Vaněk
 */
public class NativeDevice extends NativeFile {
    /**
     * Create a native device to provide access to the specified character device
     *
     * @param dname name of the character device
     */
    public NativeDevice(@NonNull String dname) throws LastErrorException {
        super(dname, O_RDWR, 0);
    }

    /**
     * Create a native device to provide access to the specified character device
     *
     * @param flags opening flags of the device - read, write or both
     * @param dname name of the character device
     */
    public NativeDevice(@NonNull String dname, int flags) throws LastErrorException {
        super(dname, flags);
    }

    /**
     * Create a native device to provide access to the specified character device
     *
     * @param dname name of the character device
     * @param libc  standard C library interface to be used.
     */
    public NativeDevice(@NonNull String dname, @NonNull ILibc libc) throws LastErrorException {
        super(dname, O_RDWR, 0, libc);
    }

    /**
     * Create a native device to provide access to the specified character device
     *
     * @param flags opening flags of the device - read, write or both
     * @param dname name of the character device
     * @param libc  standard C library interface to be used.
     */
    public NativeDevice(@NonNull String dname, int flags, @NonNull ILibc libc) throws LastErrorException {
        super(dname, flags, libc);
    }

    /**
     * Map a portion of the device into memory and return a pointer which can be
     * used to read/write the device.
     *
     * @param len number of bytes to map
     * @return a pointer that can be used to access the device memory
     */
    public Pointer mmap(long len) throws LastErrorException {
        return super.mmap(len, PROT_READ | PROT_WRITE, MAP_SHARED, 0);
    }
}
