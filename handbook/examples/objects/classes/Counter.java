final class Counter {
    private int value;
    Counter increment() {
        value = Math.incrementExact(value);
        return this;
    }
    int value() { return value; }
}
