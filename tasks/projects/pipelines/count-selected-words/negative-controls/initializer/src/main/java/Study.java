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

    public static void printCounts(String text, List<String> queries) {
        Map<String, Long> counts =
                Arrays.stream(text.split("[^A-Za-z]+"))
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        System.out.print(
                queries.stream()
                        .map(s -> s + "-" + counts.getOrDefault(s, 0L))
                        .collect(Collectors.joining(", ", "[", "]")));
    }
}
