package ev3dev;

import ev3dev.utils.Sysfs;
import ev3dev.utils.Sysfs2;
import ev3dev.utils.SysfsNIO;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

public class Sysfs_readInteger_Benchmark {

    @State(Scope.Thread)
    public static class St {
        //final EV3DevPropertyLoader ev3DevPropertyLoader = new EV3DevPropertyLoader();
        //final Properties ev3DevProperties = ev3DevPropertyLoader.getEV3DevProperties();

        //final String KEY = "ev3.led.left.red";
    }

    @Benchmark
    public void SysfsOriginal(St state, Blackhole b) {
        b.consume(Sysfs.readInteger("/sys/class/leds/led0:green:brick-status/brightness"));
    }

    @Benchmark
    public void Sysfs2(St state, Blackhole b) {
        b.consume(Sysfs2.readInteger("/sys/class/leds/led0:green:brick-status/brightness"));
    }

    @Benchmark
    public void SysfsNIO(St state, Blackhole b) {
        b.consume(SysfsNIO.readInteger("/sys/class/leds/led0:green:brick-status/brightness"));
    }
}
