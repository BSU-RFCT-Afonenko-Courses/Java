final class IntStack {
    private final int[] data;
    private int size;

    IntStack(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity");
        data = new int[capacity];
    }
    int size() { return size; }
    void push(int value) {
        if (size == data.length) throw new IllegalStateException("full");
        data[size] = value;
        size++;
    }
    int pop() {
        if (size == 0) throw new IllegalStateException("empty");
        size--;
        return data[size];
    }
}
