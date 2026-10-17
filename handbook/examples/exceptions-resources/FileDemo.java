import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileDemo {
    static String manualFirst(Path path) throws IOException {
        var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        String line = reader.readLine();
        reader.close();
        return line;
    }

    static String firstLine(Path path) throws IOException {
        try (var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return reader.readLine();
        }
    }

    static void copy(Path source, Path target) throws IOException {
        try (var input = Files.newInputStream(source);
             var output = Files.newOutputStream(target)) {
            input.transferTo(output);
        }
    }

    static long countLines(BufferedReader reader) throws IOException {
        long count = 0;
        while (reader.readLine() != null) count++;
        return count;
    }

    static List<String> nonblankLines(Path path) throws IOException {
        try (var lines = Files.lines(path, StandardCharsets.UTF_8)) {
            return lines.filter(s -> !s.isBlank()).toList();
        }
    }

    static ExceptionDemo.ScoreResult firstScore(Path path) throws IOException {
        try (var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            if (line == null) {
                return new ExceptionDemo.ScoreFailure(ExceptionDemo.ScoreProblem.NOT_INTEGER);
            }
            return ExceptionDemo.parseScoreResult(line);
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Укажите исходный файл и отдельный путь для копии");
            return;
        }
        Path source = Path.of(args[0]);
        Path target = Path.of(args[1]);
        System.out.println(manualFirst(source));
        System.out.println(firstLine(source));
        copy(source, target);
        System.out.print(Files.readString(target, StandardCharsets.UTF_8));
        System.out.println(nonblankLines(source));
        var reader = Files.newBufferedReader(source, StandardCharsets.UTF_8);
        try (reader) {
            System.out.println(countLines(reader));
            System.out.println(reader.readLine());
        }
        try {
            reader.readLine();
        } catch (IOException failure) {
            System.out.println("чтение после close: " + failure.getClass().getSimpleName());
        }
    }
}
