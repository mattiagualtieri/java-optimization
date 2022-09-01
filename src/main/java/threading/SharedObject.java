package threading;

import java.util.concurrent.TimeUnit;

public class SharedObject {

    private String name;

    public SharedObject(String name) {
        this.name = name;
    }

    synchronized void test1(SharedObject s) throws InterruptedException {
        System.out.println("[" + Thread.currentThread().getName() + "] test1 started");
        System.out.println("[" + Thread.currentThread().getName() + "] locked resource: " + this.getName());

        // ...
        TimeUnit.SECONDS.sleep(1);
        // ...

        System.out.println("[" + Thread.currentThread().getName() + "] trying to lock resource: " + s.getName());
        s.test2();
        System.out.println("[" + Thread.currentThread().getName() + "] test1 terminated");
    }

    synchronized void test2() throws InterruptedException {
        System.out.println("[" + Thread.currentThread().getName() + "] test2 started");

        // ...
        TimeUnit.SECONDS.sleep(1);
        // ...

        System.out.println("[" + Thread.currentThread().getName() + "] test2 terminated");
    }

    public String getName() {
        return name;
    }

}
