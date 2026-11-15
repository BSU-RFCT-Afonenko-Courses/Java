import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
import java.util.stream.*;

public class Study {
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

    public static String order(String input) {
        return input.chars()
                .boxed()
                .sorted(
                        Comparator.<Integer>comparingInt(c -> "aeiou".indexOf(c) >= 0 ? 0 : 1)
                                .thenComparingInt(c -> c))
                .collect(
                        StringBuilder::new,
                        (b, c) -> b.append((char) c.intValue()),
                        StringBuilder::append)
                .toString();
    }
}
