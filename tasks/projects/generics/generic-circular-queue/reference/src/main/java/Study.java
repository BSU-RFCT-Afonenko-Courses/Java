class Example {
    static <T> void show(T[] storage, T a, T b, T c) throws Throwable {
        Study<T> q = new Study<>(storage);
        System.out.println(q.get());
        q.put(a);
        q.put(b);
        q.put(c);
        System.out.println(q.get());
        q.put(c);
        System.out.println(q.get());
        System.out.println(q.get());
        System.out.println(q.get());
    }

    void main() throws Throwable {
        show(new String[] {"старое", "содержимое"}, "Лист", "Ветка", "Плод");
        show(new Integer[] {42, 43}, 7, 8, 9);
    }
}

public class Study<T> {
    private final CircularQueue<T> queue;

    public Study(T[] storage) {
        queue = new CircularQueue<>(storage);
    }

    public void put(T value) {
        queue.put(value);
    }

    public T get() {
        return queue.get();
    }
}

class CircularQueue<T> {
    private final T[] cells;
    private int head, size;

    CircularQueue(T[] storage) {
        cells = storage;
    }

    void put(T value) {
        if (size == cells.length) {
            System.err.println("Очередь полна");
            return;
        }
        cells[(head + size) % cells.length] = value;
        size++;
    }

    T get() {
        if (size == 0) {
            System.err.println("Очередь пуста");
            return null;
        }
        T value = cells[head];
        head = (head + 1) % cells.length;
        size--;
        return value;
    }
}
