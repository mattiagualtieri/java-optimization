package techniques;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The final output with these configurations should be looking something like this:
 *
 * -------------------------------------------------------------------------------------
 * |  Benchmark                                     Mode  Cnt   Score   Error  Units   |
 * |  ResizingVersusSizedArray.addToResizingArray  thrpt   10  28,804 ± 3,230  ops/s   |
 * |  ResizingVersusSizedArray.addToSizedArray     thrpt   10  44,894 ± 3,776  ops/s   |
 * -------------------------------------------------------------------------------------
 *
 */

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 3)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
public class ResizingVersusSizedArray {

    int SIZE = 1_000_000;
    List<Integer> resizingArray;
    List<Integer> sizedArray;

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(ResizingVersusSizedArray.class.getSimpleName())
                .build();

        new Runner(opt).run();

    }

    @Benchmark
    public List<Integer> addToResizingArray() {
        resizingArray = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            resizingArray.add(i);
        }
        return resizingArray;
    }

    @Benchmark
    public List<Integer> addToSizedArray() {
        sizedArray = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            sizedArray.add(i);
        }
        return sizedArray;
    }

}
