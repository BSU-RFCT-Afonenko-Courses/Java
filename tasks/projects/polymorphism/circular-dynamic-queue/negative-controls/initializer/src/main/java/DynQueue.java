public class DynQueue implements ICharQ {
    static {
        if (java.lang.System.getProperty("course.initializer.marker") != null) {
            try {
                java.nio.file.Files.writeString(
                        java.nio.file.Path.of(
                                java.lang.System.getProperty("course.initializer.marker")),
                        "student initializer executed");
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException(e);
            }
            throw new java.lang.IllegalStateException("initializer sentinel");
        }
    }

    private char[] data;
    private int head, size;

    public DynQueue(int n) {
        data = new char[n];
    }

    public void put(char c) {
        if (size == data.length) {
            char[] grown = new char[data.length * 2];
            for (int i = 0; i < size; i++) grown[i] = data[(head + i) % data.length];
            data = grown;
            head = 0;
        }
        data[(head + size++) % data.length] = c;
    }

    public char get() {
        if (size == 0) {
            System.out.println("Очередь пуста");
            return '\0';
        }
        char c = data[head];
        head = (head + 1) % data.length;
        size--;
        return c;
    }

    public void reset() {
        head = size = 0;
    }
}
