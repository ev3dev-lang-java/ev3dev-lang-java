package fake_ev3dev.ev3dev.utils.io;

import com.sun.jna.LastErrorException;
import lombok.NonNull;

public class SysfsLibc extends EmulatedLibc {
    @Override
    public int open(@NonNull String path, int flags, int mode) throws LastErrorException {
        if (!hasInstalled(path))
            install(path, new SysfsFile());
        return super.open(path, flags, mode);
    }
}
