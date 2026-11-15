class Base<E, F extends Exception, X extends Exception> {
    private final E[] data;
    private int size;
    private final java.util.function.Supplier<F> full;
    private final java.util.function.Supplier<X> empty;

    public Base(
            E[] data, java.util.function.Supplier<F> full, java.util.function.Supplier<X> empty) {
        this.data = data;
        this.full = full;
        this.empty = empty;
    }

    public void put(E value) throws F {
        if (size == data.length) throw full.get();
        data[size++] = value;
    }

    public E get() throws X {
        if (size == 0) throw empty.get();
        E answer = data[0];
        size--;
        System.arraycopy(data, 1, data, 0, size);
        data[size] = null;
        return answer;
    }
}

class Example {
    static <T> void show(T[] storage, T a, T b, T c) throws Throwable {
        GenericQueue<T> q = new GenericQueue<>(storage);
        try {
            q.get();
        } catch (QueueEmptyException e) {
            System.out.println("Обработана начальная пустота");
        }
        q.put(a);
        q.put(b);
        try {
            q.put(c);
        } catch (QueueFullException e) {
            System.out.println("Обработано переполнение");
        }
        System.out.println(q.get());
        q.put(c);
        System.out.println(q.get());
        System.out.println(q.get());
        try {
            q.get();
        } catch (QueueEmptyException e) {
            System.out.println("Обработана пустота");
        }
    }

    void main() throws Throwable {
        show(new String[] {"старое", "содержимое"}, "Лист", "Ветка", "Плод");
        show(new Integer[] {42, 43}, 7, 8, 9);
    }
}

public class GenericQueue<T> extends Base<T, QueueFullException, QueueEmptyException> {
    public GenericQueue(T[] data) {
        super(data, () -> new QueueFullException(7), () -> new QueueEmptyException("empty"));
    }
}
