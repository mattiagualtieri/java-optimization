package threading;

/**
 * The class is intending to reproduce a race condition, accessing
 * making two threads access the same shared object (an instance of
 * class "Counter"). This will probably (not for sure, considering
 * the fact that race conditions are non-deterministic) produce
 * inconsistent data in the output.
 * Indeed, instantiating and running 2 instances of CounterRaceCondition
 * should be (hypothetically, in a "serial" way of thinking) producing
 * an output like this:
 *
 * [Thread-X] counter = 2000000 <-- last read
 *
 * Instead of this, in the majority of cases, we're having:
 *
 * [Thread-X] counter = 199999X <-- last read
 *
 * This proves that we're "loosing" soma data (not a lot, but enough to
 * produce a deadly bug in the process)
 *
 */

public class RaceCondition implements Runnable {

    private Counter counter;

    public RaceCondition(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1_000_000; i++) {
            this.counter.increment();
            if (i == 1_000_000 - 1) {
                System.out.println("[" + Thread.currentThread().getName() + "] counter = " + this.counter + " <-- last read");
            } else {
                System.out.println("[" + Thread.currentThread().getName() + "] counter = " + this.counter);
            }
        }
    }

    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread t0 = new Thread(new RaceCondition(counter));
        Thread t1 = new Thread(new RaceCondition(counter));
        t0.start();
        t1.start();
    }

}