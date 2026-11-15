import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.regex.*;

class RunFile {
    public static void main(String[] args) {
        try {
            Study.process(
                    Path.of(args[0]),
                    Path.of(args[1]),
                    Arrays.asList(Arrays.copyOfRange(args, 2, args.length)));
        } catch (Exception failure) {
            System.err.println("Ошибка файловой операции: " + failure.getMessage());
        }
    }
}

/** Выбор: непрерывные Unicode-буквы, нижний регистр, устойчивый порядок списка. */
public class Study {
    public static void process(Path input, Path output, List<String> words) throws IOException {
        var report = new ArrayList<String>();
        for (String line : Files.readAllLines(input, StandardCharsets.UTF_8)) {
            var counts = new LinkedHashMap<String, Integer>();
            for (String word : words) counts.put(word, 0);
            var tokens = Pattern.compile("\\p{L}+").matcher(line.toLowerCase(Locale.ROOT));
            while (tokens.find()) counts.computeIfPresent(tokens.group(), (k, v) -> v + 1);
            var sorted = new ArrayList<>(counts.entrySet());
            sorted.sort(Map.Entry.comparingByValue());
            report.add(
                    sorted.stream()
                            .filter(e -> e.getValue() > 0)
                            .map(e -> e.getKey() + ":" + e.getValue())
                            .collect(java.util.stream.Collectors.joining(" | ")));
        }
        Files.write(output, report, StandardCharsets.UTF_8);
    }
}
