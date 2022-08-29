package threading;

public class Counter {

    private int value;

    public Counter() {
        this.value = 0;
    }

    public void increment() {
        this.value++;
    }

    public void decrement() {
        this.value--;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
