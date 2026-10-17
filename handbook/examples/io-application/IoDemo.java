import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class IoDemo {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("использование: IoDemo path|bytes|lines|text|count файл");
            return;
        }
        Path path = Path.of(args[1]);
        switch (args[0]) {
            case "path" -> {
                System.out.println(path.isAbsolute());
                System.out.println(path.toAbsolutePath());
            }
            case "bytes" -> {
                try (var input = Files.newInputStream(path)) {
                    int value;
                    while ((value = input.read()) != -1) {
                        System.out.println(value);
                    }
                }
            }
            case "lines" -> {
                try (var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                    long number = 0;
                    String line;
                    while ((line = reader.readLine()) != null) {
                        number++;
                        System.out.println(number + ": [" + line + "]");
                    }
                }
            }
            case "text" -> System.out.println(
                Files.readString(path, StandardCharsets.UTF_8));
            case "count" -> {
                try (var lines = Files.lines(path, StandardCharsets.UTF_8)) {
                    System.out.println(lines.filter(line -> !line.isBlank()).count());
                }
            }
            default -> System.err.println("неизвестный опыт: " + args[0]);
        }
    }
}
