package techniques;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * The final output with these configurations should be looking something like this:
 *
 * ----------------------------------------------------------------------------------------------------
 * |   Benchmark                                          Mode  Cnt       Score       Error   Units   |
 * ----------------------------------------------------------------------------------------------------
 * |   ArrayListVersusLinkedList.accessHeadArrayList     thrpt    5  758623,877 ± 17552,261  ops/ms   |
 * |   ArrayListVersusLinkedList.accessHeadLinkedList    thrpt    5  871233,317 ±  3830,908  ops/ms   |
 * ----------------------------------------------------------------------------------------------------
 * |   ArrayListVersusLinkedList.accessMiddleArrayList   thrpt    5  717299,705 ± 33600,716  ops/ms   |
 * |   ArrayListVersusLinkedList.accessMiddleLinkedList  thrpt    5      10,284 ±     0,749  ops/ms   |
 * ----------------------------------------------------------------------------------------------------
 * |   ArrayListVersusLinkedList.accessTailArrayList     thrpt    5  723130,792 ± 13028,458  ops/ms   |
 * |   ArrayListVersusLinkedList.accessTailLinkedList    thrpt    5  767751,140 ±  7863,887  ops/ms   |
 * ----------------------------------------------------------------------------------------------------
 * |   ArrayListVersusLinkedList.insertHeadArrayList     thrpt    5      30,601 ±    27,354  ops/ms   |
 * |   ArrayListVersusLinkedList.insertHeadLinkedList    thrpt    5    6207,210 ±   405,025  ops/ms   |
 * ----------------------------------------------------------------------------------------------------
 *
 */

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
public class ArrayListVersusLinkedList {

    int SIZE = 100_000;
    List<Integer> arrayList;
    List<Integer> linkedList;

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(ArrayListVersusLinkedList.class.getSimpleName())
                .build();

        new Runner(opt).run();

    }

    @Setup()
    public void setUp() {
        arrayList = new ArrayList<>();
        linkedList = new LinkedList<>();
        for (int i = 0; i < SIZE; i++) {
            int value = new Random().nextInt(Integer.MAX_VALUE);
            arrayList.add(value);
            linkedList.add(value);
        }
    }

    @Benchmark
    public int accessHeadArrayList() {
        int value = arrayList.get(0);
        return value;
    }

    @Benchmark
    public int accessHeadLinkedList() {
        int value = linkedList.get(0);
        return value;
    }

    @Benchmark
    public int accessMiddleArrayList() {
        int value = arrayList.get(SIZE / 2);
        return value;
    }

    @Benchmark
    public int accessMiddleLinkedList() {
        int value = linkedList.get(SIZE / 2);
        return value;
    }

    @Benchmark
    public int accessTailArrayList() {
        int value = arrayList.get(SIZE - 1);
        return value;
    }

    @Benchmark
    public int accessTailLinkedList() {
        int value = linkedList.get(SIZE - 1);
        return value;
    }

    @Benchmark
    public void insertHeadArrayList() {
        arrayList.add(0, 999);
    }

    @Benchmark
    public void insertHeadLinkedList() {
        linkedList.add(0, 999);
    }

}
