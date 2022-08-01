package techniques;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * The final output with these configurations should be looking something like this:
 *
 * -------------------------------------------------------------------------------
 * |  Benchmark                              Mode  Cnt   Score   Error   Units   |
 * |  ClassicArrayVersusList.readList   thrpt    5   526,556 ± 64,293  ops/ms    |
 * |  ClassicArrayVersusList.readArray  thrpt    5  1134,478 ± 16,180  ops/ms    |
 * -------------------------------------------------------------------------------
 *
 */

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 10, time = 1)
@Measurement(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
public class ClassicArrayVersusList {

    int SIZE = 1_000;
    int dataClassicArray[];
    List<Integer> dataList;

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(ClassicArrayVersusList.class.getSimpleName())
                .build();

        new Runner(opt).run();

    }

    @Setup
    public void setUp() {
        dataList = new ArrayList<>(SIZE);
        dataClassicArray = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            int value = new Random().nextInt(Integer.MAX_VALUE);
            dataList.add(value);
            dataClassicArray[i] = value;
        }
    }

    @Benchmark
    public void readList(Blackhole blackhole) {
        for (int value : dataList) {
            blackhole.consume(value);
        }
    }

    @Benchmark
    public void readArray(Blackhole blackhole) {
        for (int i = 0; i < SIZE; i++) {
            int value = dataClassicArray[i];
            blackhole.consume(value);
        }
    }

}
