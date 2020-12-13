package ev3dev;

import ev3dev.utils.Sysfs;
import ev3dev.utils.Sysfs2;
import ev3dev.utils.SysfsNIO;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

public class Sysfs_existPath_Benchmark {

    @State(Scope.Thread)
    public static class St {

    }

    @Benchmark
    public void SysfsOriginal(St state, Blackhole b) {
        b.consume(Sysfs.existPath("/sys/class/lego-port"));
    }

    @Benchmark
    public void Sysfs2(St state, Blackhole b) {
        b.consume(Sysfs2.existPath("/sys/class/lego-port"));
    }

    @Benchmark
    public void SysfsNIO(St state, Blackhole b) {
        b.consume(SysfsNIO.existPath("/sys/class/lego-port"));
    }
}
