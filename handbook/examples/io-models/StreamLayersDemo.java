import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public final class StreamLayersDemo {
    private static final byte[] SAMPLE =
            "Ада\nБоб\n".getBytes(StandardCharsets.UTF_8);

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            throw new IllegalArgumentException(
                    "Запуск: java StreamLayersDemo [файл UTF-8]");
        }

        try (InputStream input = open(args)) {
            System.out.println("Байтов: " + countBytes(input));
        }

        try (Reader input = new InputStreamReader(
                open(args), StandardCharsets.UTF_8.newDecoder())) {
            System.out.println("Единиц char: " + countCharacters(input));
        }

        try (BufferedReader input = new BufferedReader(
                new InputStreamReader(
                        open(args), StandardCharsets.UTF_8.newDecoder()), 4)) {
            String line;
            int number = 0;
            while ((line = input.readLine()) != null) {
                System.out.println(++number + ": " + line);
            }
        }
    }

    private static InputStream open(String[] args) throws IOException {
        return args.length == 0
                ? new ByteArrayInputStream(SAMPLE)
                : new FileInputStream(args[0]);
    }

    static long countBytes(InputStream input) throws IOException {
        long count = 0;
        while (input.read() != -1) {
            count++;
        }
        return count;
    }

    static long countCharacters(Reader input) throws IOException {
        long count = 0;
        while (input.read() != -1) {
            count++;
        }
        return count;
    }
}
