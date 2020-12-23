package ev3dev;

import ev3dev.utils.Sysfs;
import ev3dev.utils.Sysfs2;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

public class Sysfs_readString_Benchmark {

    @State(Scope.Thread)
    public static class St {
        String path = "/sys/class/power_supply/lego-ev3-battery/voltage_now";
    }

    @Benchmark
    public void SysfsOriginal(St state, Blackhole b) {
        b.consume(Sysfs.readString(state.path));
    }

    @Benchmark
    public void Sysfs2(St state, Blackhole b) {
        b.consume(Sysfs2.readString(state.path));
    }

}
