import java.util.ArrayDeque;

public class Study {
    interface Queue {
        void put(char c);

        char get();

        default int reset() {
            throw new UnsupportedOperationException();
        }
    }

    private static class Old implements Queue {
        final ArrayDeque<Character> q = new ArrayDeque<>();

        public void put(char c) {
            if (q.size() == 2) {
                System.out.println("Очередь переполнена");
                return;
            }
            q.add(c);
        }

        public char get() {
            if (q.isEmpty()) {
                System.out.println("Очередь пуста");
                return '\0';
            }
            return q.remove();
        }
    }

    private interface Resettable extends Queue {
        ArrayDeque<Character> data();

        default int reset() {
            int n = data().size();
            data().clear();
            return n;
        }
    }

    private static final class New extends Old implements Resettable {
        public ArrayDeque<Character> data() {
            return q;
        }
    }

    public static Object oldQueue() {
        return new Old();
    }

    public static Object newQueue() {
        return new New();
    }

    public static void put(Object q, char c) {
        ((Queue) q).put(c);
    }

    public static char get(Object q) {
        return ((Queue) q).get();
    }

    public static void reset(Object q) {
        ((Queue) q).reset();
    }
}
