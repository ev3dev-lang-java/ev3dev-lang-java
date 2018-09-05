package fake_ev3dev.ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import ev3dev.utils.io.ILibc;

public interface IFile extends ILibc {
    int open(int fd, String path, int flags, int mode) throws LastErrorException;
}
