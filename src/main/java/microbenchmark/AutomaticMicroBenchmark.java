package microbenchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * The final output with these configurations should be looking something like this:
 *
 * -------------------------------------------------------------------------------
 * |  Benchmark                              Mode  Cnt   Score   Error   Units   |
 * |  AutomaticMicroBenchmark.classicSort   thrpt    5  18,583 ± 1,705  ops/ms   |
 * |  AutomaticMicroBenchmark.parallelSort  thrpt    5  12,635 ± 0,542  ops/ms   |
 * -------------------------------------------------------------------------------
 *
 */

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 10, time = 1)
@Measurement(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
public class AutomaticMicroBenchmark {

    int SIZE = 1_000;
    List<Integer> data;

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(AutomaticMicroBenchmark.class.getSimpleName())
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

    @Benchmark
    public List<Integer> parallelSort() {
        List<Integer> copy = new ArrayList<>(data);
        Collections.sort(copy);
        return copy;
    }

}
