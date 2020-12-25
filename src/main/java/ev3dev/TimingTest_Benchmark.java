package ev3dev;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

public class TimingTest_Benchmark {

    @State(Scope.Thread)
    public static class St {
        TimingTest timingTest = new TimingTest();
    }

    @Benchmark
    public void SysfsOriginal(St state, Blackhole b) {
        b.consume(state.timingTest.program());
    }
}
