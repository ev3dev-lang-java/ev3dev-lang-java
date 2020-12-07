package ev3dev;

import ev3dev.utils.Sysfs;
import ev3dev.utils.Sysfs2;
import ev3dev.utils.SysfsJNA;
import ev3dev.utils.SysfsJNA2;
import ev3dev.utils.SysfsJNA3;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class Sysfs_getElements_Benchmark {

    @State(Scope.Thread)
    public static class St {

    }

    @Benchmark
    public void SysfsOriginal(St state) {
        Sysfs.getElements("sys/class/lego-port");
    }

    @Benchmark
    public void Sysfs2(St state) {
        Sysfs2.getElements("sys/class/lego-port");
    }

    @Benchmark
    public void SysfsJNA(St state) {
        SysfsJNA.getElements("sys/class/lego-port");
    }

    @Benchmark
    public void SysfsJNA2(St state) {
        SysfsJNA2.getElements("sys/class/lego-port");
    }

    @Benchmark
    public void SysfsJNA3(St state) {
        SysfsJNA3.getElements("sys/class/lego-port");
    }
}
