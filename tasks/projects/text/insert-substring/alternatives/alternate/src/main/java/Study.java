/** Альтернатива с собственной реализацией в отдельном пакете. */
public class Study {
    public static String insert(String text, String fragment, int position) {
        return new Example().joinAt(fragment, position, text);
    }
}

class Example {
    public String joinAt(String fragment, int at, String base) {
        return new StringBuilder(base).insert(at, fragment).toString();
    }
}
