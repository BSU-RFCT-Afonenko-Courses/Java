public class CompositionDemo {
    static class Counter {
        private int value;
        void increment() { value++; }
        int value() { return value; }
        void add(int n) {
            if (n < 0) throw new IllegalArgumentException("Отрицательное приращение");
            for (int i = 0; i < n; i++) {
                increment();
            }
        }
    }

    static class CountingCounter extends Counter {
        private int added;
        @Override void increment() {
            added++;
            super.increment();
        }
        @Override void add(int n) {
            if (n < 0) throw new IllegalArgumentException("Отрицательное приращение");
            added += n;
            super.add(n);
        }
        int added() { return added; }
    }

    static final class CounterMeter {
        private final Counter counter = new Counter();
        private int added;
        void increment() {
            counter.increment();
            added++;
        }
        void add(int n) {
            counter.add(n);
            added += n;
        }
        int value() { return counter.value(); }
        int added() { return added; }
    }

    public static void main(String[] args) {
        CountingCounter counter = new CountingCounter();
        counter.add(3);
        System.out.println(counter.value());
        System.out.println(counter.added());
        CounterMeter meter = new CounterMeter();
        meter.add(3);
        System.out.println(meter.value());
        System.out.println(meter.added());
        try {
            meter.add(-1);
        } catch (IllegalArgumentException error) {
            System.out.println("отказ");
        }
        System.out.println(meter.value());
        System.out.println(meter.added());
    }
}
