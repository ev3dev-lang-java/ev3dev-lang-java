package ev3dev;

import ev3dev.utils.Sysfs;
import ev3dev.utils.Sysfs2;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

public class Sysfs_writeInteger_Benchmark {

    @State(Scope.Thread)
    public static class St {
        String path = "/sys/class/leds/led0:green:brick-status/brightness";
    }

    @Benchmark
    public void SysfsOriginal(St state, Blackhole b) {
        b.consume(Sysfs.writeInteger(state.path, 255));
    }

    @Benchmark
    public void Sysfs2(St state, Blackhole b) {
        b.consume(Sysfs2.writeInteger(state.path, 255));
    }

}
