package ev3dev.utils;

import ev3dev.sensors.Battery;
import ev3dev.sensors.Battery2;
import ev3dev.sensors.BatteryOld;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

public class BatteryBenchmark {

    @State(Scope.Thread)
    public static class St {
        Battery battery = Battery.getInstance();
        Battery2 battery2 = Battery2.getInstance();
        BatteryOld batteryOld = BatteryOld.getInstance();
    }

    @Benchmark
    public void batteryOldMethod(St state, Blackhole b) {
        b.consume(state.batteryOld.getVoltage());
    }

    @Benchmark
    public void batteryMethod(St state, Blackhole b) {
        b.consume(state.battery.getVoltage());
    }

    @Benchmark
    public void battery2Method(St state, Blackhole b) {
        b.consume(state.battery2.getVoltage());
    }

}
