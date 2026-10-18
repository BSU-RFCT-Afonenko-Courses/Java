import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesLinesDemo {
    static long countNonblank(Path path) throws IOException {
        try (var lines = Files.lines(path, StandardCharsets.UTF_8)) {
            return lines.filter(line -> !line.isBlank()).count();
        }
    }
    static void report(Path path) {
        try {
            System.out.println(countNonblank(path));
        } catch (IOException ex) {
            System.err.println("Ошибка открытия файла");
        } catch (UncheckedIOException ex) {
            System.err.println("Ошибка чтения или закрытия файла");
        }
    }
    public static void main(String[] args) throws IOException {
        if (args.length == 1) {
            report(Path.of(args[0]));
            return;
        }
        if (args.length != 0) throw new IllegalArgumentException("нужен один путь");
        Path input = Files.createTempFile("lines-", ".txt");
        try {
            Files.writeString(input, "Ada\n   \nAnn", StandardCharsets.UTF_8);
            report(input);
        } finally {
            Files.deleteIfExists(input);
        }
    }
}
