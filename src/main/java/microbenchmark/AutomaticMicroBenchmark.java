package microbenchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.SampleTime)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
public class AutomaticMicroBenchmark {

    int SIZE = 1_000;
    List<Integer> data;

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(AutomaticMicroBenchmark.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .jvmArgs("-server", "-Xms2048m", "-Xmx2048m")
                .addProfiler(GCProfiler.class)
                .addProfiler(StackProfiler.class)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setUp() {
        data = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            data.add(new Random().nextInt(Integer.MAX_VALUE));
        }
    }

    @Benchmark
    public List<Integer> classicSort() {
        List<Integer> copy = new ArrayList<>(data);
        Collections.sort(copy);
        return copy;
    }

}
