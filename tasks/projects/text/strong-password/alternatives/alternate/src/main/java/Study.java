/** Альтернатива с собственной реализацией в отдельном пакете. */
public class Study {
    public static boolean accepts(String text) {
        return new Example().test(text);
    }
}

class Example {
    public boolean test(String value) {
        return value.matches("(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])[A-Za-z0-9_]{10,}");
    }
}
