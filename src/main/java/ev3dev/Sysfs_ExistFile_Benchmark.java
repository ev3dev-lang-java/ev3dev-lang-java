package ev3dev;

import ev3dev.hardware.EV3DevPropertyLoader;
import ev3dev.utils.Sysfs;
import ev3dev.utils.Sysfs2;
import ev3dev.utils.SysfsNIO;
import java.nio.file.Path;
import java.util.Properties;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class Sysfs_ExistFile_Benchmark {

    @State(Scope.Thread)
    public static class St {
        final EV3DevPropertyLoader ev3DevPropertyLoader = new EV3DevPropertyLoader();
        final Properties ev3DevProperties = ev3DevPropertyLoader.getEV3DevProperties();

        final String KEY = "ev3.led.left.red";
    }

    @Benchmark
    public void SysfsOriginal(St state) {
        Sysfs.existFile(Path.of(state.ev3DevProperties.getProperty(state.KEY)));
    }

    @Benchmark
    public void Sysfs2(St state) {
        Sysfs2.existFile(Path.of(state.ev3DevProperties.getProperty(state.KEY)));
    }

    @Benchmark
    public void SysfsNIO(St state) {
        SysfsNIO.existFile(Path.of(state.ev3DevProperties.getProperty(state.KEY)));
    }
}
