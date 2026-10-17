public class CounterDemo {
    public static void main(String[] args) {
        Counter counter = new Counter();
        Counter same = counter.increment().increment();
        System.out.println(counter.value());
        System.out.println(same == counter);
    }
}
