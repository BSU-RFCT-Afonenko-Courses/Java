public class ClassApiDemo {
    static final class Counter {
        private int value;
        Counter(int initial) { value = initial; }
        int value() { return value; }
        void increment() { value = Math.incrementExact(value); }
    }

    static class Box {
        private final int side;
        Box(int side) {
            if (side <= 0) throw new IllegalArgumentException();
            this.side = side;
        }
        Box() { this(1); }
    }

    record Range(int start, int end) {
        Range {
            if (start > end) throw new IllegalArgumentException();
        }
        Range(int end) { this(0, end); }
    }

    public static void main(String[] args) {
        Counter counter = new Counter(4);
        counter.increment();
        System.out.println(counter.value());

        Box unit = new Box();
        System.out.println(unit.side);

        Range range = new Range(5);
        System.out.println(range.start() + " " + range.end());
    }
}
