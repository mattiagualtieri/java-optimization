package threading;

import java.util.concurrent.TimeUnit;

/**
 * In this example, we'll instantiate 2 threads of the same class.
 * The 2 threads will switch behaviours depending on the "behaviour" variable.
 * Both threads hold an instance of 2 objects (SharedObject), that have a
 * name and 2 synchronized methods.
 *
 * Let's recreate the sequence of the events:
 * 1. The first thread (t1) calls method test1(s2) of object s1. Now the
 *    object s1 is locked by t1.
 * 2. The second thread (t2) calls method test1(s1) of object s2. Now the
 *    object s2 is locked by t2.
 * 3. Both method calls simulate some operations, introducing a small time
 *    delay.
 * 4. Then, in test1() method, t1 tries to take object s2 lock in order to
 *    call method test2() on it, but s2's lock is already acquired by t2.
 *    The same situation happens for t2 on object s1.
 * 5. Note that s1's lock won't be released until method test1() called by
 *    t1 is terminated, and same thing for s2's lock: both threads are now
 *    waiting for the other thread to release the lock.
 * 6. We have a "Dead Lock condition", the program will execute forever,
 *    while the 2 threads are trying to access locks.
 *
 * Output:
 * [Thread-1] test1 started
 * [Thread-2] test1 started
 * [Thread-1] locked resource: s1
 * [Thread-2] locked resource: s2
 * [Thread-2] trying to lock resource: s1
 * [Thread-1] trying to lock resource: s2
 * 
 */

public class DeadLockCondition extends Thread {

    private SharedObject s1;
    private SharedObject s2;
    private int behaviour;

    public DeadLockCondition(SharedObject s1, SharedObject s2, int behaviour) {
        this.s1 = s1;
        this.s2 = s2;
        if (behaviour != 1 && behaviour != 2) {
            throw new IllegalArgumentException("Behaviour must be 1 or 2");
        }
        this.behaviour = behaviour;
        this.setName("Thread-" + this.behaviour);
    }

    @Override
    public void run() {
        try {
            if (behaviour == 1) {
                // Thread-1
                s1.test1(s2);
            } else if (behaviour == 2) {
                // Thread-2
                s2.test1(s1);
            } else {
                throw new UnsupportedOperationException("Invalid behaviour specified: " + this.behaviour);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        SharedObject s1 = new SharedObject("s1");
        SharedObject s2 = new SharedObject("s2");
        DeadLockCondition t1 = new DeadLockCondition(s1, s2, 1);
        t1.start();
        DeadLockCondition t2 = new DeadLockCondition(s1, s2, 2);
        t2.start();

        TimeUnit.SECONDS.sleep(2);
    }

}
