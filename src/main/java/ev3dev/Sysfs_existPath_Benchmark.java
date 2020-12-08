package ev3dev;

import ev3dev.utils.Sysfs;
import ev3dev.utils.Sysfs2;
import ev3dev.utils.SysfsNIO;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class Sysfs_existPath_Benchmark {

    @State(Scope.Thread)
    public static class St {

    }

    @Benchmark
    public void SysfsOriginal(St state) {
        Sysfs.existPath("sys/class/lego-port");
    }

    @Benchmark
    public void Sysfs2(St state) {
        Sysfs2.existPath("sys/class/lego-port");
    }

    @Benchmark
    public void SysfsNIO(St state) {
        SysfsNIO.existPath("sys/class/lego-port");
    }
}
