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

public class Study<T> extends Access<T> {
    public <U extends T> Study(U... storage) throws Throwable {
        super(storage);
    }
}

class Access<T> extends CircularQueue<T> {
    protected Access(T[] storage) {
        super(storage);
    }

    public <U extends T> void put(U value) {
        super.put(value);
    }

    public T get() {
        return super.get();
    }
}

class CircularQueue<T> {
    private final T[] storage;
    private int read, write, count;

    CircularQueue(T[] array) {
        storage = array;
    }

    <U extends T> void put(U x) {
        if (count == storage.length) {
            System.out.println("Мест нет");
            return;
        }
        storage[write] = x;
        write = (write + 1) % storage.length;
        count++;
    }

    T get() {
        if (count == 0) {
            System.out.println("Данных нет");
            return null;
        }
        T value = storage[read];
        read = (read + 1) % storage.length;
        count--;
        return value;
    }
}
