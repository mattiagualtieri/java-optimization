package microbenchmark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ManualMicroBenchmark {

    private static final int SIZE = 1_000;
    private static final int N_RUNS = 100_000;
    private static List<Integer> data;

    public static void main(String[] args) {
        initData();
        System.out.println("Testing Sort Algorithm");
        double startTime = System.nanoTime();
        for (int i = 0; i < N_RUNS; i++) {
            List<Integer> copy = new ArrayList<>(data);
            Collections.sort(copy);
        }
        double endTime = System.nanoTime();
        double timePerOperation = (endTime - startTime) / (1_000_000_000L * N_RUNS);
        double timeForExecution = (endTime - startTime) / 1_000_000_000L;
        System.out.println("Result: " + (1 / timePerOperation) + " op/s");
        System.out.println("Result: " + timeForExecution + " s");
    }

    private static void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            data.add(new Random().nextInt(Integer.MAX_VALUE));
        }
    }

}