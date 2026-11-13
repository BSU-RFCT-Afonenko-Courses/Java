import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
import java.util.stream.*;

public class Study {
    public static void printCounts(String text, List<String> queries) {
        Map<String, Long> counts =
                Arrays.stream(text.toLowerCase(java.util.Locale.ROOT).split("[^A-Za-z]+"))
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        System.out.print(
                queries.stream()
                        .map(s -> s + "-" + counts.getOrDefault(s, 0L))
                        .collect(Collectors.joining(", ", "[", "]")));
    }
}
