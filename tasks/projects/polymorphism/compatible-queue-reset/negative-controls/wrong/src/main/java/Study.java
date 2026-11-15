public class Study {
    public static Object oldQueue() {
        return new LegacyQueue(2);
    }

    public static Object newQueue() {
        return new ResetQueue(2);
    }

    public static void put(Object q, char c) {
        ((ICharQ) q).put(c);
    }

    public static char get(Object q) {
        return ((ICharQ) q).get();
    }

    public static void reset(Object q) {
        ((ICharQ) q).reset();
    }
}

interface ICharQ {
    void put(char c);

    char get();

    default void reset() {
        throw new UnsupportedOperationException("reset unsupported");
    }
}

class LegacyQueue implements ICharQ {
    private final char[] data;
    private int head, tail;

    public LegacyQueue(int n) {
        data = new char[n];
    }

    public void put(char c) {
        if (tail == data.length) {
            System.out.println("Очередь переполнена");
            return;
        }
        data[tail++] = c;
    }

    public char get() {
        if (head == tail) {
            System.out.println("Очередь пуста");
            return '\0';
        }
        return data[head++];
    }
}

class ResetQueue implements ICharQ {
    private final char[] data;
    private int head, tail;

    public ResetQueue(int n) {
        data = new char[n];
    }

    public void put(char c) {
        if (tail == data.length) {
            System.out.println("Очередь переполнена");
            return;
        }
        data[tail++] = c;
    }

    public char get() {
        if (head == tail) {
            System.out.println("Очередь пуста");
            return '\0';
        }
        return data[head++];
    }

    public void reset() {
        /* Ошибка: сброс ничего не меняет. */
    }
}
