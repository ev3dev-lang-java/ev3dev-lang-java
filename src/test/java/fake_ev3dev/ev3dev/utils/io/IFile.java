package fake_ev3dev.ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import ev3dev.utils.io.ILibc;

/**
 * Emulated file interface
 *
 * @author Jakub Vaněk
 * @since 2.4.7
 */
public interface IFile extends ILibc {
    /**
     * Signal opening of a file on the path of this file.
     *
     * @param fd    Associated file descriptor number, provided by the EmulatedLibc class.
     * @param path  Path to the emulated file, provided by the tested code.
     * @param flags Opening flags, provided by the tested code.
     * @param mode  Opening mode, provided by the tested code.
     * @return File desciptor number {@code fd}.
     * @throws LastErrorException When an error is to be simulated.
     */
    int open(int fd, String path, int flags, int mode) throws LastErrorException;
}
