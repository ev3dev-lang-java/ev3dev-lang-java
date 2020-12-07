package ev3dev;

import ev3dev.utils.Sysfs;
import ev3dev.utils.Sysfs2;
import ev3dev.utils.SysfsJNA;
import ev3dev.utils.SysfsJNA2;
import ev3dev.utils.SysfsJNA3;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class Sysfs_readString_Benchmark {

    @State(Scope.Thread)
    public static class St {
        //final EV3DevPropertyLoader ev3DevPropertyLoader = new EV3DevPropertyLoader();
        //final Properties ev3DevProperties = ev3DevPropertyLoader.getEV3DevProperties();

        //final String KEY = "ev3.led.left.red";
    }

    @Benchmark
    public void SysfsOriginal(St state) {
        Sysfs.readString("/sys/class/power_supply/lego-ev3-battery/voltage_now");
    }

    @Benchmark
    public void Sysfs2(St state) {
        Sysfs2.readString("/sys/class/power_supply/lego-ev3-battery/voltage_now");
    }

    @Benchmark
    public void SysfsJNA(St state) {
        SysfsJNA.readString("/sys/class/power_supply/lego-ev3-battery/voltage_now");
    }

    @Benchmark
    public void SysfsJNA2(St state) {
        SysfsJNA2.readString("/sys/class/power_supply/lego-ev3-battery/voltage_now");
    }

    @Benchmark
    public void SysfsJNA3(St state) {
        SysfsJNA3.readString("/sys/class/power_supply/lego-ev3-battery/voltage_now");
    }
}
