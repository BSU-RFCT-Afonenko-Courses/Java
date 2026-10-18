final class Reservation {
    private final int capacity;
    private int occupied;

    Reservation(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.capacity = capacity;
    }

    int available() {
        return capacity - occupied;
    }

    void reserve(int count) {
        if (count <= 0 || count > available()) {
            throw new IllegalArgumentException("invalid reservation size");
        }
        occupied += count;
    }
}
