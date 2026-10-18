final class Box<T> {
    private final T value;
    Box(T value) {
        this.value = value;
    }
    T get() {
        return value;
    }
}
