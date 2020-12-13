package ev3dev;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.openjdk.jmh.runner.options.VerboseMode;

public class SysfsBenchmarkProgram {

    public static void main(String[] args) throws Exception {

        Options options = new OptionsBuilder()
                .include(Sysfs_ExistFile_Benchmark.class.getSimpleName())
                .include(Sysfs_writeString_Benchmark.class.getSimpleName())
                .include(Sysfs_writeInteger_Benchmark.class.getSimpleName())
                .include(Sysfs_readString_Benchmark.class.getSimpleName())
                .include(Sysfs_readInteger_Benchmark.class.getSimpleName())
                .include(Sysfs_getElements_Benchmark.class.getSimpleName())
                .include(Sysfs_existPath_Benchmark.class.getSimpleName())

                .resultFormat(ResultFormatType.JSON)
                .result("/home/robot/jmh-results.json")
                .verbosity(VerboseMode.EXTRA)
                .timeUnit(TimeUnit.MILLISECONDS)
                .mode(Mode.SingleShotTime)
                .warmupTime(TimeValue.seconds(2))
                .warmupIterations(10)
                .measurementTime(TimeValue.seconds(2))
                .measurementIterations(50)
            /* alternative measuring mode:
                .mode(Mode.AverageTime)
                .warmupTime(TimeValue.seconds(2))
                .warmupIterations(2)
                .measurementTime(TimeValue.seconds(2))
                .measurementIterations(50)
             */
                .threads(1)
                .shouldFailOnError(false)
                .shouldDoGC(true)
                .forks(2)
                .jvmArgs("-Xint", "-Xmx32m", "-Xms32m", "-XX:+UseSerialGC", "-noverify")
                //.addProfiler(StackProfiler.class)
                //.addProfiler(GCProfiler.class)
                //.addProfiler(LinuxPerfProfiler.class)
                //.addProfiler(ClassloaderProfiler.class)
                //.addProfiler(CompilerProfiler.class)
                //.addProfiler(JmhFlightRecorderProfiler.class)
                .build();

        new Runner(options).run();
    }
}
