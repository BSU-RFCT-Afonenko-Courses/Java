import java.util.*;
import java.util.stream.*;

public class Study {
    private static final class Frequencies extends HashMap<Integer, Long> {
        @java.io.Serial private static final long serialVersionUID = 1L;
    }

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

    public static Object count(String text) {
        return text.chars()
                .boxed()
                .collect(Collectors.groupingBy(x -> x, Frequencies::new, Collectors.counting()));
    }

    public static Map<Integer, ? extends Number> frequencies(Object result) {
        return Frequencies.class.cast(result);
    }
}
