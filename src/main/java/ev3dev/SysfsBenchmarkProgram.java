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
                .mode(Mode.SingleShotTime)
                .timeUnit(TimeUnit.MILLISECONDS)
                .warmupTime(TimeValue.seconds(5))
                .measurementTime(TimeValue.milliseconds(1))
                .measurementIterations(10)
                .threads(Runtime.getRuntime().availableProcessors())
                .warmupIterations(1)
                .shouldFailOnError(false)
                .shouldDoGC(true)
                .forks(2)
                .jvmArgs("-Xmx64m", "-Xms64m", "-XX:+UseSerialGC", "-noverify")
                .addProfiler(StackProfiler.class)
                .addProfiler(GCProfiler.class)
                //.addProfiler(LinuxPerfProfiler.class)
                //.addProfiler(ClassloaderProfiler.class)
                //.addProfiler(CompilerProfiler.class)
                //.addProfiler(JmhFlightRecorderProfiler.class)
                .build();

        new Runner(options).run();
    }

}

/**
 * Benchmark                                   Mode  Cnt   Score    Error  Units
 * Sysfs_ExistFile_Benchmark.Sysfs2            avgt   10  15.592 ? 27.924  ms/op
 * Sysfs_ExistFile_Benchmark.SysfsJNA          avgt   10  12.340 ?  8.758  ms/op
 * Sysfs_ExistFile_Benchmark.SysfsJNA2         avgt   10  10.810 ?  7.733  ms/op
 * Sysfs_ExistFile_Benchmark.SysfsJNA3         avgt   10   8.605 ?  3.080  ms/op
 * Sysfs_ExistFile_Benchmark.SysfsOriginal     avgt   10  10.002 ? 20.725  ms/op
 * Sysfs_existPath_Benchmark.Sysfs2            avgt   10  49.480 ? 46.960  ms/op
 * Sysfs_existPath_Benchmark.SysfsJNA          avgt   10  46.822 ? 13.254  ms/op
 * Sysfs_existPath_Benchmark.SysfsJNA2         avgt   10  42.022 ? 19.543  ms/op
 * Sysfs_existPath_Benchmark.SysfsOriginal     avgt   10  42.520 ? 27.847  ms/op
 * Sysfs_readString_Benchmark.SysfsJNA         avgt   10  69.320 ? 22.873  ms/op
 * Sysfs_readString_Benchmark.SysfsJNA2        avgt   10  83.479 ? 36.463  ms/op
 * Sysfs_readString_Benchmark.SysfsJNA3        avgt   10  89.231 ? 58.068  ms/op
 * Sysfs_readString_Benchmark.SysfsOriginal    avgt   10  78.398 ? 16.224  ms/op
 * Sysfs_writeInteger_Benchmark.Sysfs2         avgt   10  44.641 ? 31.890  ms/op
 * Sysfs_writeInteger_Benchmark.SysfsJNA       avgt   10  38.574 ? 12.712  ms/op
 * Sysfs_writeInteger_Benchmark.SysfsJNA2      avgt   10  36.952 ?  9.491  ms/op
 * Sysfs_writeInteger_Benchmark.SysfsJNA3      avgt   10  37.658 ? 13.754  ms/op
 * Sysfs_writeInteger_Benchmark.SysfsOriginal  avgt   10  41.258 ? 11.446  ms/op
 * Sysfs_writeString_Benchmark.Sysfs2          avgt   10  42.764 ? 17.031  ms/op
 * Sysfs_writeString_Benchmark.SysfsJNA        avgt   10  34.404 ?  6.775  ms/op
 * Sysfs_writeString_Benchmark.SysfsJNA2       avgt   10  37.501 ?  6.310  ms/op
 * Sysfs_writeString_Benchmark.SysfsJNA3       avgt   10  36.704 ? 30.766  ms/op
 * Sysfs_writeString_Benchmark.SysfsOriginal   avgt   10  37.589 ? 11.072  ms/op
 */

/**
 Benchmark                                                  Mode  Cnt    Score     Error   Units
 Sysfs_ExistFile_Benchmark.Sysfs2                           avgt   20    8.876 ?   7.415   ms/op
 Sysfs_ExistFile_Benchmark.Sysfs2:?gc.alloc.rate            avgt   20      NaN            MB/sec
 Sysfs_ExistFile_Benchmark.Sysfs2:?gc.count                 avgt   20      ? 0            counts
 Sysfs_ExistFile_Benchmark.Sysfs2:?stack                    avgt           NaN               ---
 Sysfs_ExistFile_Benchmark.SysfsJNA                         avgt   20   17.467 ?  14.597   ms/op
 Sysfs_ExistFile_Benchmark.SysfsJNA:?gc.alloc.rate          avgt   20      NaN            MB/sec
 Sysfs_ExistFile_Benchmark.SysfsJNA:?gc.count               avgt   20      ? 0            counts
 Sysfs_ExistFile_Benchmark.SysfsJNA:?stack                  avgt           NaN               ---
 Sysfs_ExistFile_Benchmark.SysfsJNA2                        avgt   20   19.286 ?  12.194   ms/op
 Sysfs_ExistFile_Benchmark.SysfsJNA2:?gc.alloc.rate         avgt   20      NaN            MB/sec
 Sysfs_ExistFile_Benchmark.SysfsJNA2:?gc.count              avgt   20      ? 0            counts
 Sysfs_ExistFile_Benchmark.SysfsJNA2:?stack                 avgt           NaN               ---
 Sysfs_ExistFile_Benchmark.SysfsJNA3                        avgt   20   21.299 ?  25.011   ms/op
 Sysfs_ExistFile_Benchmark.SysfsJNA3:?gc.alloc.rate         avgt   20      NaN            MB/sec
 Sysfs_ExistFile_Benchmark.SysfsJNA3:?gc.count              avgt   20      ? 0            counts
 Sysfs_ExistFile_Benchmark.SysfsJNA3:?stack                 avgt           NaN               ---
 Sysfs_ExistFile_Benchmark.SysfsOriginal                    avgt   20   12.838 ?  15.249   ms/op
 Sysfs_ExistFile_Benchmark.SysfsOriginal:?gc.alloc.rate     avgt   20      NaN            MB/sec
 Sysfs_ExistFile_Benchmark.SysfsOriginal:?gc.count          avgt   20      ? 0            counts
 Sysfs_ExistFile_Benchmark.SysfsOriginal:?stack             avgt           NaN               ---
 Sysfs_existPath_Benchmark.Sysfs2                           avgt   20   46.367 ?   8.729   ms/op
 Sysfs_existPath_Benchmark.Sysfs2:?gc.alloc.rate            avgt   20      NaN            MB/sec
 Sysfs_existPath_Benchmark.Sysfs2:?gc.count                 avgt   20      ? 0            counts
 Sysfs_existPath_Benchmark.Sysfs2:?stack                    avgt           NaN               ---
 Sysfs_existPath_Benchmark.SysfsJNA                         avgt   20   81.047 ?  67.046   ms/op
 Sysfs_existPath_Benchmark.SysfsJNA:?gc.alloc.rate          avgt   20      NaN            MB/sec
 Sysfs_existPath_Benchmark.SysfsJNA:?gc.count               avgt   20      ? 0            counts
 Sysfs_existPath_Benchmark.SysfsJNA:?stack                  avgt           NaN               ---
 Sysfs_existPath_Benchmark.SysfsJNA2                        avgt   20   77.890 ?  60.135   ms/op
 Sysfs_existPath_Benchmark.SysfsJNA2:?gc.alloc.rate         avgt   20      NaN            MB/sec
 Sysfs_existPath_Benchmark.SysfsJNA2:?gc.count              avgt   20      ? 0            counts
 Sysfs_existPath_Benchmark.SysfsJNA2:?stack                 avgt           NaN               ---
 Sysfs_existPath_Benchmark.SysfsOriginal                    avgt   20   52.415 ?  16.031   ms/op
 Sysfs_existPath_Benchmark.SysfsOriginal:?gc.alloc.rate     avgt   20      NaN            MB/sec
 Sysfs_existPath_Benchmark.SysfsOriginal:?gc.count          avgt   20      ? 0            counts
 Sysfs_existPath_Benchmark.SysfsOriginal:?stack             avgt           NaN               ---
 Sysfs_readString_Benchmark.SysfsJNA                        avgt   20  130.497 ?  64.471   ms/op
 Sysfs_readString_Benchmark.SysfsJNA:?gc.alloc.rate         avgt   20      NaN            MB/sec
 Sysfs_readString_Benchmark.SysfsJNA:?gc.count              avgt   20      ? 0            counts
 Sysfs_readString_Benchmark.SysfsJNA:?stack                 avgt           NaN               ---
 Sysfs_readString_Benchmark.SysfsJNA2                       avgt   20  147.819 ? 130.883   ms/op
 Sysfs_readString_Benchmark.SysfsJNA2:?gc.alloc.rate        avgt   20      NaN            MB/sec
 Sysfs_readString_Benchmark.SysfsJNA2:?gc.count             avgt   20      ? 0            counts
 Sysfs_readString_Benchmark.SysfsJNA2:?stack                avgt           NaN               ---
 Sysfs_readString_Benchmark.SysfsJNA3                       avgt   20  135.971 ? 106.608   ms/op
 Sysfs_readString_Benchmark.SysfsJNA3:?gc.alloc.rate        avgt   20      NaN            MB/sec
 Sysfs_readString_Benchmark.SysfsJNA3:?gc.count             avgt   20      ? 0            counts
 Sysfs_readString_Benchmark.SysfsJNA3:?stack                avgt           NaN               ---
 Sysfs_readString_Benchmark.SysfsOriginal                   avgt   20  130.898 ?  37.657   ms/op
 Sysfs_readString_Benchmark.SysfsOriginal:?gc.alloc.rate    avgt   20      NaN            MB/sec
 Sysfs_readString_Benchmark.SysfsOriginal:?gc.count         avgt   20      ? 0            counts
 Sysfs_readString_Benchmark.SysfsOriginal:?stack            avgt           NaN               ---
 Sysfs_writeInteger_Benchmark.Sysfs2                        avgt   20   71.374 ?  43.235   ms/op
 Sysfs_writeInteger_Benchmark.Sysfs2:?gc.alloc.rate         avgt   20      NaN            MB/sec
 Sysfs_writeInteger_Benchmark.Sysfs2:?gc.count              avgt   20      ? 0            counts
 Sysfs_writeInteger_Benchmark.Sysfs2:?stack                 avgt           NaN               ---
 Sysfs_writeInteger_Benchmark.SysfsJNA                      avgt   20  101.911 ?  76.781   ms/op
 Sysfs_writeInteger_Benchmark.SysfsJNA:?gc.alloc.rate       avgt   20      NaN            MB/sec
 Sysfs_writeInteger_Benchmark.SysfsJNA:?gc.count            avgt   20      ? 0            counts
 Sysfs_writeInteger_Benchmark.SysfsJNA:?stack               avgt           NaN               ---
 Sysfs_writeInteger_Benchmark.SysfsJNA2                     avgt   20   95.995 ?  85.353   ms/op
 Sysfs_writeInteger_Benchmark.SysfsJNA2:?gc.alloc.rate      avgt   20      NaN            MB/sec
 Sysfs_writeInteger_Benchmark.SysfsJNA2:?gc.count           avgt   20      ? 0            counts
 Sysfs_writeInteger_Benchmark.SysfsJNA2:?stack              avgt           NaN               ---
 Sysfs_writeInteger_Benchmark.SysfsJNA3                     avgt   20  104.898 ? 134.129   ms/op
 Sysfs_writeInteger_Benchmark.SysfsJNA3:?gc.alloc.rate      avgt   20      NaN            MB/sec
 Sysfs_writeInteger_Benchmark.SysfsJNA3:?gc.count           avgt   20      ? 0            counts
 Sysfs_writeInteger_Benchmark.SysfsJNA3:?stack              avgt           NaN               ---
 Sysfs_writeInteger_Benchmark.SysfsOriginal                 avgt   20   69.563 ?  29.926   ms/op
 Sysfs_writeInteger_Benchmark.SysfsOriginal:?gc.alloc.rate  avgt   20      NaN            MB/sec
 Sysfs_writeInteger_Benchmark.SysfsOriginal:?gc.count       avgt   20      ? 0            counts
 Sysfs_writeInteger_Benchmark.SysfsOriginal:?stack          avgt           NaN               ---
 Sysfs_writeString_Benchmark.Sysfs2                         avgt   20   63.268 ?  20.395   ms/op
 Sysfs_writeString_Benchmark.Sysfs2:?gc.alloc.rate          avgt   20      NaN            MB/sec
 Sysfs_writeString_Benchmark.Sysfs2:?gc.count               avgt   20      ? 0            counts
 Sysfs_writeString_Benchmark.Sysfs2:?stack                  avgt           NaN               ---
 Sysfs_writeString_Benchmark.SysfsJNA                       avgt   20   77.667 ?  59.439   ms/op
 Sysfs_writeString_Benchmark.SysfsJNA:?gc.alloc.rate        avgt   20      NaN            MB/sec
 Sysfs_writeString_Benchmark.SysfsJNA:?gc.count             avgt   20      ? 0            counts
 Sysfs_writeString_Benchmark.SysfsJNA:?stack                avgt           NaN               ---
 Sysfs_writeString_Benchmark.SysfsJNA2                      avgt   20   99.891 ?  70.904   ms/op
 Sysfs_writeString_Benchmark.SysfsJNA2:?gc.alloc.rate       avgt   20      NaN            MB/sec
 Sysfs_writeString_Benchmark.SysfsJNA2:?gc.count            avgt   20      ? 0            counts
 Sysfs_writeString_Benchmark.SysfsJNA2:?stack               avgt           NaN               ---
 Sysfs_writeString_Benchmark.SysfsJNA3                      avgt   20   82.663 ?  88.105   ms/op
 Sysfs_writeString_Benchmark.SysfsJNA3:?gc.alloc.rate       avgt   20      NaN            MB/sec
 Sysfs_writeString_Benchmark.SysfsJNA3:?gc.count            avgt   20      ? 0            counts
 Sysfs_writeString_Benchmark.SysfsJNA3:?stack               avgt           NaN               ---
 Sysfs_writeString_Benchmark.SysfsOriginal                  avgt   20   60.243 ?  16.181   ms/op
 Sysfs_writeString_Benchmark.SysfsOriginal:?gc.alloc.rate   avgt   20      NaN            MB/sec
 Sysfs_writeString_Benchmark.SysfsOriginal:?gc.count        avgt   20      ? 0            counts
 Sysfs_writeString_Benchmark.SysfsOriginal:?stack           avgt           NaN               ---

 */

/**
 * Benchmark                                                   Mode  Cnt  Score   Error   Units
 * Sysfs_ExistFile_Benchmark.Sysfs2                           thrpt   20  0.121 ? 0.057  ops/ms
 * Sysfs_ExistFile_Benchmark.Sysfs2:?gc.alloc.rate            thrpt   20    NaN          MB/sec
 * Sysfs_ExistFile_Benchmark.Sysfs2:?gc.count                 thrpt   20    ? 0          counts
 * Sysfs_ExistFile_Benchmark.Sysfs2:?stack                    thrpt         NaN             ---
 * Sysfs_ExistFile_Benchmark.SysfsJNA                         thrpt   20  0.088 ? 0.042  ops/ms
 * Sysfs_ExistFile_Benchmark.SysfsJNA:?gc.alloc.rate          thrpt   20    NaN          MB/sec
 * Sysfs_ExistFile_Benchmark.SysfsJNA:?gc.count               thrpt   20    ? 0          counts
 * Sysfs_ExistFile_Benchmark.SysfsJNA:?stack                  thrpt         NaN             ---
 * Sysfs_ExistFile_Benchmark.SysfsJNA2                        thrpt   20  0.079 ? 0.040  ops/ms
 * Sysfs_ExistFile_Benchmark.SysfsJNA2:?gc.alloc.rate         thrpt   20    NaN          MB/sec
 * Sysfs_ExistFile_Benchmark.SysfsJNA2:?gc.count              thrpt   20    ? 0          counts
 * Sysfs_ExistFile_Benchmark.SysfsJNA2:?stack                 thrpt         NaN             ---
 * Sysfs_ExistFile_Benchmark.SysfsJNA3                        thrpt   20  0.085 ? 0.028  ops/ms
 * Sysfs_ExistFile_Benchmark.SysfsJNA3:?gc.alloc.rate         thrpt   20    NaN          MB/sec
 * Sysfs_ExistFile_Benchmark.SysfsJNA3:?gc.count              thrpt   20    ? 0          counts
 * Sysfs_ExistFile_Benchmark.SysfsJNA3:?stack                 thrpt         NaN             ---
 * Sysfs_ExistFile_Benchmark.SysfsOriginal                    thrpt   20  0.123 ? 0.052  ops/ms
 * Sysfs_ExistFile_Benchmark.SysfsOriginal:?gc.alloc.rate     thrpt   20    NaN          MB/sec
 * Sysfs_ExistFile_Benchmark.SysfsOriginal:?gc.count          thrpt   20    ? 0          counts
 * Sysfs_ExistFile_Benchmark.SysfsOriginal:?stack             thrpt         NaN             ---
 * Sysfs_existPath_Benchmark.Sysfs2                           thrpt   20  0.024 ? 0.004  ops/ms
 * Sysfs_existPath_Benchmark.Sysfs2:?gc.alloc.rate            thrpt   20    NaN          MB/sec
 * Sysfs_existPath_Benchmark.Sysfs2:?gc.count                 thrpt   20    ? 0          counts
 * Sysfs_existPath_Benchmark.Sysfs2:?stack                    thrpt         NaN             ---
 * Sysfs_existPath_Benchmark.SysfsJNA                         thrpt   20  0.022 ? 0.007  ops/ms
 * Sysfs_existPath_Benchmark.SysfsJNA:?gc.alloc.rate          thrpt   20    NaN          MB/sec
 * Sysfs_existPath_Benchmark.SysfsJNA:?gc.count               thrpt   20    ? 0          counts
 * Sysfs_existPath_Benchmark.SysfsJNA:?stack                  thrpt         NaN             ---
 * Sysfs_existPath_Benchmark.SysfsJNA2                        thrpt   20  0.022 ? 0.008  ops/ms
 * Sysfs_existPath_Benchmark.SysfsJNA2:?gc.alloc.rate         thrpt   20    NaN          MB/sec
 * Sysfs_existPath_Benchmark.SysfsJNA2:?gc.count              thrpt   20    ? 0          counts
 * Sysfs_existPath_Benchmark.SysfsJNA2:?stack                 thrpt         NaN             ---
 * Sysfs_existPath_Benchmark.SysfsOriginal                    thrpt   20  0.021 ? 0.005  ops/ms
 * Sysfs_existPath_Benchmark.SysfsOriginal:?gc.alloc.rate     thrpt   20    NaN          MB/sec
 * Sysfs_existPath_Benchmark.SysfsOriginal:?gc.count          thrpt   20    ? 0          counts
 * Sysfs_existPath_Benchmark.SysfsOriginal:?stack             thrpt         NaN             ---
 * Sysfs_readString_Benchmark.SysfsJNA                        thrpt   20  0.010 ? 0.003  ops/ms
 * Sysfs_readString_Benchmark.SysfsJNA:?gc.alloc.rate         thrpt   20    NaN          MB/sec
 * Sysfs_readString_Benchmark.SysfsJNA:?gc.count              thrpt   20    ? 0          counts
 * Sysfs_readString_Benchmark.SysfsJNA:?stack                 thrpt         NaN             ---
 * Sysfs_readString_Benchmark.SysfsJNA2                       thrpt   20  0.010 ? 0.003  ops/ms
 * Sysfs_readString_Benchmark.SysfsJNA2:?gc.alloc.rate        thrpt   20    NaN          MB/sec
 * Sysfs_readString_Benchmark.SysfsJNA2:?gc.count             thrpt   20    ? 0          counts
 * Sysfs_readString_Benchmark.SysfsJNA2:?stack                thrpt         NaN             ---
 * Sysfs_readString_Benchmark.SysfsJNA3                       thrpt   20  0.011 ? 0.004  ops/ms
 * Sysfs_readString_Benchmark.SysfsJNA3:?gc.alloc.rate        thrpt   20    NaN          MB/sec
 * Sysfs_readString_Benchmark.SysfsJNA3:?gc.count             thrpt   20    ? 0          counts
 * Sysfs_readString_Benchmark.SysfsJNA3:?stack                thrpt         NaN             ---
 * Sysfs_readString_Benchmark.SysfsOriginal                   thrpt   20  0.008 ? 0.002  ops/ms
 * Sysfs_readString_Benchmark.SysfsOriginal:?gc.alloc.rate    thrpt   20    NaN          MB/sec
 * Sysfs_readString_Benchmark.SysfsOriginal:?gc.count         thrpt   20    ? 0          counts
 * Sysfs_readString_Benchmark.SysfsOriginal:?stack            thrpt         NaN             ---
 * Sysfs_writeInteger_Benchmark.Sysfs2                        thrpt   20  0.017 ? 0.004  ops/ms
 * Sysfs_writeInteger_Benchmark.Sysfs2:?gc.alloc.rate         thrpt   20    NaN          MB/sec
 * Sysfs_writeInteger_Benchmark.Sysfs2:?gc.count              thrpt   20    ? 0          counts
 * Sysfs_writeInteger_Benchmark.Sysfs2:?stack                 thrpt         NaN             ---
 * Sysfs_writeInteger_Benchmark.SysfsJNA                      thrpt   20  0.016 ? 0.006  ops/ms
 * Sysfs_writeInteger_Benchmark.SysfsJNA:?gc.alloc.rate       thrpt   20    NaN          MB/sec
 * Sysfs_writeInteger_Benchmark.SysfsJNA:?gc.count            thrpt   20    ? 0          counts
 * Sysfs_writeInteger_Benchmark.SysfsJNA:?stack               thrpt         NaN             ---
 * Sysfs_writeInteger_Benchmark.SysfsJNA2                     thrpt   20  0.016 ? 0.006  ops/ms
 * Sysfs_writeInteger_Benchmark.SysfsJNA2:?gc.alloc.rate      thrpt   20    NaN          MB/sec
 * Sysfs_writeInteger_Benchmark.SysfsJNA2:?gc.count           thrpt   20    ? 0          counts
 * Sysfs_writeInteger_Benchmark.SysfsJNA2:?stack              thrpt         NaN             ---
 * Sysfs_writeInteger_Benchmark.SysfsJNA3                     thrpt   20  0.017 ? 0.006  ops/ms
 * Sysfs_writeInteger_Benchmark.SysfsJNA3:?gc.alloc.rate      thrpt   20    NaN          MB/sec
 * Sysfs_writeInteger_Benchmark.SysfsJNA3:?gc.count           thrpt   20    ? 0          counts
 * Sysfs_writeInteger_Benchmark.SysfsJNA3:?stack              thrpt         NaN             ---
 * Sysfs_writeInteger_Benchmark.SysfsOriginal                 thrpt   20  0.017 ? 0.004  ops/ms
 * Sysfs_writeInteger_Benchmark.SysfsOriginal:?gc.alloc.rate  thrpt   20    NaN          MB/sec
 * Sysfs_writeInteger_Benchmark.SysfsOriginal:?gc.count       thrpt   20    ? 0          counts
 * Sysfs_writeInteger_Benchmark.SysfsOriginal:?stack          thrpt         NaN             ---
 * Sysfs_writeString_Benchmark.Sysfs2                         thrpt   20  0.019 ? 0.003  ops/ms
 * Sysfs_writeString_Benchmark.Sysfs2:?gc.alloc.rate          thrpt   20    NaN          MB/sec
 * Sysfs_writeString_Benchmark.Sysfs2:?gc.count               thrpt   20    ? 0          counts
 * Sysfs_writeString_Benchmark.Sysfs2:?stack                  thrpt         NaN             ---
 * Sysfs_writeString_Benchmark.SysfsJNA                       thrpt   20  0.015 ? 0.006  ops/ms
 * Sysfs_writeString_Benchmark.SysfsJNA:?gc.alloc.rate        thrpt   20    NaN          MB/sec
 * Sysfs_writeString_Benchmark.SysfsJNA:?gc.count             thrpt   20    ? 0          counts
 * Sysfs_writeString_Benchmark.SysfsJNA:?stack                thrpt         NaN             ---
 * Sysfs_writeString_Benchmark.SysfsJNA2                      thrpt   20  0.016 ? 0.007  ops/ms
 * Sysfs_writeString_Benchmark.SysfsJNA2:?gc.alloc.rate       thrpt   20    NaN          MB/sec
 * Sysfs_writeString_Benchmark.SysfsJNA2:?gc.count            thrpt   20    ? 0          counts
 * Sysfs_writeString_Benchmark.SysfsJNA2:?stack               thrpt         NaN             ---
 * Sysfs_writeString_Benchmark.SysfsJNA3                      thrpt   20  0.020 ? 0.008  ops/ms
 * Sysfs_writeString_Benchmark.SysfsJNA3:?gc.alloc.rate       thrpt   20    NaN          MB/sec
 * Sysfs_writeString_Benchmark.SysfsJNA3:?gc.count            thrpt   20    ? 0          counts
 * Sysfs_writeString_Benchmark.SysfsJNA3:?stack               thrpt         NaN             ---
 * Sysfs_writeString_Benchmark.SysfsOriginal                  thrpt   20  0.016 ? 0.003  ops/ms
 * Sysfs_writeString_Benchmark.SysfsOriginal:?gc.alloc.rate   thrpt   20    NaN          MB/sec
 * Sysfs_writeString_Benchmark.SysfsOriginal:?gc.count        thrpt   20    ? 0          counts
 * Sysfs_writeString_Benchmark.SysfsOriginal:?stack           thrpt         NaN             ---
 *
 * Benchmark result is saved to /home/robot/jmh-results.json
 */

/**
 Benchmark                                                  Mode  Cnt    Score     Error   Units
 Sysfs_ExistFile_Benchmark.Sysfs2                             ss   20   15.571 ?  12.105   ms/op
 Sysfs_ExistFile_Benchmark.Sysfs2:?gc.alloc.rate              ss   20      NaN            MB/sec
 Sysfs_ExistFile_Benchmark.Sysfs2:?gc.count                   ss   20      ? 0            counts
 Sysfs_ExistFile_Benchmark.Sysfs2:?stack                      ss           NaN               ---
 Sysfs_ExistFile_Benchmark.SysfsJNA                           ss   20   19.547 ?  20.092   ms/op
 Sysfs_ExistFile_Benchmark.SysfsJNA:?gc.alloc.rate            ss   20      NaN            MB/sec
 Sysfs_ExistFile_Benchmark.SysfsJNA:?gc.count                 ss   20      ? 0            counts
 Sysfs_ExistFile_Benchmark.SysfsJNA:?stack                    ss           NaN               ---
 Sysfs_ExistFile_Benchmark.SysfsJNA2                          ss   20   28.258 ?  48.619   ms/op
 Sysfs_ExistFile_Benchmark.SysfsJNA2:?gc.alloc.rate           ss   20      NaN            MB/sec
 Sysfs_ExistFile_Benchmark.SysfsJNA2:?gc.count                ss   20      ? 0            counts
 Sysfs_ExistFile_Benchmark.SysfsJNA2:?stack                   ss           NaN               ---
 Sysfs_ExistFile_Benchmark.SysfsJNA3                          ss   20   17.829 ?  20.050   ms/op
 Sysfs_ExistFile_Benchmark.SysfsJNA3:?gc.alloc.rate           ss   20      NaN            MB/sec
 Sysfs_ExistFile_Benchmark.SysfsJNA3:?gc.count                ss   20      ? 0            counts
 Sysfs_ExistFile_Benchmark.SysfsJNA3:?stack                   ss           NaN               ---
 Sysfs_ExistFile_Benchmark.SysfsOriginal                      ss   20    9.523 ?   3.981   ms/op
 Sysfs_ExistFile_Benchmark.SysfsOriginal:?gc.alloc.rate       ss   20      NaN            MB/sec
 Sysfs_ExistFile_Benchmark.SysfsOriginal:?gc.count            ss   20      ? 0            counts
 Sysfs_ExistFile_Benchmark.SysfsOriginal:?stack               ss           NaN               ---
 Sysfs_existPath_Benchmark.Sysfs2                             ss   20   39.418 ?   5.533   ms/op
 Sysfs_existPath_Benchmark.Sysfs2:?gc.alloc.rate              ss   20      NaN            MB/sec
 Sysfs_existPath_Benchmark.Sysfs2:?gc.count                   ss   20      ? 0            counts
 Sysfs_existPath_Benchmark.Sysfs2:?stack                      ss           NaN               ---
 Sysfs_existPath_Benchmark.SysfsJNA                           ss   20   73.290 ?  64.469   ms/op
 Sysfs_existPath_Benchmark.SysfsJNA:?gc.alloc.rate            ss   20      NaN            MB/sec
 Sysfs_existPath_Benchmark.SysfsJNA:?gc.count                 ss   20      ? 0            counts
 Sysfs_existPath_Benchmark.SysfsJNA:?stack                    ss           NaN               ---
 Sysfs_existPath_Benchmark.SysfsJNA2                          ss   20   62.707 ?  50.280   ms/op
 Sysfs_existPath_Benchmark.SysfsJNA2:?gc.alloc.rate           ss   20      NaN            MB/sec
 Sysfs_existPath_Benchmark.SysfsJNA2:?gc.count                ss   20      ? 0            counts
 Sysfs_existPath_Benchmark.SysfsJNA2:?stack                   ss           NaN               ---
 Sysfs_existPath_Benchmark.SysfsOriginal                      ss   20   43.207 ?  10.250   ms/op
 Sysfs_existPath_Benchmark.SysfsOriginal:?gc.alloc.rate       ss   20      NaN            MB/sec
 Sysfs_existPath_Benchmark.SysfsOriginal:?gc.count            ss   20      ? 0            counts
 Sysfs_existPath_Benchmark.SysfsOriginal:?stack               ss           NaN               ---
 Sysfs_readString_Benchmark.SysfsJNA                          ss   20  114.712 ?  64.723   ms/op
 Sysfs_readString_Benchmark.SysfsJNA:?gc.alloc.rate           ss   20      NaN            MB/sec
 Sysfs_readString_Benchmark.SysfsJNA:?gc.count                ss   20      ? 0            counts
 Sysfs_readString_Benchmark.SysfsJNA:?stack                   ss           NaN               ---
 Sysfs_readString_Benchmark.SysfsJNA2                         ss   20  124.777 ?  69.026   ms/op
 Sysfs_readString_Benchmark.SysfsJNA2:?gc.alloc.rate          ss   20      NaN            MB/sec
 Sysfs_readString_Benchmark.SysfsJNA2:?gc.count               ss   20      ? 0            counts
 Sysfs_readString_Benchmark.SysfsJNA2:?stack                  ss           NaN               ---
 Sysfs_readString_Benchmark.SysfsJNA3                         ss   20  139.290 ? 116.395   ms/op
 Sysfs_readString_Benchmark.SysfsJNA3:?gc.alloc.rate          ss   20      NaN            MB/sec
 Sysfs_readString_Benchmark.SysfsJNA3:?gc.count               ss   20      ? 0            counts
 Sysfs_readString_Benchmark.SysfsJNA3:?stack                  ss           NaN               ---
 Sysfs_readString_Benchmark.SysfsOriginal                     ss   20  125.183 ?  35.970   ms/op
 Sysfs_readString_Benchmark.SysfsOriginal:?gc.alloc.rate      ss   20      NaN            MB/sec
 Sysfs_readString_Benchmark.SysfsOriginal:?gc.count           ss   20      ? 0            counts
 Sysfs_readString_Benchmark.SysfsOriginal:?stack              ss           NaN               ---
 Sysfs_writeInteger_Benchmark.Sysfs2                          ss   20   57.790 ?  13.316   ms/op
 Sysfs_writeInteger_Benchmark.Sysfs2:?gc.alloc.rate           ss   20      NaN            MB/sec
 Sysfs_writeInteger_Benchmark.Sysfs2:?gc.count                ss   20      ? 0            counts
 Sysfs_writeInteger_Benchmark.Sysfs2:?stack                   ss           NaN               ---
 Sysfs_writeInteger_Benchmark.SysfsJNA                        ss   20  102.920 ?  66.352   ms/op
 Sysfs_writeInteger_Benchmark.SysfsJNA:?gc.alloc.rate         ss   20      NaN            MB/sec
 Sysfs_writeInteger_Benchmark.SysfsJNA:?gc.count              ss   20      ? 0            counts
 Sysfs_writeInteger_Benchmark.SysfsJNA:?stack                 ss           NaN               ---
 Sysfs_writeInteger_Benchmark.SysfsJNA2                       ss   20  112.213 ? 119.813   ms/op
 Sysfs_writeInteger_Benchmark.SysfsJNA2:?gc.alloc.rate        ss   20      NaN            MB/sec
 Sysfs_writeInteger_Benchmark.SysfsJNA2:?gc.count             ss   20      ? 0            counts
 Sysfs_writeInteger_Benchmark.SysfsJNA2:?stack                ss           NaN               ---
 Sysfs_writeInteger_Benchmark.SysfsJNA3                       ss   20   82.762 ?  93.211   ms/op
 Sysfs_writeInteger_Benchmark.SysfsJNA3:?gc.alloc.rate        ss   20      NaN            MB/sec
 Sysfs_writeInteger_Benchmark.SysfsJNA3:?gc.count             ss   20      ? 0            counts
 Sysfs_writeInteger_Benchmark.SysfsJNA3:?stack                ss           NaN               ---
 Sysfs_writeInteger_Benchmark.SysfsOriginal                   ss   20   67.545 ?  39.738   ms/op
 Sysfs_writeInteger_Benchmark.SysfsOriginal:?gc.alloc.rate    ss   20      NaN            MB/sec
 Sysfs_writeInteger_Benchmark.SysfsOriginal:?gc.count         ss   20      ? 0            counts
 Sysfs_writeInteger_Benchmark.SysfsOriginal:?stack            ss           NaN               ---
 Sysfs_writeString_Benchmark.Sysfs2                           ss   20   57.017 ?  19.105   ms/op
 Sysfs_writeString_Benchmark.Sysfs2:?gc.alloc.rate            ss   20      NaN            MB/sec
 Sysfs_writeString_Benchmark.Sysfs2:?gc.count                 ss   20      ? 0            counts
 Sysfs_writeString_Benchmark.Sysfs2:?stack                    ss           NaN               ---
 Sysfs_writeString_Benchmark.SysfsJNA                         ss   20   75.872 ?  59.289   ms/op
 Sysfs_writeString_Benchmark.SysfsJNA:?gc.alloc.rate          ss   20      NaN            MB/sec
 Sysfs_writeString_Benchmark.SysfsJNA:?gc.count               ss   20      ? 0            counts
 Sysfs_writeString_Benchmark.SysfsJNA:?stack                  ss           NaN               ---
 Sysfs_writeString_Benchmark.SysfsJNA2                        ss   20   93.195 ?  70.036   ms/op
 Sysfs_writeString_Benchmark.SysfsJNA2:?gc.alloc.rate         ss   20      NaN            MB/sec
 Sysfs_writeString_Benchmark.SysfsJNA2:?gc.count              ss   20      ? 0            counts
 Sysfs_writeString_Benchmark.SysfsJNA2:?stack                 ss           NaN               ---
 Sysfs_writeString_Benchmark.SysfsJNA3                        ss   20   89.512 ?  78.663   ms/op
 Sysfs_writeString_Benchmark.SysfsJNA3:?gc.alloc.rate         ss   20      NaN            MB/sec
 Sysfs_writeString_Benchmark.SysfsJNA3:?gc.count              ss   20      ? 0            counts
 Sysfs_writeString_Benchmark.SysfsJNA3:?stack                 ss           NaN               ---
 Sysfs_writeString_Benchmark.SysfsOriginal                    ss   20   55.680 ?  15.191   ms/op
 Sysfs_writeString_Benchmark.SysfsOriginal:?gc.alloc.rate     ss   20      NaN            MB/sec
 Sysfs_writeString_Benchmark.SysfsOriginal:?gc.count          ss   20      ? 0            counts
 Sysfs_writeString_Benchmark.SysfsOriginal:?stack             ss           NaN               ---

 */
