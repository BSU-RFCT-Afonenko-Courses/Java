class Example {
    static <T> void show(T[] storage, T[] values) throws Throwable {
        Study<T> q = new Study<>(storage);
        System.out.println(q.get());
        q.put(values[0]);
        q.put(values[1]);
        System.out.println(q.get());
        for (int i = 2; i < values.length; i++) q.put(values[i]);
        for (int i = 1; i < values.length; i++) System.out.println(q.get());
        System.out.println(q.get());
    }

    void main() throws Throwable {
        show(new String[1], new String[] {"a", "b", "c", "d", "e", "f", "g", "h"});
        show(new Integer[1], new Integer[] {1, 2, 3, 4, 5, 6, 7, 8});
    }
}

public class Study<T> extends Access<T> {
    public <U extends T> Study(U... storage) throws Throwable {
        super(storage);
    }
}

class Access<T> extends DynQueue<T> {
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

class DynQueue<T> {
    private T[] data;
    private int first, last;

    DynQueue(T[] input) {
        data = input;
    }

    <U extends T> void put(U value) {
        if (last == data.length) {
            T[] next = java.util.Arrays.copyOfRange(data, first, data.length + first + 1);
            last -= first;
            first = 0;
            data = next;
        }
        data[last++] = value;
    }

    T get() {
        if (first == last) {
            System.out.println("Очередь пуста");
            return null;
        }
        return data[first++];
    }
}
